package Model;

import Logic.EventoDiario;
import Logic.GeneradorUnico;
import Logic.LimitadorPorCantidad;
import Logic.RepetidorDeEventos;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class GeneradorUnicoTest {
    private final LocalDate CUATRODENOVIEMBRE = LocalDate.parse("2023-11-04");
    private final LocalDate CINCODENOVIEMBRE = LocalDate.parse("2023-11-05");

    @Test
    public void EventoSinRepeticionSeGeneraUnaSolaVez() {
        var evento = new EventoDiario("", "", CUATRODENOVIEMBRE, CINCODENOVIEMBRE);

        var generador = new GeneradorUnico();
        var repetidor = new RepetidorDeEventos(evento, new LimitadorPorCantidad(1), generador);

        var lista = repetidor.generarInstancias(0, 100);

        assertEquals(1, lista.size());
    }

    @Test
    public void EventoSinRepeticionSeGeneraEnLaHoraOriginal() {
        var evento = new EventoDiario("", "", CUATRODENOVIEMBRE, CINCODENOVIEMBRE);

        var generador = new GeneradorUnico();
        var repetidor = new RepetidorDeEventos(evento, new LimitadorPorCantidad(10), generador);

        var lista = repetidor.generarInstancias(0, 100);

        assertEquals(CUATRODENOVIEMBRE.atStartOfDay(), lista.get(0).getInicio());
    }
}