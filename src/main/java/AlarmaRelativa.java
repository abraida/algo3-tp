import java.time.Duration;
import java.time.LocalDateTime;


public class AlarmaRelativa extends Alarma {

	private final Duration tiempoRelativo;

	public AlarmaRelativa(Duration tiempoRelativo, Efecto efecto) {
		this.efecto = efecto;
		this.tiempoRelativo = tiempoRelativo;
	}

	@Override

	public LocalDateTime getTiempo(LocalDateTime tiempo) {
		if (Duration.between(LocalDateTime.MIN, tiempo).compareTo(tiempoRelativo)<0)
			return LocalDateTime.MIN;
		return tiempo.minus(this.tiempoRelativo);
	}
}