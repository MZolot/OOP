package pizzeria;

class Baker implements Runnable {
    final private SynchronizedQueue queue;
    final private SynchronizedQueue storage;
    final private int bakingTime;
    boolean busy;

    Baker(SynchronizedQueue queue, SynchronizedQueue storage, int experience) {
        this.queue = queue;
        this.storage = storage;
        bakingTime = 10000 / experience;
        busy = false;
    }

    @Override
    public void run() {
        try {
            int order = queue.take();
            busy = true;
            System.out.println(order + " [started baking]");
            Thread.sleep(bakingTime); //baking
            System.out.println(order + " [baked]");
            storage.add(order);
            busy = false;
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

}
