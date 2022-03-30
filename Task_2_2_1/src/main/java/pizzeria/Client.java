package pizzeria;

public class Client implements Runnable {
    private final Pizzeria pizzeria;

    public Client(Pizzeria pizzeria) {
        this.pizzeria = pizzeria;
    }

    @Override
    public void run() {
        try {
            while (pizzeria.isOpen()) {
                pizzeria.order();
                Thread.sleep((int) (Math.random() * 1000));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
