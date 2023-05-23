import com.google.gson.*;

import java.lang.reflect.Type;

public class LimitadorDeRepeticionAdapter implements JsonSerializer<LimitadorDeRepeticion>, JsonDeserializer<LimitadorDeRepeticion> {

    private int getCase(String type){
        if (type.equals(LimitadorPorCantidad.class.getSimpleName()))
            return 1;
        if (type.equals(LimitadorPorFecha.class.getSimpleName()))
            return 2;
        if (type.equals(LimitadorInfinito.class.getSimpleName()))
            return 3;
        return 0;
    }

    @Override
    public JsonElement serialize(LimitadorDeRepeticion src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(src.getClass().getSimpleName()));
        result.add("properties", context.serialize(src, src.getClass()));

        return result;
    }

    @Override
    public LimitadorDeRepeticion deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");

        Class<? extends LimitadorDeRepeticion> clase = switch (this.getCase(type)) {
            case 1 -> LimitadorPorCantidad.class;
            case 2 -> LimitadorPorFecha.class;
            case 3 -> LimitadorInfinito.class;

            default -> throw new JsonParseException("Unknown element type: " + type);
        };

        return context.deserialize(element, clase);

    }
}


