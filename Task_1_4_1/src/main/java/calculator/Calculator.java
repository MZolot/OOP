package calculator;

import operations.*;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Map;

public class Calculator {

    private static Map<String, Operations> implementedOperations;

    public Calculator() {
        implementedOperations = new HashMap<>(){
            {
                put("sin", new OpSin());
                put("cos", new OpCos());
                put ("sqrt", new OpSqrt());
                put ("log", new OpLog());
                put ("+", new OpSum());
                put ("-", new OpSub());
                put ("*", new OpMult());
                put ("/", new OpDiv());
                put ("pow", new OpPow());
            }};
    }

    public void addOperations(Map<String, Operations> extraOperations) {
        implementedOperations.putAll(extraOperations);
    }


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
        Operations operation = implementedOperations.get(operationName);
        if (operation==null) {
            throw new IllegalArgumentException("Non-existent operation");
        }
        return operation;
    }

}

