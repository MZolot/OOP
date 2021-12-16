import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Tree<E> implements Collection<E> {

    private int treeSize;
    private E value;
    public List<Tree<E>> children;

    public Tree() {
        treeSize = 0;
        children = new ArrayList<>();
    }

    public int size() {
        return treeSize;
    }

    public boolean isEmpty() {
        return treeSize == 0;
    }

    public boolean contains(Object o) {
        if (treeSize == 0) {
            throw new NullPointerException("Tree is empty");
        }
        for (E e : this) {
            if (e.equals(o)) {
                return true;
            }
        }
        return false;
    }

    public Iterator<E> iterator() {
        return depthFirstIterator();
    }

    public Object[] toArray() {
        if (treeSize == 0) {
            throw new NullPointerException("Tree is empty");
        }
        Object[] array = new Object[treeSize];
        Iterator<E> it = iterator();
        int j = 0;
        while (it.hasNext()) {
            array[j++] = it.next();
        }
        return array;
    }

    public <E> E[] toArray(E[] a) {
        if (treeSize == 0) {
            throw new NullPointerException("Tree is empty");
        }
        return null;
    }

    public boolean add(E e) {
        return true;
    }

    public boolean remove(Object o) {
        if (treeSize == 0) {
            throw new NullPointerException("Tree is empty");
        }
        return false;
    }

    public boolean containsAll(Collection<?> c) {
        if (treeSize == 0) {
            throw new NullPointerException("Tree is empty");
        }
        Iterator<?> it = c.iterator();
        boolean res = true;
        while (it.hasNext()) {
            res = res && contains(it.next());
        }
        return res;
    }

    public boolean addAll(Collection<? extends E> c) {
        for (E e : c) {
            this.add(e);
        }
        return false;
    }

    public boolean removeAll(Collection<?> c) {
        if (treeSize == 0) {
            throw new NullPointerException("Tree is empty");
        }
        return false;
    }

    public boolean retainAll(Collection<?> c) {
        return false;
    }

    public void clear() {
    }

    public Iterator<E> depthFirstIterator() {
        return null;
    }

    public Iterator<E> breadthFirstIterator() {
        return null;
    }

}
