package Logic;

import com.google.gson.*;

import java.lang.reflect.Type;

public class EventoAdapter implements JsonSerializer<Evento>, JsonDeserializer<Evento> {

    private int getID(String type) {
        if (type.equals(EventoDiario.class.getSimpleName()))
            return 1;
        if (type.equals(EventoPuntual.class.getSimpleName()))
            return 2;
        return 0;
    }

    @Override
    public JsonElement serialize(Evento src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(getID(src.getClass().getSimpleName())));
        result.add("properties", context.serialize(src, src.getClass()));

        return result;
    }

    @Override
    public Evento deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");

        Class<? extends Evento> clase = switch (type) {
            case "1" -> EventoDiario.class;
            case "2" -> EventoPuntual.class;
            default -> throw new JsonParseException("Unknown element type: " + type);
        };

        return context.deserialize(element, clase);

    }
}


