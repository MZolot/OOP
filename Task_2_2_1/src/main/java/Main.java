import pizzeria.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        int clientsNumber = 1;
        Pizzeria pizzeria = new Pizzeria(new File("config.json"));
        ExecutorService clients = Executors.newFixedThreadPool(clientsNumber);
        ExecutorService pizzerias = Executors.newFixedThreadPool(1);
        pizzerias.submit(pizzeria);
        for (int i = 0; i <clientsNumber; i++) {
            clients.submit(new Client(pizzeria));
        }

        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextLine()) {}
        pizzeria.close();
        clients.shutdown();
        pizzerias.shutdown();

    }
}
