package src;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Dial {

    private int position;
    private static int modulus;

    public Dial(int modulus) {
        this.position = 0;
        this.modulus = modulus;
    }

    private void spin(int amount) {
        this.position += amount;
        this.position = this.position % this.modulus;
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

    public void spinDial(String fileName) throws IOException {
        System.out.println(this.spinOrder(fileName)[7]);
    }

}
