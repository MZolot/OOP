package pizzeria;

class Courier implements Runnable {

    final private Pizzeria pizzeria;
    //final private int trunkSize;

    Courier(Pizzeria pizzeria) {
        this.pizzeria = pizzeria;
        //this.trunkSize = trunkSize;
    }

    @Override
    public void run() {
        while (true) {
            try {
                int order = pizzeria.takeFromStorage();
                System.out.println(order + " [delivering]");
                Thread.sleep(1000); //delivering
                System.out.println(order + " [delivered]");
            }
            catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }
}
