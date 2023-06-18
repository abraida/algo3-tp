package Model;

import Logic.*;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;


public class EventoTest {

    private final LocalDate CUATRODENOVIEMBRE = LocalDate.parse("2023-11-04");
    private final LocalDate CINCODENOVIEMBRE = LocalDate.parse("2023-11-05");

    private Evento evento1;

    private Alarma alarma1;

    private Alarma alarma2;

    private Evento evento2;

    @Before

    public void setUp() {
        this.evento1 = new EventoDiario("", "", CUATRODENOVIEMBRE, CUATRODENOVIEMBRE);

        evento1.setTitulo("Evento A");
        evento1.setDescripcion("ASDASD");

        this.alarma1 = new AlarmaRelativa(0, Duration.ofMinutes(10), new EfectoSinEfecto());
        this.alarma2 = new AlarmaAbsoluta(1,
                CUATRODENOVIEMBRE.minusDays(1).atTime(LocalTime.NOON),
                new EfectoSinEfecto());

        evento1.agregarAlarma(alarma1);
        evento1.agregarAlarma(alarma2);

        this.evento2 = new EventoPuntual("",
                "",
                CUATRODENOVIEMBRE.atTime(LocalTime.NOON),
                CUATRODENOVIEMBRE.atTime(LocalTime.NOON).plusHours(3));

        evento2.setTitulo("Evento B");
    }

    @Test

    public void repetirEventoCopiaLosAtributos() {
        var generador = new GeneradorDiario(2);
        var repetidor = new RepetidorDeEventos(evento1, new LimitadorPorCantidad(10), generador);

        var eventos = repetidor.generarInstancias(0, 15);
        var titulosRepetidos = eventos.stream()
                .filter(e -> e.getTitulo().equals("Evento A"))
                .toList().size();
        var descripcionesRepetidas = eventos.stream().
                filter(e -> e.getDescripcion().equals("ASDASD"))
                .toList().size();

        assertEquals(10, titulosRepetidos);
        assertEquals(10, descripcionesRepetidas);
    }

    @Test

    public void repetirEventoCopiaLasAlarmas() {
        var generador = new GeneradorDiario(2);
        var repetidor = new RepetidorDeEventos(evento1, new LimitadorPorCantidad(10), generador);

        var eventos = repetidor.generarInstancias(0, 15);

        for (int i = 0; i < 10; i++) {
            assertEquals(CUATRODENOVIEMBRE.plusDays(2 * i).atStartOfDay().minusMinutes(10),
                    eventos.get(i).obtenerTiempoDeAlarma(this.alarma1));
            assertEquals(CUATRODENOVIEMBRE.minusDays(1).atTime(LocalTime.NOON),
                    eventos.get(i).obtenerTiempoDeAlarma(this.alarma2));
        }
    }

    @Test

    public void eventoEsMenorAEventoQueEmpiezaDespuesQueEste() {
        assertEquals(-1, this.evento1.compareTo(evento2));
        assertEquals(1, this.evento2.compareTo(evento1));
    }

    @Test

    public void eventoEsIgualAEventoQueEmpiezaYTerminaIgualQueEste() {
        var generador = new GeneradorDiario(0);
        var r = new RepetidorDeEventos(evento1, new LimitadorPorCantidad(2), generador);

        var eventos = r.generarInstancias(0, 2);

        assertEquals(0, eventos.get(0).compareTo(eventos.get(1)));
        assertEquals(0, eventos.get(1).compareTo(eventos.get(0)));
    }

    @Test

    public void eventoEsMenorATareaQueVenceDespuesDeQueEsteComienze() {
        var t = new TareaPuntual("", "", CUATRODENOVIEMBRE.atTime(LocalTime.NOON));
        assertEquals(-1, this.evento1.compareTo(t));
        assertEquals(1, t.compareTo(evento1));
    }

    @Test

    public void generarNEventosPosterioresLosGeneraCorrectamente() {
        var generador = new GeneradorDiario(1);
        var r = new RepetidorDeEventos(evento1, new LimitadorPorCantidad(20), generador);

        var eventos = r.generarEventosPosteriores(100, CUATRODENOVIEMBRE.plusDays(2).atTime(LocalTime.NOON));

        assertEquals(18, eventos.size());
        assertEquals(6, eventos.get(0).getInicio().getDayOfMonth());
    }

}