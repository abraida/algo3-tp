import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class Evento implements Elemento {

	private final long id;

	private String titulo;

	private String descripcion;

	private LocalDateTime inicio;

	public void setDuracion(Duration duracion) {
		this.duracion = duracion;
	}

	private Duration duracion;

	private final ArrayList<Alarma> alarmas;

	public Evento(long id, LocalDateTime inicio, Duration duracion) {
		this.id = id;
		this.inicio = inicio;
		this.duracion = duracion;
		this.alarmas = new ArrayList<>();

	}

	public Evento(long id, LocalDate fecha, Long duracionDias) {
		this.id = id;
		this.inicio = fecha.atStartOfDay();
		this.duracion = Duration.ofDays(duracionDias);
		this.alarmas = new ArrayList<>();

	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setInicio(LocalDateTime inicio) {
		this.inicio = inicio;
	}

	public LocalDateTime getInicio() {
		return this.inicio;
	}

	public Evento crearCopiaDesplazada(LocalDateTime inicio) {
		var e = new Evento(id, inicio, this.duracion);
		e.setTitulo(this.titulo);
		e.setDescripcion(this.descripcion);

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

	public void agregarAlarma(Alarma alarma) {
		alarmas.add(alarma);
	}

	@Override

	public boolean eliminarAlarma(Alarma alarma) {
		return alarmas.remove(alarma);
	}

	@Override

	public LocalDateTime obtenerTiempoDeAlarma(Alarma alarma) {
		return alarma.getTiempo(this.inicio);
	}

	@Override

	public long getId() {
		return this.id;
	}
}