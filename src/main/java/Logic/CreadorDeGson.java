package Logic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CreadorDeGson {
	GsonBuilder gsonBuilder = new GsonBuilder();
    public Gson crearGson() {
		gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
		gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
		gsonBuilder.registerTypeAdapter(Duration.class, new DurationAdapter());

		gsonBuilder.registerTypeAdapter(Tarea.class, new TareaAdapter());
		gsonBuilder.registerTypeAdapter(Evento.class, new EventoAdapter());
		gsonBuilder.registerTypeAdapter(Alarma.class, new AlarmaAdapter());
		gsonBuilder.registerTypeAdapter(GeneradorDeInstancia.class, new GeneradorDeInstanciaAdapter());
		gsonBuilder.registerTypeAdapter(LimitadorDeRepeticion.class, new LimitadorDeRepeticionAdapter());
		gsonBuilder.registerTypeAdapter(Efecto.class, new EfectoAdapter());

		return gsonBuilder.setPrettyPrinting().create();
	}
}
