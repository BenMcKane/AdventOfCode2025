import src.Dial;

import java.io.IOException;

public void main(String[] args) throws IOException {
    Dial dial = new Dial(100);
    int password = dial.spinDial("C:/Users/benmc/IdeaProjects/AdventOfCode2025/inputFiles/dial.txt");
    System.out.println(password);
}
