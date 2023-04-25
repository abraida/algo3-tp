import java.time.LocalDateTime;


public abstract class Alarma {
	protected Efecto efecto;

	public abstract LocalDateTime getTiempo(LocalDateTime tiempo);

	void disparar() {
		this.efecto.realizar();
	}

}