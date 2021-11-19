import java.util.Scanner;

interface Operations {
    double calculate(Scanner scanner);

    default double getArg(Scanner scanner) {
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

    class OpSin implements Operations {
        public double calculate(Scanner scan){
            double x = getArg(scan);
            return Math.sin(x);
        }
    }

    class OpCos implements Operations {
        public double calculate(Scanner scan){
            double x = getArg(scan);
            return Math.cos(x);
        }
    }

    class OpSqrt implements Operations {
        public double calculate(Scanner scan){
            double x = getArg(scan);
            return Math.sqrt(x);
        }
    }

    class OpLog implements Operations {
        public double calculate(Scanner scan){
            double x = getArg(scan);
            return Math.log(x);
        }
    }

    class OpSum implements Operations {
        public double calculate(Scanner scan){
            double a = getArg(scan);
            double b = getArg(scan);
            return a + b;
        }
    }

    class OpSub implements Operations {
        public double calculate(Scanner scan){
            double a = getArg(scan);
            double b = getArg(scan);
            return a - b;
        }
    }

    class OpMult implements Operations {
        public double calculate(Scanner scan){
            double a = getArg(scan);
            double b = getArg(scan);
            return a * b;
        }
    }
    class OpDiv implements Operations {
        public double calculate(Scanner scan){
            double a = getArg(scan);
            double b = getArg(scan);
            return a / b;
        }
    }

    class OpPow implements Operations {
        public double calculate(Scanner scan) {
            double a = getArg(scan);
            double b = getArg(scan);
            return Math.pow(a, b);
        }
    }
}




