package Logic;

import Controller.ElementoVisitor;

import java.time.LocalDateTime;


public class TareaPuntual extends Tarea {

    private LocalDateTime vencimiento;

    public TareaPuntual(String titulo, String descripcion, LocalDateTime vencimiento) {
        super(titulo, descripcion);
        this.vencimiento = vencimiento;
    }

    public LocalDateTime getVencimiento() {
        return this.vencimiento;
    }

    public void setVencimiento(LocalDateTime vencimiento) {
        this.vencimiento = vencimiento;
    }

    @Override

    public LocalDateTime obtenerTiempoDeAlarma(Alarma alarma) {
        return alarma.getTiempo(this.vencimiento);
    }

    @Override
    public void aceptar(ElementoVisitor visitor) {
        visitor.visitarTareaPuntual(this);
    }

    @Override
    public LocalDateTime getInicio() {
        return vencimiento;
    }

    @Override
    public LocalDateTime getFin() {
        return vencimiento;
    }
}