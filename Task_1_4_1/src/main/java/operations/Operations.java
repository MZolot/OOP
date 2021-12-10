package operations;

import java.util.Scanner;
import calculator.*;

public interface Operations {
    double calculate(Scanner scanner);

    static double getArg(Scanner scanner) {
        if (!scanner.hasNext()) {
            throw new IllegalArgumentException("Not enough arguments");
        }
        if (scanner.hasNextDouble()) {
            return scanner.nextDouble();
        } else {
            Operations operation = Calculator.getOperation(scanner.next());
            return operation.calculate(scanner);
        }
    }

}




