package Logic;

import Controller.ElementoVisitor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class TareaDiaria extends Tarea {

    private LocalDate fecha;

    public TareaDiaria(String titulo, String descripcion, LocalDate fecha) {
        super(titulo, descripcion);
        this.fecha = fecha;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public LocalDateTime getInicio() {
        return fecha.atStartOfDay();
    }

    @Override
    public LocalDateTime getFin() {
        return fecha.atTime(LocalTime.MAX);
    }

    @Override

    public LocalDateTime obtenerTiempoDeAlarma(Alarma alarma) {
        return alarma.getTiempo(this.fecha.atStartOfDay());
    }

    @Override
    public void aceptar(ElementoVisitor visitor) {
        visitor.visitarTareaDiaria(this);
    }

}