package Model;

import Logic.*;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class GeneradorSemanalTest {
    private final LocalDate CUATRODENOVIEMBRE = LocalDate.parse("2023-11-04");
	private final LocalDate CINCODENOVIEMBRE = LocalDate.parse("2023-11-05");

    @Test
    public void EventoSemanalSeGeneraLasVecesEspecificadas() {
		var evento = new EventoDiario("", "", CUATRODENOVIEMBRE, CINCODENOVIEMBRE );
        var diasActivo =  new DiasActivos(false,
                false,
                true,
                false,
                false,
                false,
                true);
        var generador = new GeneradorSemanal(diasActivo);
        var repetidor = new RepetidorDeEventos(evento,
                new LimitadorPorCantidad(12),
                generador);

        var lista = repetidor.generarInstancias(0, 100);

        assertEquals(12, lista.size());
    }

    @Test
    public void EventoSemanalSeGeneraConLaFrecuenciaEspecificada() {
		var evento = new EventoDiario("", "", CUATRODENOVIEMBRE, CINCODENOVIEMBRE );
        var diasActivo =  new DiasActivos(false,
                true,
                false,
                false,
                false,
                true,
                false);
        var generador = new GeneradorSemanal(diasActivo);
        var repetidor = new RepetidorDeEventos(evento,
                new LimitadorPorCantidad(4),
                generador);

        var lista = repetidor.generarInstancias(0, 100);

        assertEquals(CUATRODENOVIEMBRE.atStartOfDay(), lista.get(0).getInicio());
        assertEquals(CUATRODENOVIEMBRE.plusDays(3).atStartOfDay(), lista.get(1).getInicio());
        assertEquals(CUATRODENOVIEMBRE.plusDays(7).atStartOfDay(), lista.get(2).getInicio());
        assertEquals(CUATRODENOVIEMBRE.plusDays(10).atStartOfDay(), lista.get(3).getInicio());
    }

    @Test
    public void EventoSemanalNoSeUsaElDiaOriginalSiNoEsUnDiaDeRepeticion() {
		var evento = new EventoDiario("", "", CUATRODENOVIEMBRE, CINCODENOVIEMBRE );

        var diasActivo =  new DiasActivos(false,
                true,
                false,
                false,
                false,
                false,
                true);
        var generador = new GeneradorSemanal(diasActivo);
        var repetidor = new RepetidorDeEventos(evento,
                new LimitadorPorCantidad(4),
                generador);


        var lista = repetidor.generarInstancias(0, 100);

        assertEquals(CUATRODENOVIEMBRE.plusDays(1).atStartOfDay(), lista.get(0).getInicio());
        assertEquals(CUATRODENOVIEMBRE.plusDays(3).atStartOfDay(), lista.get(1).getInicio());
        assertEquals(CUATRODENOVIEMBRE.plusDays(8).atStartOfDay(), lista.get(2).getInicio());
        assertEquals(CUATRODENOVIEMBRE.plusDays(10).atStartOfDay(), lista.get(3).getInicio());
    }

    @Test
    public void generarInstanciasDeEventoSemanalDevuelveLasPedidas() {
		var evento = new EventoDiario("", "", CUATRODENOVIEMBRE, CINCODENOVIEMBRE );

        var diasActivo =  new DiasActivos(false,
                true,
                false,
                false,
                false,
                false,
                true);
        var generador = new GeneradorSemanal(diasActivo);
        var repetidor = new RepetidorDeEventos(evento,
                new LimitadorPorCantidad(12),
                generador);



        var lista = repetidor.generarInstancias(0, 3);

        assertEquals(3, lista.size());
    }
}