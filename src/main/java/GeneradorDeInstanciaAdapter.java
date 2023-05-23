import com.google.gson.*;

import java.lang.reflect.Type;

public class GeneradorDeInstanciaAdapter implements JsonSerializer<GeneradorDeInstancia>, JsonDeserializer<GeneradorDeInstancia> {

    private int getCase(String type){
        if (type.equals(GeneradorUnico.class.getSimpleName()))
            return 1;
        if (type.equals(GeneradorDiario.class.getSimpleName()))
            return 2;
        if (type.equals(GeneradorSemanal.class.getSimpleName()))
            return 3;
        if (type.equals(GeneradorMensual.class.getSimpleName()))
            return 4;
        if (type.equals(GeneradorAnual.class.getSimpleName()))
            return 5;
        return 0;
    }

    @Override
    public JsonElement serialize(GeneradorDeInstancia src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(src.getClass().getSimpleName()));
        result.add("properties", context.serialize(src, src.getClass()));

        return result;
    }

    @Override
    public GeneradorDeInstancia deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");

        Class<? extends GeneradorDeInstancia> clase = switch (this.getCase(type)) {
            case 1 -> GeneradorUnico.class;
            case 2 -> GeneradorDiario.class;
            case 3 -> GeneradorSemanal.class;
            case 4 -> GeneradorMensual.class;
            case 5 -> GeneradorAnual.class;
            default -> throw new JsonParseException("Unknown element type for " + typeOfT + ": " + type);
        };

        return context.deserialize(element, clase);

    }
}


