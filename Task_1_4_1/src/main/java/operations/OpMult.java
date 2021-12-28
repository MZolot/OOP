package operations;

import java.util.Scanner;

public class OpMult implements Operation {
    public double calculate(Scanner scan){
        double a = Operation.getArg(scan);
        double b = Operation.getArg(scan);
        return a * b;
    }
}

