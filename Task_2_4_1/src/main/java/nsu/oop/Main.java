package nsu.oop;

import picocli.CommandLine;

public class Main {
    public static void main(String[] args) {
        String[] myArgs = {"report", "C:/Users/user/Desktop/2_4_1_repositories/"};
        new CommandLine(new Commands()).execute(myArgs);
    }
}
