package pizzeria;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class BakerManager implements Runnable {
    final Pizzeria pizzeria;

    BakerManager(Pizzeria pizzeria) {
        this.pizzeria = pizzeria;
    }

    @Override
    public void run() {
        int bakersAmount = pizzeria.parameters.bakers().size();
        ExecutorService bakersPool = Executors.newFixedThreadPool(bakersAmount);
        Baker[] bakers = new Baker[bakersAmount];
        for (int i = 0; i < bakersAmount; i++) {
            bakers[i] = new Baker(pizzeria, pizzeria.parameters.bakers().get(i));
        }

        while (pizzeria.isOpen() || !pizzeria.queue.isEmpty()) {
            try {
                if (pizzeria.queue.canRemove()) {
                    for (Baker baker : bakers) {
                        if (baker.free) {
                            bakersPool.submit(baker);
                            break;
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        bakersPool.shutdown();
        try {
            bakersPool.awaitTermination(11000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
