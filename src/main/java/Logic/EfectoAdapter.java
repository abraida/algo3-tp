package Logic;

import com.google.gson.*;

import java.lang.reflect.Type;

public class EfectoAdapter implements JsonSerializer<Efecto>, JsonDeserializer<Efecto> {

    private int getID(String type) {
        if (type.equals(EfectoSinEfecto.class.getSimpleName()))
            return 1;
        if (type.equals(EfectoNotificacion.class.getSimpleName()))
            return 2;
        if (type.equals(EfectoEnviarMail.class.getSimpleName()))
            return 3;
        if (type.equals(EfectoReproducirSonido.class.getSimpleName()))
            return 4;
        return -1;
    }

    @Override
    public JsonElement serialize(Efecto src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(getID(src.getClass().getSimpleName())));
        result.add("properties", context.serialize(src, src.getClass()));

        return result;
    }

    @Override
    public Efecto deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");

        Class<? extends Efecto> clase = switch (type) {
            case "1" -> EfectoSinEfecto.class;
            case "2" -> EfectoNotificacion.class;
            case "3" -> EfectoEnviarMail.class;
            case "4" -> EfectoReproducirSonido.class;
            default -> throw new JsonParseException("Unknown element type for " + typeOfT + ": " + type);
        };

        return context.deserialize(element, clase);

    }
}


