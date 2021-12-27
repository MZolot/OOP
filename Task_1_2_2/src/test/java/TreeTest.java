import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TreeTest {

    @Test
    public void testIsEmpty() {
        Tree<Integer> tree = new Tree<>();
        assertTrue(tree.isEmpty());
        tree.add(0);
        assertFalse(tree.isEmpty());
    }

    @Test
    public void testSize() {
        Tree<Integer> tree = new Tree<>();
        Assertions.assertEquals(0, tree.size());
        tree.add(1);
        Assertions.assertEquals(1, tree.size());
        tree.add(2);
        tree.add(3);
        tree.remove(1);
        Assertions.assertEquals(2, tree.size());
    }

    @Test
    public void testContains() {
        Tree<Integer> tree = new Tree<>();
        tree.add(1);
        tree.add(2);
        tree.add(3);
        Assertions.assertTrue(tree.contains(1));
        Assertions.assertFalse(tree.contains(5));
    }

    @Test
    public void testContainsAll() {
        Tree<Integer> tree = new Tree<>();
        tree.add(1);
        tree.add(2);
        tree.add(3);
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        Assertions.assertTrue(tree.containsAll(list));
        list.add(4);
        Assertions.assertFalse(tree.containsAll(list));
    }

    @Test
    public void testToArray() {
        Tree<Integer> tree = new Tree<>();
        tree.add(1);
        tree.add(2);
        tree.add(3);
        Integer[] array = {1, 2, 3};
        Assertions.assertArrayEquals(array, tree.toArray());
    }

    @Test
    public void testToArray2() {
        Tree<Integer> tree = new Tree<>();
        tree.add(1);
        tree.add(2);
        tree.add(3);
        Integer[] array = {1, 2, 3};
        Integer[] a = new Integer[3];
        tree.toArray(a);
        Assertions.assertArrayEquals(array, a);
    }

    @Test
    public void testAddAll() {
        Tree<Integer> tree = new Tree<>();
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        tree.addAll(list);
        Assertions.assertTrue(tree.containsAll(list));
    }

    @Test
    public void testRemoveAll() {
        Tree<Integer> tree = new Tree<>();
        tree.add(1);
        tree.add(2);
        tree.add(3);
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        tree.removeAll(list);
        Assertions.assertFalse(tree.containsAll(list));
        Assertions.assertFalse(tree.contains(1));
        Assertions.assertFalse(tree.contains(2));
        Assertions.assertTrue(tree.contains(3));
    }

    @Test
    public void testRetainAll() {
        Tree<Integer> tree = new Tree<>();
        tree.add(1);
        tree.add(2);
        tree.add(3);
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        tree.retainAll(list);
        Assertions.assertTrue(tree.containsAll(list));
        Assertions.assertTrue(tree.contains(1));
        Assertions.assertTrue(tree.contains(2));
        Assertions.assertFalse(tree.contains(3));
    }

    @Test
    public void testClear() {
        Tree<Integer> tree = new Tree<>();
        tree.add(1);
        tree.add(2);
        tree.add(3);
        tree.clear();
        Assertions.assertTrue(tree.isEmpty());
        Assertions.assertEquals(0, tree.size());
    }

    @Test
    public void testStreamAPI() {
        Tree<Integer> tree = new Tree<>();
        tree.add(1);
        tree.add(2);
        tree.add(3);
        tree.add(4);
        Object[] treeArray = tree.stream().filter(a -> a % 2 == 0).toArray();
        Integer[] array = {2, 4};
        Assertions.assertArrayEquals(array, treeArray);
    }

    @Test
    public void testStreamAPIFromExample() {
        Tree<String> tree = new Tree<>();
        tree.add("A");
        tree.add("B");
        tree.add("AB");
        tree.add("BB");
        Object[] treeArray = tree.stream().filter(a -> a.contains("B")).toArray();
        String[] array = {"B", "AB", "BB"};
        Assertions.assertArrayEquals(array, treeArray);
    }
}