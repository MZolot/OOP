package pizzeria;

public class Client implements Runnable {
    private final Pizzeria pizzeria;

    public Client(Pizzeria pizzeria) {
        this.pizzeria = pizzeria;
    }

    @Override
    public void run() {
        try {
            while (!pizzeria.isOpen()) {
                //waiting for opening
            }
            while (pizzeria.isOpen()) {
                System.out.println("ordering");
                pizzeria.order();
                Thread.sleep((int) (Math.random() * pizzeria.getTimeConstant()));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
