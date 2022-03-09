package pizzeria;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Pizzeria {

    private final int storageSize;
    private final Queue<Integer> storage;
    private final Queue<Integer> queue;
    int orderNumber;

    public Pizzeria(int storageSize) {
        this.storageSize = storageSize;
        storage = new ArrayDeque<>();
        queue = new ArrayDeque<>();
        orderNumber = 1;
    }

    synchronized void addToQueue() {
        queue.add(orderNumber);
        System.out.println(orderNumber + " [in queue]");
        orderNumber += 1;
    }

    synchronized int takeFromQueue() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        int order = queue.remove();
        notifyAll();
        return order;
    }

    synchronized void addToStorage(int order) throws InterruptedException {
        while (storage.size() == storageSize) {
            wait();
        }
        storage.add(order);
        System.out.println(order + " [stored]   (" + storage.size() + " in storage)");
        notifyAll();
    }

    synchronized int takeFromStorage() throws InterruptedException {
        while (storage.isEmpty()) {
            wait();
        }
        int order = storage.remove();
        System.out.println(order + " [taken]    (" + storage.size() + " in storage)");
        notifyAll();
        return order;
    }

    public void run(int bakersNumber, int couriersNumber, int orders) {
        for (int i = 0; i < orders; i++)
            this.addToQueue();

        ExecutorService bakers = Executors.newFixedThreadPool(bakersNumber);
        ExecutorService couriers = Executors.newFixedThreadPool(couriersNumber);

        for (int i = 0; i < bakersNumber; i++) {
            Runnable baker = new Baker(this, 10);
            bakers.submit(baker);
        }

        for (int i = 0; i < couriersNumber; i++) {
            Runnable courier = new Courier(this);
            couriers.submit(courier);
        }

    }

    public static void main(String[] args) {
        Pizzeria pizzeria = new Pizzeria(2);
        pizzeria.run(3, 2, 5);
    }

}
