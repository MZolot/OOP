package pizzeria;

class Courier implements Runnable {

    final private Pizzeria pizzeria;
    final private SynchronizedQueue storage;
    final private int trunkSize;
    boolean free;


    Courier(Pizzeria pizzeria, int trunkSize) {
        this.pizzeria = pizzeria;
        this.storage = pizzeria.storage;
        this.trunkSize = trunkSize;
        this.free = true;
    }

    @Override
    public void run() {
        try {
            this.free = false;
            int[] orders = storage.remove(trunkSize);
            for (int order : orders) {
                System.out.println(order + " [delivering]");
            }
            for (int order : orders) {
                Thread.sleep((int) (Math.random() * 10000)); //delivering
                System.out.println(order + " [delivered]");
            }
            pizzeria.updateCompleteOrders(orders.length);
            this.free = true;
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}
