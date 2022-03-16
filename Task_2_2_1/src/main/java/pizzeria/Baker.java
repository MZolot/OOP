package pizzeria;

class Baker implements Runnable {
    final private SynchronizedQueue queue;
    final private SynchronizedQueue storage;
    final private int bakingTime;
    boolean free;

    Baker(SynchronizedQueue queue, SynchronizedQueue storage, int experience) {
        this.queue = queue;
        this.storage = storage;
        bakingTime = 10000 / experience;
        this.free = true;
    }

    @Override
    public void run() {
        try {
            int order = queue.take();
            this.free = false;
            System.out.println(order + " [started baking]");
            Thread.sleep(bakingTime); //baking
            System.out.println(order + " [baked]");
            storage.add(order);
            this.free = true;
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

}
