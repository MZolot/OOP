package pizzeria;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class BakerManager implements Runnable {
    final Pizzeria pizzeria;
    private final AtomicBoolean working;

    BakerManager(Pizzeria pizzeria) {
        this.pizzeria = pizzeria;
        working = new AtomicBoolean();
    }

    boolean isWorking() {
        return working.get();
    }

    @Override
    public void run() {
        working.set(true);
        int bakersAmount = pizzeria.parameters.bakers().size();
        ExecutorService bakersPool = Executors.newFixedThreadPool(bakersAmount);
        Baker[] bakers = new Baker[bakersAmount];
        for (int i = 0; i < bakersAmount; i++) {
            bakers[i] = new Baker(pizzeria, pizzeria.parameters.bakers().get(i));
        }

        while (pizzeria.isOpen() || !pizzeria.queue.isEmpty()) {
            if (pizzeria.queue.isEmpty()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                for (Baker baker : bakers) {
                    if (baker.free) {
                        bakersPool.submit(baker);
                        break;
                    }
                }
            }
        }
        try {
            bakersPool.awaitTermination(10000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bakersPool.shutdown();
        working.set(false);
    }
}
