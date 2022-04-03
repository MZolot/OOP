import pizzeria.*;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Pizzeria pizzeria = new Pizzeria(new File("config.json"));
        ExecutorService clients = Executors.newFixedThreadPool(1);
        Client client = new Client(pizzeria);
        clients.submit(client);
        pizzeria.work();
        clients.shutdown();
    }
}
