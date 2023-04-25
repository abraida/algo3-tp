import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class RepetidorDeEventosSemanalTest {
    private final LocalDate CUATRODENOVIEMBRE = LocalDate.parse("2023-11-04");

    @Test
    public void EventoSemanalSeGeneraLasVecesEspecificadas() {
        var evento = new Evento(0L, CUATRODENOVIEMBRE, 1L);
        var repetidor = new RepetidorDeEventosSemanal(evento,
                12,
                false,
                false,
                true,
                false,
                false,
                false,
                true);

        var lista = repetidor.generarInstancias(0, 100);

        assertEquals(12, lista.size());
    }

    @Test
    public void EventoSemanalSeGeneraConLaFrecuenciaEspecificada() {
        var evento = new Evento(0L, CUATRODENOVIEMBRE, 1L);
        var repetidor = new RepetidorDeEventosSemanal(evento,
                4,
                false,
                true,
                false,
                false,
                false,
                true,
                false);


        var lista = repetidor.generarInstancias(0, 100);

        assertEquals(CUATRODENOVIEMBRE.atStartOfDay(), lista.get(0).getInicio());
        assertEquals(CUATRODENOVIEMBRE.plusDays(3).atStartOfDay(), lista.get(1).getInicio());
        assertEquals(CUATRODENOVIEMBRE.plusDays(7).atStartOfDay(), lista.get(2).getInicio());
        assertEquals(CUATRODENOVIEMBRE.plusDays(10).atStartOfDay(), lista.get(3).getInicio());
    }

    @Test
    public void EventoSemanalNoSeUsaElDiaOriginalSiNoEsUnDiaDeRepeticion() {
        var evento = new Evento(0L, CUATRODENOVIEMBRE, 1L);
        var repetidor = new RepetidorDeEventosSemanal(evento,
                4,
                false,
                true,
                false,
                false,
                false,
                false,
                true);


        var lista = repetidor.generarInstancias(0, 100);

        assertEquals(CUATRODENOVIEMBRE.plusDays(1).atStartOfDay(), lista.get(0).getInicio());
        assertEquals(CUATRODENOVIEMBRE.plusDays(3).atStartOfDay(), lista.get(1).getInicio());
        assertEquals(CUATRODENOVIEMBRE.plusDays(8).atStartOfDay(), lista.get(2).getInicio());
        assertEquals(CUATRODENOVIEMBRE.plusDays(10).atStartOfDay(), lista.get(3).getInicio());
    }

    @Test
    public void generarInstanciasDeEventoSemanalDevuelveLasPedidas() {
        var evento = new Evento(0L, CUATRODENOVIEMBRE, 1L);
        var repetidor = new RepetidorDeEventosSemanal(evento,
                12,
                false,
                true,
                false,
                false,
                false,
                false,
                true);


        var lista = repetidor.generarInstancias(0, 3);

        assertEquals(3, lista.size());
    }
}