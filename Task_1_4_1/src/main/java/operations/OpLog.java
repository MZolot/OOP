package operations;

import java.util.Scanner;

public class OpLog implements Operations {
    public double calculate(Scanner scan){
        double x = Operations.getArg(scan);
        return Math.log(x);
    }
}

