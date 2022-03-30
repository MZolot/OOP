package pizzeria;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class CourierManager implements Runnable {
    final Pizzeria pizzeria;
    AtomicBoolean working;

    CourierManager(Pizzeria pizzeria) {
        this.pizzeria = pizzeria;
        working = new AtomicBoolean();
    }

    boolean isWorking() {
        return working.get();
    }

    @Override
    public void run() {
        working.set(true);
        int couriersAmount = pizzeria.parameters.couriers().size();
        ExecutorService couriersPool = Executors.newFixedThreadPool(couriersAmount);
        Courier[] couriers = new Courier[couriersAmount];
        for (int i = 0; i < couriersAmount; i++) {
            couriers[i] = new Courier(pizzeria, pizzeria.parameters.couriers().get(i));
        }

        while (pizzeria.isOpen() || !pizzeria.storage.isEmpty()) {
            if (pizzeria.storage.isEmpty()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                for (Courier courier : couriers) {
                    if (courier.free) {
                        couriersPool.submit(courier);
                        break;
                    }
                }
            }
        }
        try {
            couriersPool.awaitTermination(10000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        couriersPool.shutdown();
        working.set(false);
    }
}
