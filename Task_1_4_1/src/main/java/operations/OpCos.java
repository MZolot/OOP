package operations;

import java.util.Scanner;

public class OpCos implements Operation {
    public double calculate(Scanner scan){
        double x = Operation.getArg(scan);
        return Math.cos(x);
    }
}

