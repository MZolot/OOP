package operations;

import java.util.Scanner;

public class OpLog implements Operation {
    public double calculate(Scanner scan){
        double x = Operation.getArg(scan);
        return Math.log(x);
    }
}

