package operations;

import java.util.Scanner;

public class OpDiv implements Operation {
    public double calculate(Scanner scan){
        double a = Operation.getArg(scan);
        double b = Operation.getArg(scan);
        return a / b;
    }
}

