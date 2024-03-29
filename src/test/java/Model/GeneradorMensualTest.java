package Model;

import Logic.EventoDiario;
import Logic.GeneradorMensual;
import Logic.LimitadorPorCantidad;
import Logic.RepetidorDeEventos;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class GeneradorMensualTest {
    private final LocalDate CUATRODENOVIEMBRE = LocalDate.parse("2023-11-04");
    private final LocalDate CINCODENOVIEMBRE = LocalDate.parse("2023-11-05");

    @Test
    public void EventoDiarioSeGeneraLasVecesEspecificadas() {
        var evento = new EventoDiario("", "", CUATRODENOVIEMBRE, CINCODENOVIEMBRE);

        var generador = new GeneradorMensual();
        var repetidor = new RepetidorDeEventos(evento, new LimitadorPorCantidad(12), generador);

        var lista = repetidor.generarInstancias(0, 100);

        assertEquals(12, lista.size());
    }

    @Test
    public void EventoMensualSeGeneraCadaUnMesTodosLosMeses() {
        var evento = new EventoDiario("", "", CUATRODENOVIEMBRE, CINCODENOVIEMBRE);
        var generador = new GeneradorMensual();
        var repetidor = new RepetidorDeEventos(evento, new LimitadorPorCantidad(3), generador);


        var lista = repetidor.generarInstancias(0, 100);

        assertEquals(CUATRODENOVIEMBRE.atStartOfDay(), lista.get(0).getInicio());
        assertEquals(CUATRODENOVIEMBRE.plusMonths(1).atStartOfDay(), lista.get(1).getInicio());
        assertEquals(CUATRODENOVIEMBRE.plusMonths(2).atStartOfDay(), lista.get(2).getInicio());
    }
}