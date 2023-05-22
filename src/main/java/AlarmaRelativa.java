import java.time.Duration;
import java.time.LocalDateTime;


public class AlarmaRelativa extends Alarma {

	private final Duration antelacion;

	public AlarmaRelativa(long id, Duration antelacion, Efecto efecto) {
		this.id = id;
		this.efecto = efecto;
		this.antelacion = antelacion;
	}

	@Override

	public LocalDateTime getTiempo(LocalDateTime tiempo) {
		if (Duration.between(LocalDateTime.MIN, tiempo).compareTo(antelacion)<0)
			return LocalDateTime.MIN;
		return tiempo.minus(this.antelacion);
	}
}