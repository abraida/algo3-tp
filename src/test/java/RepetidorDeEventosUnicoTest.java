import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class RepetidorDeEventosUnicoTest {
    private final LocalDate CUATRODENOVIEMBRE = LocalDate.parse("2023-11-04");

    @Test
    public void EventoSinRepeticionSeGeneraUnaSolaVez() {
        var evento = new Evento(0L, CUATRODENOVIEMBRE, 1L);
        var repetidor = new RepetidorDeEventosUnico(evento);

        var lista = repetidor.generarInstancias(0, 100);

        assertEquals(1, lista.size());
    }

    @Test
    public void EventoSinRepeticionSeGeneraEnLaHoraOriginal() {
        var evento = new Evento(0L, CUATRODENOVIEMBRE, 1L);
        var repetidor = new RepetidorDeEventosUnico(evento);

        var lista = repetidor.generarInstancias(0, 100);

        assertEquals(CUATRODENOVIEMBRE.atStartOfDay(), lista.get(0).getInicio());
    }
}