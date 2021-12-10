package operations;

import java.util.Scanner;

public class OpSub implements Operations {
    public double calculate(Scanner scan){
        double a = Operations.getArg(scan);
        double b = Operations.getArg(scan);
        return a - b;
    }
}