import java.util.Scanner;

abstract class Operations {
    abstract double calculate(Scanner scanner);
}

class OpSin extends Operations {
    double calculate(Scanner scan){
        double x = Calculator.getArg(scan);
        return Math.sin(x);
    }
}

class OpCos extends Operations {
    double calculate(Scanner scan){
        double x = Calculator.getArg(scan);
        return Math.cos(x);
    }
}

class OpSqrt extends Operations {
    double calculate(Scanner scan){
        double x = Calculator.getArg(scan);
        return Math.sqrt(x);
    }
}

class OpLog extends Operations {
    double calculate(Scanner scan){
        double x = Calculator.getArg(scan);
        return Math.log(x);
    }
}

class OpSum extends Operations {
    double calculate(Scanner scan){
        double a = Calculator.getArg(scan);
        double b = Calculator.getArg(scan);
        return a + b;
    }
}

class OpSub extends Operations {
    double calculate(Scanner scan){
        double a = Calculator.getArg(scan);
        double b = Calculator.getArg(scan);
        return a - b;
    }
}

class OpMult extends Operations {
    double calculate(Scanner scan){
        double a = Calculator.getArg(scan);
        double b = Calculator.getArg(scan);
        return a * b;
    }
}
class OpDiv extends Operations {
    double calculate(Scanner scan){
        double a = Calculator.getArg(scan);
        double b = Calculator.getArg(scan);
        return a / b;
    }
}

class OpPow extends Operations {
    double calculate(Scanner scan){
        double a = Calculator.getArg(scan);
        double b = Calculator.getArg(scan);
        return Math.pow(a, b);
    }
}
