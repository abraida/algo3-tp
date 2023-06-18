package Logic;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
    @Override
    public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
        Instant instant = localDateTime.truncatedTo(ChronoUnit.SECONDS).atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        return new JsonPrimitive(date.getTime());
    }

    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {

        return Instant.
                ofEpochMilli(json.getAsLong()).
                atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}