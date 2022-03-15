package pizzeria;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Pizzeria {

    record Parameters(int bakersAmt, Integer[] bakers, int couriersAmt, Integer[] couriers) {
    }

    final SynchronizedQueue storage;
    final SynchronizedQueue queue;
    final Parameters parameters;
    int orderNumber;

    public Pizzeria(File parametersFile, int queueSize, int storageSize) throws IOException {
        queue = new SynchronizedQueue(queueSize, "queue");
        storage = new SynchronizedQueue(storageSize, "storage");
        orderNumber = 1;
        Deserializer deserializer = new Deserializer(parametersFile);
        parameters = deserializer.deserialize();
    }

    public void run(int ordersAmount) throws InterruptedException {
        for (int i = 1; i <= ordersAmount; i++)
            queue.add(i);

        ExecutorService bakers = Executors.newFixedThreadPool(parameters.bakersAmt);
        ExecutorService couriers = Executors.newFixedThreadPool(parameters.couriersAmt);

        for (int i : parameters.bakers) {
            Runnable baker = new Baker(this.queue, this.storage, i);
            bakers.submit(baker);
        }

        for (int i : parameters.couriers) {
            Runnable courier = new Courier(this.storage, i);
            couriers.submit(courier);
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        Pizzeria pizzeria = new Pizzeria(new File("workers.json"), 100, 2);
        pizzeria.run(6);
    }

}
