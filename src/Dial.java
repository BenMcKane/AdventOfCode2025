package src;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Dial {

    private int position;
    private static int modulus;

    public Dial(int modulus) {
        this.position = 50;
        this.modulus = modulus;
    }

    // "Regular" spin; doesn't keep track of how many times you pass 0
    private void spin(int amount) {
        this.position += amount;
        this.position = this.position % this.modulus;
        if (this.position < 0) this.position += this.modulus;
    }

    // "Super" spin; keeps track of how many times you pass 0
    private int superSpin(int amount) {
        // Counts number of times we've passed 100 or -100 to determine how many times 0 was passed
        int startPosition = this.position;
        this.position += amount;
        int numClicks = Math.abs(this.position / this.modulus);

        // Handles the case where we flipped from positive to negative/zero
        if (startPosition != 0 && this.position * startPosition <= 0) numClicks += 1;

        // Spins like normal
        this.position = this.position % this.modulus;
        if (this.position < 0) this.position += this.modulus;
        return numClicks;
    }

    private void reset() {
        this.position = 50;
    }

    private int[] spinOrder(String fileName) throws IOException {
        // Initialize the array that will store the instructions for spinning the dial
        long length = Files.lines(Paths.get(fileName)).count();
        int[] instructions = new int[(int) length];

        // Create a buffered reader to handle input reading
        BufferedReader br = new BufferedReader(new FileReader(fileName));

        // Do the magic
        for (int i = 0; i < instructions.length; i++) {
            String line = br.readLine();
            int sign = 1;
            if (line.charAt(0) == 'L') sign = -1;
            instructions[i] = sign * Integer.parseInt(line.substring(1));
        }

        return instructions;
    }

    public int spinDial(String fileName) throws IOException {
        // Takes in instructions
        int[] instructions = this.spinOrder(fileName);
        int password = 0;

        // Spins the dial one instruction at a time
        for (int i = 0; i < instructions.length; i++) {
            this.spin(instructions[i]);
            if (this.position == 0) password++;
        }

        // Resets in case you want to spin again
        this.reset();

        return password;
    }

    public int superSpinDial(String fileName) throws IOException {
        // Takes in instructions
        int[] instructions = this.spinOrder(fileName);
        int password = 0;

        // Counts the number of clicks as we spin the dial
        for (int i = 0; i < instructions.length; i++) {
            password += this.superSpin(instructions[i]);
        }

        // Resets in case you want to spin again
        this.reset();

        return password;
    }

}
