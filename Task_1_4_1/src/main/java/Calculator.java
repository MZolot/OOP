import java.util.Scanner;

public class Calculator {

    public double calculate(Scanner scanner) {
        if (!scanner.hasNext()) {
            throw new IllegalArgumentException("Empty input");
        }
        if (scanner.hasNextDouble()) {
            throw new IllegalArgumentException("Expression needs to start with an operation");
        }
        Operations operation0 = getOperation(scanner.next());
        return operation0.calculate(scanner);
    }

    public static Operations getOperation(String operationName) {
        return switch (operationName) {
            case ("sin") -> new Operations.OpSin();
            case ("cos") -> new Operations.OpCos();
            case ("sqrt") -> new Operations.OpSqrt();
            case ("log") -> new Operations.OpLog();
            case ("+") -> new Operations.OpSum();
            case ("-") -> new Operations.OpSub();
            case ("*") -> new Operations.OpMult();
            case ("/") -> new Operations.OpDiv();
            case ("pow") -> new Operations.OpPow();
            default -> throw new IllegalArgumentException("Nonexistent operation");
        };
    }

}

