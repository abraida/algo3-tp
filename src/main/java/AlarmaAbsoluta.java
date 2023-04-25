import java.time.LocalDateTime;


public class AlarmaAbsoluta extends Alarma {

	private final LocalDateTime tiempo;

	public AlarmaAbsoluta(LocalDateTime tiempo, Efecto efecto) {
		this.efecto = efecto;
		this.tiempo = tiempo;
	}

	@Override

	public LocalDateTime getTiempo(LocalDateTime tiempo) {
		return this.tiempo;
	}
}