package pizzeria;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Pizzeria implements Runnable{

    record Parameters(int queueSize, int storageSize, List<Integer> bakers, List<Integer[]> couriers) {
    }

    final SynchronizedQueue queue;
    final SynchronizedQueue storage;
    final Parameters parameters;
    final int timeConstant = 10000;
    private int orderNumber;
    private AtomicInteger completeOrders;
    private AtomicBoolean open;
    private final ExecutorService managers;

    public Pizzeria(File parametersFile) throws IOException {
        open = new AtomicBoolean();
        orderNumber = 1;
        completeOrders = new AtomicInteger(0);

        Deserializer deserializer = new Deserializer(parametersFile);
        this.parameters = deserializer.deserializeParameters();
        queue = new SynchronizedQueue(parameters.queueSize, "queue");
        storage = new SynchronizedQueue(parameters.storageSize, "storage");
        managers = Executors.newFixedThreadPool(2);
    }

    public int getTimeConstant() {
        return timeConstant;
    }

    public boolean isOpen() {
        return open.get();
    }

    synchronized public void order() throws InterruptedException {
        queue.add(orderNumber);
        orderNumber++;
    }

    void updateCompleteOrders(int completed) {
        completeOrders.set(completeOrders.get() + completed);
    }

    int getCompleteOrders() {
        return completeOrders.get();
    }

    int getOrderNumber() {
        return orderNumber;
    }

    public void run() {
        System.out.println("OPEN");
        open.set(true);
        managers.submit(new BakerManager(this));
        managers.submit(new CourierManager(this));
    }

    public void close() throws InterruptedException {
        System.out.println("CLOSING");
        open.set(false);
        managers.shutdown();
        managers.awaitTermination(30, TimeUnit.SECONDS);
        System.out.println("CLOSED");
    }

}
