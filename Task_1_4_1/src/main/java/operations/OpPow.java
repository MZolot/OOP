package operations;

import java.util.Scanner;

public class OpPow implements Operations {
    public double calculate(Scanner scan) {
        double a = Operations.getArg(scan);
        double b = Operations.getArg(scan);
        return Math.pow(a, b);
    }
}