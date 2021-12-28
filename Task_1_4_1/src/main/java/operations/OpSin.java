package operations;

import java.util.Scanner;

public class OpSin implements Operation {
    public double calculate(Scanner scan){
        double x = Operation.getArg(scan);
        return Math.sin(x);
    }
}