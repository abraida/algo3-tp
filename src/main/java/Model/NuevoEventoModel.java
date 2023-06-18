package Model;

import Logic.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class NuevoEventoModel extends NuevoElementoModel{
    public Evento evento;
    private RepetidorDeEventos repetidor;
    public NuevoEventoModel(Calendario calendario) {
        super(calendario);
    }

    @Override
    public void mandarACalendario() {
        calendario.agregarEvento(repetidor);;
    }

    public void crearEventoDiaCompleto(String titulo, String descripcion, LocalDate inicio, LocalDate fin) {
        this.elemento = new EventoDiario(titulo, descripcion, inicio, fin);
    }
    public void crearEventoPuntual(String titulo, String descripcion, LocalDateTime inicio, LocalDateTime fin) {
        this.elemento = new EventoPuntual(titulo, descripcion, inicio, fin);
    }

    public void crearRepeticion(long dias){
        this.repetidor = new RepetidorDeEventos((Evento) elemento, new LimitadorPorCantidad(20), new GeneradorDiario(dias));
    }

    public void crearRepeticionUnica() {
        this.repetidor = new RepetidorDeEventos((Evento) elemento, new LimitadorPorCantidad(1), new GeneradorUnico());
    }
}
