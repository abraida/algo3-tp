import java.time.LocalDateTime;


public abstract class Alarma implements Identificable {
	protected Efecto efecto;
	protected long id;
	public abstract LocalDateTime getTiempo(LocalDateTime tiempo);

	EfectoEnum disparar() {
		return this.efecto.realizar();
	}

	@Override
	public long getId() {
		return this.id;
	}
}