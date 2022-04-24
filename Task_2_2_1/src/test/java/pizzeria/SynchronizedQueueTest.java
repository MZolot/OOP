package pizzeria;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SynchronizedQueueTest {

    @Test
    void testIsEmpty() throws InterruptedException {
        SynchronizedQueue queue = new SynchronizedQueue(10, "testIsEmpty");
        Assertions.assertTrue(queue.isEmpty());
        queue.add(1);
        Assertions.assertFalse(queue.isEmpty());
    }

    @Test
    void testCanRemove() throws InterruptedException {
        SynchronizedQueue queue = new SynchronizedQueue(10, "testCanRemove");
        Assertions.assertFalse(queue.canRemove());
        queue.add(1);
        Assertions.assertTrue(queue.canRemove());
    }

    @Test
    void testRemove() throws InterruptedException {
        SynchronizedQueue queue = new SynchronizedQueue(10, "testRemove");
        queue.add(1);
        queue.add(2);
        Assertions.assertEquals(1, queue.remove());
        Assertions.assertEquals(2, queue.remove());
        Assertions.assertTrue(queue.isEmpty());
    }

    @Test
    void testRemoveArray() throws InterruptedException {
        SynchronizedQueue queue = new SynchronizedQueue(10, "testRemoveArray");
        queue.add(1);
        queue.add(2);
        int[] expected = {1, 2};
        Assertions.assertArrayEquals(expected, queue.remove(2));
        Assertions.assertTrue(queue.isEmpty());
    }

}