package operations;

import java.util.Scanner;

public class OpCos implements Operations {
    public double calculate(Scanner scan){
        double x = Operations.getArg(scan);
        return Math.cos(x);
    }
}

