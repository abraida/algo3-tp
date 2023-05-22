import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Evento extends Elemento {


	private LocalDateTime inicio;

	private Duration duracion;
	public Evento(long id, LocalDateTime inicio, Duration duracion) {
		super(id);
		this.inicio = inicio;
		this.duracion = duracion;

	}

	public Evento(long id, LocalDate fecha, Long duracionDias) {
		super(id);
		this.inicio = fecha.atStartOfDay();
		this.duracion = Duration.ofDays(duracionDias);

	}
	public void setInicio(LocalDateTime inicio) {
		this.inicio = inicio;
	}

	public void setDuracion(Duration duracion) {
		this.duracion = duracion;
	}

	public LocalDateTime getInicio() {
		return this.inicio;
	}

	public Evento crearCopiaDesplazada(LocalDateTime inicio) {
		var e = new Evento(this.getId(), inicio, this.duracion);
		e.setTitulo(this.getTitulo());
		e.setDescripcion(this.getDescripcion());

		for (Alarma alarma: this.alarmas) {
			e.agregarAlarma(alarma);
		}

		return e;
	}

	@Override

	public boolean esAnteriorA(LocalDateTime tiempo) {
		return inicio.isBefore(tiempo);
	}

	@Override

	public boolean esSimultaneoA(LocalDateTime tiempo) {
		return !inicio.isAfter(tiempo) && !inicio.plus(duracion).isBefore(tiempo);
	}

	@Override

	public boolean esPosteriorA(LocalDateTime tiempo) {
		return inicio.isAfter(tiempo);
	}

	@Override

	public int compareTo(Suceso otroSuceso) {
		if (otroSuceso.esPosteriorA(this.inicio)) {
			return -1;
		}

		if (otroSuceso.esAnteriorA(this.inicio)) {
			return 1;
		}

		if (otroSuceso.esSimultaneoA(this.inicio.plus(this.duracion).plusNanos(1)))
			return -1;

		if (otroSuceso.esSimultaneoA(this.inicio.plus(this.duracion)))
			return 0;

		return 1;
	}

	@Override

	public LocalDateTime obtenerTiempoDeAlarma(Alarma alarma) {
		return alarma.getTiempo(this.inicio);
	}
}