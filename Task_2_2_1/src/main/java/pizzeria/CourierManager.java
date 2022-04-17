package pizzeria;

import java.util.concurrent.*;

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
            couriers[i] = new Courier(pizzeria,
                    pizzeria.parameters.couriers().get(i)[0],
                    pizzeria.parameters.couriers().get(i)[1]);
        }

        while (pizzeria.isOpen() || (pizzeria.getCompleteOrders() != pizzeria.getOrderNumber() - 1)) {
            try {
                if (pizzeria.storage.canRemove()) {
                    for (Courier courier : couriers) {
                        if (courier.free) {
                            couriersPool.submit(courier);
                            break;
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        couriersPool.shutdown();
        try {
            couriersPool.awaitTermination(21000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
