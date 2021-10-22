import java.lang.reflect.Array;
import java.util.Arrays;

public class Stack<T> {

    public T[] stackElements;
    private int stackLen;
    private final Class<T> stackClass;

    public Stack(Class<T> cl) {
        this.stackElements = (T[]) Array.newInstance(cl, 10);
        //this.stackElements = (T[]) new Object[10];
        this.stackLen = 0;
        this.stackClass = cl;
    }

    private void stackExpand(int newCap) {
        T[] newStack;
        newStack = Arrays.copyOf(stackElements, newCap);
        stackElements = newStack;
    }

    public void push(T x) {
        if (stackLen == stackElements.length) {
            stackExpand(stackElements.length * 2);
        }
        stackElements[stackLen++] = x;
    }

    public void pushStack(Stack<T> st) {
        if (st == null) {
            throw new IllegalArgumentException("Trying to push null stack.");
        }
        int stLen = st.size();
        if (stLen == 0) {
            return;
        }
        if (stackLen + stLen >= stackElements.length) {
            stackExpand(stackElements.length + stLen + 1);
        }
        System.arraycopy(st.stackElements, 0, stackElements, stackLen, stLen);
        stackLen += stLen;
    }

    public T pop() {
        if (stackLen == 0) {
            throw new IndexOutOfBoundsException("Trying to extract elements from empty stack.");
        }
        stackLen--;
        T res = stackElements[stackLen];
        stackElements[stackLen] = null;
        return res;
    }

    public int size() {
        return stackLen;
    }

    public Stack<T> popStack(int len) {
        if (stackLen < len || len < 0) {
            throw new IllegalArgumentException("Incorrect elements amount.");
        }
        Stack<T> res = new Stack<>(stackClass);
        for (int i = (stackLen - len); i < stackLen; i++) {
            res.push(stackElements[i]);
        }
        stackLen -= len;
        return res;
    }

}
