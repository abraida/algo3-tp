import java.time.LocalDateTime;


public class AlarmaAbsoluta extends Alarma {

	private final LocalDateTime tiempo;

	public AlarmaAbsoluta(long id, LocalDateTime tiempo, Efecto efecto) {
		this.id = id;
		this.efecto = efecto;
		this.tiempo = tiempo;
	}

	@Override

	public LocalDateTime getTiempo(LocalDateTime tiempo) {
		return this.tiempo;
	}
}