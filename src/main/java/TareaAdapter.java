import com.google.gson.*;

import java.lang.reflect.Type;

public class TareaAdapter implements JsonSerializer<Tarea>, JsonDeserializer<Tarea> {

    private int getCase(String type){
        if (type.equals(TareaDiaria.class.getSimpleName()))
            return 1;
        if (type.equals(TareaPuntual.class.getSimpleName()))
            return 2;
        return 0;
    }

    @Override
    public JsonElement serialize(Tarea src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(src.getClass().getSimpleName()));
        result.add("properties", context.serialize(src, src.getClass()));

        return result;
    }

    @Override
    public Tarea deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");

        Class<? extends Tarea> clase = switch (this.getCase(type)) {
            case 1 -> TareaDiaria.class;
            case 2 -> TareaPuntual.class;
            default -> throw new JsonParseException("Unknown element type: " + type);
        };

        return context.deserialize(element, clase);

    }
}


