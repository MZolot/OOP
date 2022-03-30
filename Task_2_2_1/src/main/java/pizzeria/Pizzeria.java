package pizzeria;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class Pizzeria {

    record Parameters(List<Integer> bakers, List<Integer> couriers) {
    }

    final SynchronizedQueue queue;
    final SynchronizedQueue storage;
    final Parameters parameters;
    final BakerManager bakerManager;
    final CourierManager courierManager;
    int orderNumber;
    AtomicBoolean open;
    private final ExecutorService managers;

    public Pizzeria(File parametersFile, int queueSize, int storageSize) throws IOException {
        open = new AtomicBoolean();
        queue = new SynchronizedQueue(queueSize, "queue");
        storage = new SynchronizedQueue(storageSize, "storage");
        orderNumber = 1;
        bakerManager = new BakerManager(this);
        courierManager = new CourierManager(this);

        Deserializer deserializer = new Deserializer(parametersFile);
        parameters = deserializer.deserializeParameters();

        managers = Executors.newFixedThreadPool(2);
    }

    public boolean isOpen() {
        return open.get();
    }

    synchronized public void order() throws InterruptedException {
        queue.add(orderNumber);
        orderNumber++;
    }

    public void open(int ordersAmount) throws InterruptedException {
        open.set(true);
        for (int i = 1; i <= ordersAmount; i++)
            order();
        managers.submit(bakerManager);
        managers.submit(courierManager);
    }

    public void close() {
        open.set(false);
        while (bakerManager.isWorking() || courierManager.isWorking()) {}
        managers.shutdown();
        System.exit(0);
    }

    public void run(int ordersAmount) throws InterruptedException {
        open(ordersAmount);
        Thread.sleep(5000);
        close();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Pizzeria pizzeria = new Pizzeria(new File("workers.json"), 100, 2);
        pizzeria.run(6);
    }

}
