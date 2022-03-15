package pizzeria;

class Courier implements Runnable {

    final private SynchronizedQueue storage;
    final private int trunkSize;

    Courier(SynchronizedQueue storage, int trunkSize) {
        this.storage = storage;
        this.trunkSize = trunkSize;
    }

    @Override
    public void run() {
        while (true) {
            try {
                int[] orders = storage.take(trunkSize);
                for (int order : orders) {
                    System.out.println(order + " [delivering]");
                }
                Thread.sleep(1000); //delivering
                for (int order : orders) {
                    System.out.println(order + " [delivered]");
                }
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }
}
