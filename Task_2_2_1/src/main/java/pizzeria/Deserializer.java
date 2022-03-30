package pizzeria;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.*;

public class Deserializer {

    public class ParametersDeserializer implements JsonDeserializer<Pizzeria.Parameters> {

        @Override
        public Pizzeria.Parameters deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            JsonArray jsonBakers = jsonObject.getAsJsonArray("bakers");
            List<Integer> bakers = new ArrayList<>();
            for (JsonElement baker : jsonBakers) {
                bakers.add(baker.getAsInt());
            }
            JsonArray jsonCouriers = jsonObject.getAsJsonArray("couriers");
            List<Integer> couriers = new ArrayList<>();
            for (JsonElement courier : jsonCouriers) {
                couriers.add(courier.getAsInt());
            }
            return new Pizzeria.Parameters(bakers.stream().sorted().collect(Collectors.toList()), couriers);
        }
    }

    private final Reader reader;
    private final Gson gson;

    Deserializer(File parametersFile) throws IOException {
        this.reader = new FileReader(parametersFile);
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Pizzeria.Parameters.class, new ParametersDeserializer())
                .create();
    }

    Pizzeria.Parameters deserializeParameters() {
        return gson.fromJson(reader, Pizzeria.Parameters.class);
    }
}
