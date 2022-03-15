package pizzeria;

import java.io.*;
import java.util.Arrays;

import com.google.gson.*;

public class Deserializer {

    private final Reader reader;
    private final Gson gson;

    Deserializer(File parametersFile) throws IOException {
        this.reader = new FileReader(parametersFile);
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    Pizzeria.Parameters deserialize() {
        Integer[] array = gson.fromJson(reader, Integer[].class);
        int bakersAmt = array[0];
        Integer[] bakers = Arrays.copyOfRange(array, 1, 1 + bakersAmt);
        int couriersAmt = array[1 + bakersAmt];
        Integer[] couriers = Arrays.copyOfRange(array, 2 + bakersAmt, 2 + bakersAmt + couriersAmt);
        return new Pizzeria.Parameters(bakersAmt, bakers, couriersAmt, couriers);
    }
}
