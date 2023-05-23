import com.google.gson.*;
import java.lang.reflect.Type;
public class AlarmaAdapter implements JsonSerializer<Alarma>, JsonDeserializer<Alarma> {
    private int getCase(String type){
        if (type.equals(AlarmaAbsoluta.class.getSimpleName()))
            return 1;
        if (type.equals(AlarmaRelativa.class.getSimpleName()))
            return 2;
        return 0;
    }

    @Override
    public JsonElement serialize(Alarma src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(src.getClass().getSimpleName()));
        result.add("properties", context.serialize(src, src.getClass()));

        return result;
    }

    @Override
    public Alarma deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");

        Class<? extends Alarma> clase = switch (this.getCase(type)) {
            case 1 -> AlarmaAbsoluta.class;
            case 2 -> AlarmaRelativa.class;
            default -> throw new JsonParseException("Unknown element type for " + typeOfT + ": " + type);
        };

        return context.deserialize(element, clase);
    }
}

