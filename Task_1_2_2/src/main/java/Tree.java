import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Consumer;

public class Tree<E> implements Collection<E> {

    public static class Node<E> {
        public E value;
        public List<Node<E>> children;
        public Node<E> parent;

        private Node(E value, Node<E> parent) {
            this.value = value;
            this.children = new ArrayList<>();
            this.parent = parent;
        }

        private void addChild(E childValue) {
            Node<E> child = new Node<>(childValue, this);
            children.add(child);
        }

        private void addChild(Node<E> newChild) {
            children.add(newChild);
        }

        private void removeChild(Node<E> child) {
            if (!this.children.contains(child)) {
                throw new IllegalArgumentException("Node does not have this child.");
            }
            this.children.remove(child);
            if (!child.children.isEmpty()) {
                Node<E> newChild = child.children.get(0);
                child.children.remove(0);
                newChild.children.addAll(child.children);
                for (Node n : newChild.children) {
                    n.parent = newChild;
                }
                this.children.add(newChild);
            }
        }
    }

    private Node<E> root;
    private int treeSize;

    public Tree() {
        this.root = null;
        this.treeSize = 0;
    }

    public int size() {
        return treeSize;
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    public boolean contains(Object o) {
        if (this.root == null) {
            throw new NullPointerException("The tree is empty");
        }
        return Arrays.asList(this.toArray()).contains(o);
    }

    public Iterator<E> iterator() {
        return new breadthFirstIterator<>(this.root);
    }

    public Object[] toArray() {
        if (root == null) {
            throw new NullPointerException("The tree is empty");
        }
        Object[] array = new Object[this.size()];
        Iterator<E> it = this.iterator();
        int j = 0;
        while (it.hasNext()) {
            array[j++] = it.next();
        }
        return array;
    }

    public <E> E[] toArray(E[] a) {
        if (root == null) {
            throw new NullPointerException("The tree is empty");
        }
        if (a == null) {
            throw new NullPointerException("The specified array is null");
        }
        if (a.length < this.treeSize) {
            a = (E[]) Array.newInstance(a.getClass(), this.treeSize);
        }
        Iterator<E> it = (Iterator<E>) this.iterator();
        int j = 0;
        while (it.hasNext()) {
            a[j++] = it.next();
        }
        return a;
    }

    public boolean add(E e) {
        if (this.size() == 0) {
            root = new Node<>(e, null);
            this.treeSize = 1;
            return true;
        }
        breadthFirstIterator<E> iterator = new breadthFirstIterator<>(this.root);
        while (iterator().hasNext()) {
            Node<E> current = iterator.nextNode();
            if (current.children.size() < 2) {
                current.addChild(e);
                this.treeSize++;
                return true;
            }
        }
        return false;
    }

    public boolean remove(Object o) {
        if (this.root == null) {
            throw new NullPointerException("The tree is empty");
        }
        if (this.root.value.equals(o)) {
            Node<E> newRoot = root.children.get(0);
            root.children.remove(0);
            newRoot.children.addAll(root.children);
            for (Node n : newRoot.children) {
                n.parent = newRoot;
            }
            newRoot.parent = null;
            root = newRoot;
            this.treeSize--;
            return true;
        }
        breadthFirstIterator<E> iterator = new breadthFirstIterator<>(this.root);
        while (iterator().hasNext()) {
            Node<E> current = iterator.nextNode();
            if (current.value.equals(o)) {
                this.treeSize--;
                current.parent.removeChild(current);
                return true;
            }
        }
        return false;
    }

    public boolean containsAll(Collection<?> c) {
        if (this.root == null) {
            throw new NullPointerException("The tree is empty");
        }
        return Arrays.asList(this.toArray()).containsAll(c);
    }

    public boolean addAll(Collection<? extends E> c) {
        boolean success = true;
        for (E e : c) {
            success &= this.add(e);
        }
        return success;
    }

    public boolean removeAll(Collection<?> c) {
        if (root == null) {
            throw new NullPointerException("The tree is empty");
        }
        if (this.treeSize < c.size()) {
            throw new IndexOutOfBoundsException("List for removing has more object than the tree");
        }
        boolean success = true;
        for (Object e : c) {
            success &= this.remove(e);
        }
        return success;
    }

    public boolean retainAll(Collection<?> c) {
        if (this.root == null) {
            throw new NullPointerException("The tree is empty");
        }
        Object[] treeArray = this.toArray();
        List<Object> elementsToDelete = new ArrayList<>();
        for (Object o : treeArray) {
            if (!c.contains(o)) {
                elementsToDelete.add(o);
            }
        }
        return removeAll(elementsToDelete);
    }

    public void clear() {
        root = null;
        this.treeSize = 0;
    }

    public static class breadthFirstIterator<E> implements Iterator<E> {

        private final List<Node<E>> queue;

        public breadthFirstIterator(Node<E> root) {
            queue = new ArrayList<>();
            queue.add(root);
        }

        public boolean hasNext() {
            return !queue.isEmpty();
        }

        public E next() {
            Node<E> nextNode = queue.get(0);
            queue.addAll(nextNode.children);
            queue.remove(0);
            return nextNode.value;
        }

        public Node<E> nextNode() {
            Node<E> nextNode = queue.get(0);
            queue.addAll(nextNode.children);
            queue.remove(0);
            return nextNode;
        }

    }

    public void printBreadthFirst() {
        Iterator<E> iterator = new breadthFirstIterator<>(this.root);
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    public void printStructure() {
        breadthFirstIterator<E> iterator = new breadthFirstIterator<>(this.root);
        while (iterator.hasNext()) {
            Node<E> current = iterator.nextNode();
            System.out.println(current.value);
            System.out.print("Children: ");
            for (Node n : current.children) {
                System.out.print(n.value);
                System.out.print(", ");
            }
            System.out.print("\n");
        }
    }

    public Spliterator<E> spliterator() {
        return new breadthFirstSpliterator<>(this);
    }

    public static class breadthFirstSpliterator<E> implements Spliterator<E> {

        private final List<Node<E>> queue;
        private final Tree<E> tree;

        public breadthFirstSpliterator(Tree<E> tree) {
            queue = new ArrayList<>();
            queue.add(tree.root);
            this.tree = tree;
        }

        @Override
        public boolean tryAdvance(Consumer<? super E> action) {
            if (queue.isEmpty()) {
                return false;
            }
            Node<E> nextNode = queue.get(0);
            queue.addAll(nextNode.children);
            queue.remove(0);
            action.accept(nextNode.value);
            return true;
        }

        @Override
        public Spliterator<E> trySplit() {
            Tree newTree = new Tree();
            if (tree.treeSize < 2) {
                return null;
            }
            newTree.root = tree.root.children.get(0);
            tree.remove(tree.root.children.get(0));
            return new breadthFirstSpliterator<>(newTree);
        }

        @Override
        public long estimateSize() {
            return tree.treeSize;
        }

        @Override
        public int characteristics() {
            return SIZED | CONCURRENT | DISTINCT;
        }
    }
}