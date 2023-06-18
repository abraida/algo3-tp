package Logic;

import Controller.ElementoVisitor;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class EventoDiario extends Evento {

    private LocalDate fecha;

    private LocalDate fechaFinal;


    public EventoDiario(String titulo, String descripcion, LocalDate fecha, LocalDate fechaFinal) {
        super(titulo, descripcion);
        this.fecha = fecha;
        this.fechaFinal = fechaFinal;
    }

    public EventoDiario(String titulo, String descripcion, LocalDate fecha, long dias) {
        super(titulo, descripcion);
        this.fecha = fecha;
        this.fechaFinal = fecha.plusDays(dias);

    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalDate getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(LocalDate fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public LocalDateTime getFin() {
        return fechaFinal.atTime(LocalTime.MAX);
    }

    @Override
    public LocalDateTime getInicio() {
        return fecha.atStartOfDay();
    }

    public Evento crearCopiaDesplazada(LocalDateTime inicio) {
        var dia = inicio.toLocalDate();
        var duration = Duration.between(this.getInicio(), this.getFin()).toDays();
        var e = new EventoDiario(this.getTitulo(),
                this.getDescripcion(), dia, dia.plusDays(duration));
        e.setID(this.getID());

        for (Alarma alarma : this.alarmas) {
            e.agregarAlarma(alarma);
        }

        return e;
    }

    @Override
    public LocalDateTime obtenerTiempoDeAlarma(Alarma alarma) {
        return alarma.getTiempo(this.fecha.atStartOfDay());
    }

    @Override
    public void aceptar(ElementoVisitor visitor) {
        visitor.visitarEventoDiario(this);
    }


}