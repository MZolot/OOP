package operations;

import java.util.Scanner;

public class OpDiv implements Operations {
    public double calculate(Scanner scan){
        double a = Operations.getArg(scan);
        double b = Operations.getArg(scan);
        return a / b;
    }
}
