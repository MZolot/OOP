import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Consumer;

public class Tree<E> implements Collection<E> {

    private static class Node<E> {
        private final E value;
        private final List<Node<E>> children;
        private Node<E> parent;

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

    /**
     * @return Returns size of the tree
     */
    public int size() {
        return treeSize;
    }

    /**
     * @return Returns true if the tree is empty
     */
    public boolean isEmpty() {
        return this.root == null;
    }

    /**
     * Checks if the object is in the tree
     * @param o - the object that is searched in the tree
     * @return - returns true if the tree contains the element
     */
    public boolean contains(Object o) {
        if (this.root == null) {
            throw new IllegalStateException("The tree is empty");
        }
        breadthFirstIterator<E> bfi = new breadthFirstIterator<>(this.root);
        while (bfi.hasNext()) {
            if (bfi.next().equals(o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns iterator that iterates tree using a breadth first algorithm
     * @return Returns Breadth First iterator
     */
    public Iterator<E> iterator() {
        return new breadthFirstIterator<>(this.root);
    }

    /**
     * @return Returns an array with values of all nodes
     */
    public Object[] toArray() {
        if (root == null) {
            throw new IllegalStateException("The tree is empty");
        }
        Object[] array = new Object[this.size()];
        Iterator<E> it = this.iterator();
        int j = 0;
        while (it.hasNext()) {
            array[j++] = it.next();
        }
        return array;
    }

    /**
     * @param a - the array where values are put
     * @param <E> - type of the objects in the array and in the tree
     * @return Returns an array with values of all nodes
     */
    public <E> E[] toArray(E[] a) {
        if (root == null) {
            throw new IllegalStateException("The tree is empty");
        }
        if (a == null) {
            throw new IllegalStateException("The specified array is null");
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

    /**
     * Adds new node with value e to the tree
     * @param e - value of the new node
     * @return Returns true if new node was successfully added
     */
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

    /**
     * Removes node with first entry of the object from the tree
     * @param o - value that is deleted
     * @return Returns true if the object was successfully deleted
     */
    public boolean remove(Object o) {
        if (this.root == null) {
            throw new IllegalStateException("The tree is empty");
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

    /**
     * Checks if the tree contains all elements from the collection
     * @param c the collection of objects that are searched in the tree
     * @return - returns true if the tree contains all objects in the collection
     */
    public boolean containsAll(Collection<?> c) {
        if (this.root == null) {
            throw new IllegalStateException("The tree is empty");
        }
        return c.stream().allMatch(this::contains);
    }

    /**
     * Adds all elements of the collection to the tree
     * @param c - collection of elements to be added
     * @return Returns true if all elements were added successfully
     */
    public boolean addAll(Collection<? extends E> c) {
        boolean success = true;
        for (E e : c) {
            success &= this.add(e);
        }
        return success;
    }

    /**
     * Removes all elements of the collection from the tree
     * @param c - collection of elements to be removed
     * @return Returns true if all elements were removed successfully
     */
    public boolean removeAll(Collection<?> c) {
        if (root == null) {
            throw new IllegalStateException("The tree is empty");
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


    /**
     * Removes all elements that are not in the collection from the tree
     * @param c - collection of elements to keep
     * @return Returns true if all other elements were removed successfully
     */
    public boolean retainAll(Collection<?> c) {
        if (this.root == null) {
            throw new IllegalStateException("The tree is empty");
        }
        List<Object> elementsToDelete = new ArrayList<>();
        breadthFirstIterator<E> bfi = new breadthFirstIterator<>(this.root);
        while (bfi.hasNext()) {
            Object o = bfi.next();
            if (!c.contains(o)) {
                elementsToDelete.add(o);
            }
        }
        return removeAll(elementsToDelete);
    }

    /**
     * Clears the tree
     * (makes tree empty)
     */
    public void clear() {
        root = null;
        this.treeSize = 0;
    }

    private static class breadthFirstIterator<E> implements Iterator<E> {

        private final Deque<Tree.Node<E>> queue;

        public breadthFirstIterator(Node<E> root) {
            queue = new ArrayDeque<>();
            queue.add(root);
        }

        public boolean hasNext() {
            return !queue.isEmpty();
        }

        public E next() {
            Node<E> nextNode = queue.getFirst();
            queue.addAll(nextNode.children);
            queue.removeFirst();
            return nextNode.value;
        }

        public Node<E> nextNode() {
            Node<E> nextNode = queue.getFirst();
            queue.addAll(nextNode.children);
            queue.removeFirst();
            return nextNode;
        }

    }

    /**
     * Prints values of all nodes
     */
    public void printBreadthFirst() {
        Iterator<E> iterator = new breadthFirstIterator<>(this.root);
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    /**
     * Prints structure of the tree
     * Prints value of a node and then values of all its children
     */
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

    /**
     * Returns spliterator that uses a breadth first algorithm
     * @return Returns Breadth First iterator
     */
    public Spliterator<E> spliterator() {
        return new breadthFirstSpliterator<>(this);
    }

    private static class breadthFirstSpliterator<E> implements Spliterator<E> {

        private final Deque<Node<E>> queue;
        private final Tree<E> tree;

        public breadthFirstSpliterator(Tree<E> tree) {
            queue = new ArrayDeque<>();
            queue.add(tree.root);
            this.tree = tree;
        }

        @Override
        public boolean tryAdvance(Consumer<? super E> action) {
            if (queue.isEmpty()) {
                return false;
            }
            Node<E> nextNode = queue.getFirst();
            queue.addAll(nextNode.children);
            queue.removeFirst();
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