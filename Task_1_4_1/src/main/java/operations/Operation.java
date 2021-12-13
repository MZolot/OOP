package operations;

import java.util.Scanner;
import calculator.*;

public interface Operation {
    double calculate(Scanner scanner);

    static double getArg(Scanner scanner) {
        if (!scanner.hasNext()) {
            throw new IllegalArgumentException("Not enough arguments");
        }
        if (scanner.hasNextDouble()) {
            return scanner.nextDouble();
        } else {
            Operation operation = Calculator.getOperation(scanner.next());
            return operation.calculate(scanner);
        }
    }

}




