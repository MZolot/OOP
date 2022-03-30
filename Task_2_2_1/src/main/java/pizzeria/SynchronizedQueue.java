package pizzeria;

import java.util.ArrayDeque;
import java.util.Queue;

public class SynchronizedQueue {
    private final int maxSize;
    private final Queue<Integer> orders;
    private final String name;

    SynchronizedQueue(int maxSize, String name) {
        this.maxSize = maxSize;
        this.orders = new ArrayDeque<>();
        this.name = name;
    }

    synchronized void add(int order) throws InterruptedException {
        while (orders.size() == maxSize) {
            wait();
        }
        orders.add(order);
        System.out.println(order + " [stored]   (" + orders.size() + " in " + name + ")");
        this.notifyAll();
    }

    synchronized int remove() throws InterruptedException {
        while (orders.isEmpty()) {
            wait();
        }
        int order = orders.remove();
        System.out.println(order + " [taken]    (" + orders.size() + " in " + name + ")");
        this.notifyAll();
        return order;
    }

    synchronized int[] remove(int maxAmount) throws InterruptedException {
        while (orders.isEmpty()) {
            wait();
        }
        int amount = Math.min(orders.size(), maxAmount);
        int[] ordersOut = new int[amount];
        for (int i = 0; i < amount; i++) {
            ordersOut[i] = orders.remove();
            System.out.println(ordersOut[i] + " [taken]    (" + orders.size() + " in " + name + ")");
        }
        this.notifyAll();
        return ordersOut;
    }

    synchronized boolean isEmpty() {
        return orders.isEmpty();
    }
}
