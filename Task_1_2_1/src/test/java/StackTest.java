import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StackTest {

    @Test
    public void testFromExample() {
        Stack<Integer> exampleSt = new Stack<>(Integer.class);
        exampleSt.push(2);
        exampleSt.push(7);
        Stack<Integer> anotherSt = new Stack<>(Integer.class);
        anotherSt.push(4);
        anotherSt.push(8);
        exampleSt.pushStack(anotherSt);
        exampleSt.pop();
        exampleSt.popStack(2);
        Assertions.assertEquals(2, exampleSt.pop());
        Assertions.assertThrows(IndexOutOfBoundsException.class, exampleSt::pop);
        // To demonstrate that 2 was indeed the only element left in stack
    }

    // Testing methods separately
    Stack<Integer> mainSt = new Stack<>(Integer.class);

    @Test
    public void testPush() {
        mainSt.push(1);
        Assertions.assertEquals(1, mainSt.size());
        mainSt.push(2);
        mainSt.push(3);
        Assertions.assertEquals(3, mainSt.size());
    }

    @Test
    public void testPop() {
        mainSt.push(1);
        mainSt.push(2);
        mainSt.push(3);
        Assertions.assertEquals(3, mainSt.pop());
        Assertions.assertEquals(2, mainSt.pop());
        Assertions.assertEquals(1, mainSt.pop());
    }


    @Test
    public void testPushStack() {
        Stack<Integer> anotherSt = new Stack<>(Integer.class);
        anotherSt.push(1);
        anotherSt.push(2);
        anotherSt.push(3);
        mainSt.pushStack(anotherSt);
        Assertions.assertEquals(3, mainSt.pop());
        Assertions.assertEquals(2, mainSt.pop());
        Assertions.assertEquals(1, mainSt.pop());
    }

    @Test
    public void testPopStack() {
        mainSt.push(1);
        mainSt.push(2);
        mainSt.push(3);
        Stack<Integer> poppedSt = mainSt.popStack(2);
        Assertions.assertEquals(3, poppedSt.pop());
        Assertions.assertEquals(2, poppedSt.pop());
        Assertions.assertEquals(1, mainSt.pop());
    }

    @Test
    public void testPopStackExceptionTooManyElements() {
        Stack<Integer> s = new Stack<>(Integer.class);
        s.push(1);
        Assertions.assertThrows(IllegalArgumentException.class, () -> s.popStack(4));
    }

    @Test
    public void testPopStackExceptionNegativeStackSize() {
        Stack<Integer> s = new Stack<>(Integer.class);
        s.push(1);
        Assertions.assertThrows(IllegalArgumentException.class, () -> s.popStack(-1));
    }

    @Test
    public void testPopException() {
        Stack<Integer> s = new Stack<>(Integer.class);
        Assertions.assertThrows(IndexOutOfBoundsException.class, s::pop);
    }

    @Test
    public void testPushStackException() {
        Stack<Integer> s = new Stack<>(Integer.class);
        Assertions.assertThrows(IllegalArgumentException.class, () -> s.pushStack(null));
    }

    @Test
    public void testStringStack() {
        Stack<String> stringStack = new Stack<>(String.class);
        stringStack.push("abcd");
        stringStack.push("efghijk");
        stringStack.push("lmnop");
        Assertions.assertEquals(3, stringStack.size());
        Assertions.assertEquals("lmnop", stringStack.pop());

        Stack<String> anotherStringStack = new Stack<>(String.class);
        anotherStringStack.push("normal");
        anotherStringStack.push("words");
        stringStack.pushStack(anotherStringStack);
        Assertions.assertEquals(4, stringStack.size());
        Assertions.assertEquals("words", stringStack.pop());

        Stack<String> poppedStringStack = stringStack.popStack(2);
        Assertions.assertEquals(1, stringStack.size());
        Assertions.assertEquals("normal", poppedStringStack.pop());
    }
}