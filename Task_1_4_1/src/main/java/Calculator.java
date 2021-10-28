import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Calculator {

    private final String[] oneArgOperators = {"sqrt", "sin", "cos", "log"};
    private final String[] twoArgOperators = {"+", "-", "*", "/", "pow"};

    private int parser(String[] tokens) {
        String token;
        int firstArg = -1;
        for (int i = 0; i < tokens.length; i++) {
            token = tokens[i];
            if (Arrays.stream(oneArgOperators).noneMatch(token::equals) &&
                    Arrays.stream(twoArgOperators).noneMatch(token::equals) &&
                    !Pattern.matches("\\d+?[.]?\\d*", token)) {
                throw new IllegalArgumentException("Incorrect input");
            }
            if (Pattern.matches("\\d+?[.]?\\d*", token) && firstArg == -1) {
                firstArg = i;
            }
        }
        if (firstArg == -1) {
            throw new IllegalArgumentException("Incorrect input: no arguments");
        }
        return firstArg;
    }

    public double calculate(Scanner scanner) {
        String expression = scanner.nextLine();
        String[] tokens = expression.split(" ");
        int firstArg = parser(tokens);
        int currentArg = firstArg + 1;
        double res = Double.parseDouble(tokens[firstArg]);
        for (int i = firstArg - 1; i >= 0; i--) {
            if (Arrays.asList(oneArgOperators).contains(tokens[i])) {
                res = operation(tokens[i], res, 0);
            } else {
                if (currentArg >= tokens.length) {
                    throw new IndexOutOfBoundsException("Less arguments than operations require");
                }
                res = operation(tokens[i], res, Double.parseDouble(tokens[currentArg++]));
            }
        }
        return res;
    }

    private double operation(String operationName, double a, double b) {
        return switch (operationName) {
            case ("sin") -> Math.sin(a);
            case ("cos") -> Math.cos(a);
            case ("sqrt") -> Math.sqrt(a);
            case ("log") -> Math.log(a);
            case ("+") -> a + b;
            case ("-") -> a - b;
            case ("*") -> a * b;
            case ("/") -> a / b;
            case ("pow") -> Math.pow(a, b);
            default -> 0;
        };
    }

}
