package operations;

import java.util.Scanner;

public class OpSin implements Operations {
    public double calculate(Scanner scan){
        double x = Operations.getArg(scan);
        return Math.sin(x);
    }
}