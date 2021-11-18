import java.util.Scanner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CalculatorTest {
    @ParameterizedTest
    @CsvSource(value = {"/ - * + 1 2 3 4 5=1", "sqrt 4=2", "pow 2 10=1024", "log cos 0=0", "sin + - 1 2 1=0", "- 5 * 6 7=-37"},
            delimiter = '=')
    void testSubstringSearch(String expression, String correctRes) {
        Scanner scanner = new Scanner(expression);
        Calculator calculator = new Calculator();
        double res = calculator.calculate(scanner);
        Assertions.assertEquals(Double.parseDouble(correctRes), res);
    }

    @Test
    void testNotEnoughArguments() {
        Scanner scanner = new Scanner("+ 1");
        Calculator calculator = new Calculator();
        Assertions.assertThrows(IllegalArgumentException.class, () -> calculator.calculate(scanner));
    }

    @Test
    void testNoArguments() {
        Scanner scanner = new Scanner("+ sin log -");
        Calculator calculator = new Calculator();
        Assertions.assertThrows(IllegalArgumentException.class, () -> calculator.calculate(scanner));
    }

    @Test
    void testIncorrectOperation() {
        Scanner scanner = new Scanner("ln 3");
        Calculator calculator = new Calculator();
        Assertions.assertThrows(IllegalArgumentException.class, () -> calculator.calculate(scanner));
    }

}