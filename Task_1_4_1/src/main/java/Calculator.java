import java.util.Scanner;

public class Calculator {

     public static double getArg(Scanner scanner) {
        if (!scanner.hasNext()) {
            throw new IllegalArgumentException("Not enough arguments");
        }
        if (scanner.hasNextDouble()) {
            return scanner.nextDouble();
        } else {
            Operations operation = operationsFactory(scanner.next());
            return operation.calculate(scanner);
        }
    }

    public double calculate(Scanner scanner) {
        if (!scanner.hasNext()) {
            throw new IllegalArgumentException("Empty input");
        }
        if (scanner.hasNextDouble()) {
            throw new IllegalArgumentException("Expression needs to start with an operation");
        }
        Operations operation0 = operationsFactory(scanner.next());
        return operation0.calculate(scanner);
    }

    public static Operations operationsFactory(String operationName) {
        return switch (operationName) {
            case ("sin") -> new OpSin();
            case ("cos") -> new OpCos();
            case ("sqrt") -> new OpSqrt();
            case ("log") -> new OpLog();
            case ("+") -> new OpSum();
            case ("-") -> new OpSub();
            case ("*") -> new OpMult();
            case ("/") -> new OpDiv();
            case ("pow") -> new OpPow();
            default -> throw new IllegalArgumentException("Nonexistent operation");
        };
    }

}

