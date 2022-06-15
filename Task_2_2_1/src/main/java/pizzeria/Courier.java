package pizzeria;

class Courier implements Runnable {

    final private Pizzeria pizzeria;
    final private SynchronizedQueue storage;
    final private int trunkSize;
    final private int speed;
    boolean free;

    Courier(Pizzeria pizzeria, int trunkSize, int speed) {
        this.pizzeria = pizzeria;
        this.storage = pizzeria.storage;
        this.trunkSize = trunkSize;
        this.speed = speed;
        this.free = true;
    }

    @Override
    public void run() {
        try {
            this.free = false;
            if(!storage.canRemove()) {
                this.free = true;
                return;
            }
            int[] orders = storage.remove(trunkSize);
            for (int order : orders) {
                System.out.println(order + " [delivering]");
            }
            for (int order : orders) {
                Thread.sleep((int) (Math.random() * pizzeria.getTimeConstant() / speed)); //delivering
                System.out.println(order + " [delivered]");
            }
            pizzeria.updateCompleteOrders(orders.length);
            this.free = true;
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}
