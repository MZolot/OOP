package operations;

import java.util.Scanner;

public class OpSqrt implements Operations {
    public double calculate(Scanner scan){
        double x = Operations.getArg(scan);
        return Math.sqrt(x);
    }
}
