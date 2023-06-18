package Logic;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class Evento extends Elemento {
	public Evento(String titulo, String descripcion) {
		super(titulo, descripcion);
	}

	public abstract Evento crearCopiaDesplazada(LocalDateTime inicio);

	public abstract LocalDateTime getInicio();
}