import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;


public class GeneradorDiarioTest {

	private final LocalDate CUATRODENOVIEMBRE = LocalDate.parse("2023-11-04");

	@Test

	public void EventoDiarioSeGeneraLasVecesEspecificadas() {
		var evento = new Evento(0L, CUATRODENOVIEMBRE, 1L);
		var generador = new GeneradorDiario(4);
		var repetidor = new RepetidorDeEventos(evento, new LimitadorPorCantidad(12), generador);

		var lista = repetidor.generarInstancias(0, 100);

		assertEquals(12, lista.size());
	}

	@Test

	public void EventoDiarioSeGeneraConLaFrecuenciaEspecificada() {
		var evento = new Evento(0L, CUATRODENOVIEMBRE, 1L);
		var generador = new GeneradorDiario(4);
		var repetidor = new RepetidorDeEventos(evento, new LimitadorPorCantidad(3), generador);

		var lista = repetidor.generarInstancias(0, 100);

		assertEquals(CUATRODENOVIEMBRE.atStartOfDay(), lista.get(0).getInicio());
		assertEquals(CUATRODENOVIEMBRE.plusDays(4).atStartOfDay(), lista.get(1).getInicio());
		assertEquals(CUATRODENOVIEMBRE.plusDays(4 * 2).atStartOfDay(), lista.get(2).getInicio());
	}

	@Test

	public void EventoDiarioSePuedeColapsar() {
		var evento = new Evento(0L, CUATRODENOVIEMBRE, 1L);
		var generador = new GeneradorDiario(0);
		var repetidor = new RepetidorDeEventos(evento, new LimitadorPorCantidad(3), generador);

		var lista = repetidor.generarInstancias(0, 100);

		assertEquals(CUATRODENOVIEMBRE.atStartOfDay(), lista.get(0).getInicio());
		assertEquals(CUATRODENOVIEMBRE.atStartOfDay(), lista.get(1).getInicio());
		assertEquals(CUATRODENOVIEMBRE.atStartOfDay(), lista.get(2).getInicio());
	}

	@Test

	public void generarInstanciasDeEventoDiarioDevuelveLasPedidas() {
		var evento = new Evento(0L, CUATRODENOVIEMBRE, 1L);
		var generador = new GeneradorDiario(2);
		var repetidor = new RepetidorDeEventos(evento, new LimitadorPorCantidad(12), generador);

		var lista = repetidor.generarInstancias(0, 3);

		assertEquals(3, lista.size());
	}
}