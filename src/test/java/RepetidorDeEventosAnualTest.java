import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;


public class RepetidorDeEventosAnualTest {

	private final LocalDate CUATRODENOVIEMBRE = LocalDate.parse("2023-11-04");

	@Test

	public void EventoAnualSeGeneraLasVecesEspecificadas() {
		var evento = new Evento(0L, CUATRODENOVIEMBRE, 1L);
		var repetidor = new RepetidorDeEventosAnual(evento, 12);

		var lista = repetidor.generarInstancias(0, 100);

		assertEquals(12, lista.size());
	}

	@Test

	public void EventoAnualSeGeneraCadaUnAnioTodosLosAnios() {
		var evento = new Evento(0L, CUATRODENOVIEMBRE, 1L);
		var repetidor = new RepetidorDeEventosAnual(evento, 3);

		var lista = repetidor.generarInstancias(0, 100);

		assertEquals(CUATRODENOVIEMBRE.atStartOfDay(), lista.get(0).getInicio());
		assertEquals(CUATRODENOVIEMBRE.plusYears(1).atStartOfDay(), lista.get(1).getInicio());
		assertEquals(CUATRODENOVIEMBRE.plusYears(2).atStartOfDay(), lista.get(2).getInicio());
	}
}