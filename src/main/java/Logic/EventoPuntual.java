package Logic;

import Controller.ElementoVisitor;

import java.time.Duration;
import java.time.LocalDateTime;

public class EventoPuntual extends Evento {
	private LocalDateTime inicio;
	private LocalDateTime fin;

	public void setFin(LocalDateTime fin) {
		this.fin = fin;
	}
	public void setInicio(LocalDateTime inicio) {
		this.inicio = inicio;
	}

	public LocalDateTime getInicio() {
		return inicio;
	}

	public LocalDateTime getFin() {
		return fin;
	}

	public EventoPuntual(String titulo, String descripcion, LocalDateTime inicio, LocalDateTime fin) {
		super(titulo, descripcion);
		this.inicio = inicio;
		this.fin = fin;
	}

	public EventoPuntual(String titulo, String descripcion, LocalDateTime inicio, Duration duracion) {
		super(titulo, descripcion);
		this.inicio = inicio;
		this.fin = inicio.plus(duracion);
	}
	@Override
	public Evento crearCopiaDesplazada(LocalDateTime inicio) {
		var fin = inicio.plus(Duration.between(this.inicio, this.fin));
		var e = new EventoPuntual(this.getTitulo(), this.getDescripcion(), inicio, fin);
		e.setID(this.getID());

		for (Alarma alarma: this.alarmas) {
			e.agregarAlarma(alarma);
		}

		return e;
	}

	@Override
	public LocalDateTime obtenerTiempoDeAlarma(Alarma alarma) {
		return alarma.getTiempo(this.inicio);
	}
	@Override
	public void aceptar(ElementoVisitor visitor) {
		visitor.visitarEventoPuntual(this);
	}


}