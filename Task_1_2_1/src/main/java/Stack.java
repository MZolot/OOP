import java.util.Arrays;

public class Stack<T> {

    public T[] stackElements;
    private int stackLen;
    private int stackCapacity;

    @SuppressWarnings("unckeched")
    public Stack() {
        this.stackCapacity = 10;
        this.stackElements = (T[]) new Object[stackCapacity];
        this.stackLen = 0;
    }

    private void stackExpand(int newCap) {
        T[] newStack;
        newStack = Arrays.copyOf(stackElements, newCap);
        stackCapacity = newCap;
        stackElements = newStack;
    }

    public void push(T x) {
        if (stackLen == stackCapacity) {
            stackExpand(stackCapacity * 2);
        }
        stackElements[stackLen++] = x;
    }

    public void pushStack(Stack<T> st) {
        if (st == null) {
            throw new IllegalArgumentException("Trying to push null stack.");
        }
        int stLen = st.count();
        if (stLen == 0) {
            return;
        }
        if (stackLen + stLen >= stackCapacity) {
            stackExpand(stackCapacity + stLen + 1);
        }
        System.arraycopy(st.stackElements, 0, stackElements, stackLen, stLen);
        stackLen += stLen;
    }

    public T pop() {
        if (stackLen == 0) {
            throw new IndexOutOfBoundsException("Trying to extract elements from empty stack.");
        }
        stackLen--;
        return stackElements[stackLen];
    }

    public int count() {
        return stackLen;
    }


    public Stack<T> popStack(int len) {
        if (stackLen < len || len < 0) {
            throw new IllegalArgumentException("Incorrect elements amount.");
        }
        Stack<T> res = new Stack<>();
        for (int i = (stackLen - len); i < stackLen; i++) {
            res.push(stackElements[i]);
        }
        stackLen -= len;
        return res;
    }

}
