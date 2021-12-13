package operations;

import java.util.Scanner;

public class OpSqrt implements Operation {
    public double calculate(Scanner scan){
        double x = Operation.getArg(scan);
        return Math.sqrt(x);
    }
}
