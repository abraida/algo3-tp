import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class GeneradorUnicoTest {
    private final LocalDate CUATRODENOVIEMBRE = LocalDate.parse("2023-11-04");

    @Test
    public void EventoSinRepeticionSeGeneraUnaSolaVez() {
        var evento = new Evento(0L, CUATRODENOVIEMBRE, 1L);
        var generador = new GeneradorUnico();
        var repetidor = new RepetidorDeEventos(evento, new LimitadorPorCantidad(1), generador);

        var lista = repetidor.generarInstancias(0, 100);

        assertEquals(1, lista.size());
    }

    @Test
    public void EventoSinRepeticionSeGeneraEnLaHoraOriginal() {
        var evento = new Evento(0L, CUATRODENOVIEMBRE, 1L);
        var generador = new GeneradorUnico();
        var repetidor = new RepetidorDeEventos(evento, new LimitadorPorCantidad(10), generador);

        var lista = repetidor.generarInstancias(0, 100);

        assertEquals(CUATRODENOVIEMBRE.atStartOfDay(), lista.get(0).getInicio());
    }
}