package pizzeria;

class Baker implements Runnable {
    final private Pizzeria pizzeria;
    final private int bakingTime;

    Baker(Pizzeria pizzeria, int experience) {
        this.pizzeria = pizzeria;
        bakingTime = 10000 / experience;
    }

    @Override
    public void run() {
        while (true) {
            try {
                int order = pizzeria.takeFromQueue();
                System.out.println(order + " [started baking]");
                Thread.sleep(bakingTime); //baking
                System.out.println(order + " [baked]");
                pizzeria.addToStorage(order);
            }
            catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }

}
