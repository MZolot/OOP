package pizzeria;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Manager {
    final Pizzeria pizzeria;

    Manager(Pizzeria pizzeria) {
        this.pizzeria = pizzeria;
    }

    void manage() {
        int bakersAmount = pizzeria.parameters.bakersAmt();
        ExecutorService bakersPool = Executors.newFixedThreadPool(bakersAmount);
        Baker[] bakers = new Baker[bakersAmount];
        for (int i =0; i<bakersAmount; i++) {
            bakers[i] = new Baker(pizzeria.queue, pizzeria.storage, pizzeria.parameters.bakers()[i]);
        }

        while(!pizzeria.queue.isEmpty()) {
            for (Baker i : bakers) {
                if (i.free) {
                    bakersPool.submit(i);
                    break;
                }
            }
        }
    }
}
