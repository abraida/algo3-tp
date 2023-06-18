package Model;

import Logic.Calendario;
import Logic.Tarea;
import Logic.TareaDiaria;
import Logic.TareaPuntual;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class NuevaTareaModel extends NuevoElementoModel {

    public NuevaTareaModel(Calendario calendario) {
        super(calendario);
    }

    public void crearTareaDiaCompleto(String titulo, String descripcion, LocalDate fecha) {
        this.elemento = new TareaDiaria(titulo, descripcion, fecha);
    }

    public void crearTareaPuntual(String titulo, String descripcion, LocalDateTime vencimiento) {
        this.elemento = new TareaPuntual(titulo,
                descripcion, vencimiento);
    }

    @Override
    public void mandarACalendario() {
        calendario.agregarTarea((Tarea) elemento);
    }
}
