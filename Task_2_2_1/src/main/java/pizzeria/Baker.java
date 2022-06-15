package pizzeria;

class Baker implements Runnable {
    final private SynchronizedQueue queue;
    final private SynchronizedQueue storage;
    final private int bakingTime;
    boolean free;

    Baker(Pizzeria pizzeria, int experience) {
        this.queue = pizzeria.queue;
        this.storage = pizzeria.storage;
        bakingTime = pizzeria.getTimeConstant() / experience;
        this.free = true;
    }

    @Override
    public void run() {
        try {
            this.free = false;
            if(!queue.canRemove()) {
                this.free = true;
                return;
            }
            int order = queue.remove();
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
