package pizzeria;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class CourierManager implements Runnable {
    final Pizzeria pizzeria;

    CourierManager(Pizzeria pizzeria) {
        this.pizzeria = pizzeria;
    }

    @Override
    public void run() {
        int couriersAmount = pizzeria.parameters.couriers().size();
        ExecutorService couriersPool = Executors.newFixedThreadPool(couriersAmount);
        Courier[] couriers = new Courier[couriersAmount];
        for (int i = 0; i < couriersAmount; i++) {
            couriers[i] = new Courier(pizzeria, pizzeria.parameters.couriers().get(i));
        }

        while (pizzeria.isOpen() || (pizzeria.getCompleteOrders() != pizzeria.getOrderNumber() - 1)) {
            /*
            try {
                pizzeria.storage.tryRemove();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
             */
            if (pizzeria.storage.isEmpty()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            for (Courier courier : couriers) {
                if (courier.free) {
                    couriersPool.submit(courier);
                    break;
                }
            }
        }
        couriersPool.shutdown();
        try {
            couriersPool.awaitTermination(11000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
