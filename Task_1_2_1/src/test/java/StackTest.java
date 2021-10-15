//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StackTest {



    @Test
    public void testPopStackException() {
        Stack<Integer> s = new Stack<>();
        s.push(1);
        Assertions.assertThrows(IllegalArgumentException.class, () -> s.popStack(4));
    }

    @Test
    public void testPopStackException2() {
        Stack<Integer> s = new Stack<>();
        s.push(1);
        Assertions.assertThrows(IllegalArgumentException.class, () -> s.popStack(-1));
    }

    @Test
    public void testPopException() {
        Stack<Integer> s = new Stack<>();
        Assertions.assertThrows(IndexOutOfBoundsException.class, s::pop);
    }

    @Test
    public void testPusStackException() {
        Stack<Integer> s = new Stack<>();
        Assertions.assertThrows(IllegalArgumentException.class, () -> s.pushStack(null));
    }
}