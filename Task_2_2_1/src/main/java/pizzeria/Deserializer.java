package pizzeria;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.*;

class Deserializer {

    public class ParametersDeserializer implements JsonDeserializer<Pizzeria.Parameters> {

        @Override
        public Pizzeria.Parameters deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            int queueSize = jsonObject.get("queue").getAsInt();
            int storageSize = jsonObject.get("storage").getAsInt();
            JsonArray jsonBakers = jsonObject.getAsJsonArray("bakers");
            List<Integer> bakers = new ArrayList<>();
            for (JsonElement baker : jsonBakers) {
                bakers.add(baker.getAsInt());
            }
            JsonArray jsonCouriersTrunk = jsonObject.getAsJsonArray("couriers trunk sizes");
            JsonArray jsonCouriersSpeed = jsonObject.getAsJsonArray("couriers speed");
            if (jsonCouriersSpeed.size() != jsonCouriersTrunk.size()) {
                throw new IllegalArgumentException("Trunk size and speed should be specified for each courier.");
            }
            List<Integer[]> couriers = new ArrayList<>();
            for (int i = 0; i < jsonCouriersTrunk.size(); i++) {
                Integer[] element = new Integer[2];
                element[0] = jsonCouriersTrunk.get(i).getAsInt();
                element[1] = jsonCouriersSpeed.get(i).getAsInt();
                couriers.add(element);
            }
            return new Pizzeria.Parameters(queueSize, storageSize,
                    bakers.stream().sorted().collect(Collectors.toList()),
                    couriers.stream().sorted().collect(Collectors.toList()));
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

    public static void main(String[] args) throws IOException {
        Deserializer deserializer = new Deserializer(new File("config.json"));
        Pizzeria.Parameters parameters = deserializer.deserializeParameters();
    }
}
