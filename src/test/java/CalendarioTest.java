import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.*;


public class CalendarioTest {

	private Calendario calendario;

	private final LocalDate CUATRODENOVIEMBRE = LocalDate.parse("2023-11-04");

	@Before

	public void setUp() {
		this.calendario = new Calendario();
	}

	@Test

	public void losIDSGeneradosSonUnicos() {
		var id1 = calendario.crearTareaDiaCompleto(CUATRODENOVIEMBRE);
		var id2 = calendario.crearTareaPuntual(CUATRODENOVIEMBRE.atStartOfDay());
		var id3 = calendario.crearEventoUnicoDiaCompleto(CUATRODENOVIEMBRE, 1L);

		calendario.agregarRepeticionDiaria(calendario.buscarEventoPorId(id3), 1, 10);
		calendario.obtenerProximosElementos(100, CUATRODENOVIEMBRE.atStartOfDay());

		var id4 = calendario.crearEventoUnicoDiaCompleto(CUATRODENOVIEMBRE, 1L);

		assertEquals(0, id1);
		assertEquals(1, id2);
		assertEquals(2, id3);
		assertEquals(3, id4);
	}

	@Test

	public void buscarIdInexistenteDevuelveNull() {
		var e1 = calendario.buscarEventoPorId(0);
		var t1 = calendario.buscarTareaPorId(0);

		assertNull(e1);
		assertNull(t1);
	}

	@Test

	public void eliminarIdInexistenteDevuelveFalse() {
		var e1 = calendario.eliminarEventoPorId(0);
		var t1 = calendario.eliminarTareaPorId(0);

		assertFalse(e1);
		assertFalse(t1);
	}

	@Test

	public void eliminarIdEliminaCorrectamente() {
		var id1 = calendario.crearTareaDiaCompleto(CUATRODENOVIEMBRE);
		var id2 = calendario.crearEventoUnicoDiaCompleto(CUATRODENOVIEMBRE, 1L);

		var t1 = calendario.buscarTareaPorId(id1);
		var e1 = calendario.buscarEventoPorId(id2);
		calendario.eliminarTareaPorId(id1);
		calendario.eliminarEventoPorId(id2);
		var t2 = calendario.buscarTareaPorId(id1);
		var e2 = calendario.buscarEventoPorId(id2);

		assertNotNull(t1);
		assertNotNull(e1);
		assertNull(t2);
		assertNull(e2);
	}

	@Test

	public void buscarTareaConIdDeEventoDevuelveNull() {
		var id2 = calendario.crearEventoUnicoDiaCompleto(CUATRODENOVIEMBRE, 1L);

		var t1 = calendario.buscarTareaPorId(id2);
		var t2 = calendario.eliminarTareaPorId(id2);

		assertNull(t1);
		assertFalse(t2);
	}

	@Test

	public void buscarEventoConIdDeTareaDevuelveNull() {
		var id1 = calendario.crearTareaDiaCompleto(CUATRODENOVIEMBRE);

		var e1 = calendario.buscarEventoPorId(id1);
		var e2 = calendario.eliminarEventoPorId(id1);

		assertNull(e1);
		assertFalse(e2);
	}

	@Test

	public void modificarEventoModificaTodasLasRepeticiones() {
		var id = calendario.crearEventoUnicoDiaCompleto(CUATRODENOVIEMBRE, 1);
		var evento = calendario.buscarEventoPorId(id);
		var alarma = calendario.agregarAlarmaRelativa(evento, Duration.ofMinutes(30), Calendario.EfectoEnum.EMAIL);

		calendario.agregarRepeticionDiaria(evento, 1, 20);
		var eventos = calendario.obtenerProximosElementos(10, CUATRODENOVIEMBRE.atStartOfDay());

		Evento evento2 = (Evento) eventos.get(9);
		calendario.modificarEventoDiaCompleto(evento2,
			"ASD",
			"asd",
			CUATRODENOVIEMBRE.plusDays(1),
			2);

		assertTrue(calendario.eliminarAlarma(evento2, alarma));

		assertEquals("ASD", evento.getTitulo());
		assertEquals(CUATRODENOVIEMBRE.plusDays(1).atStartOfDay(), evento.getInicio());
		assertFalse(evento.eliminarAlarma(alarma));
	}
	@Test

	public void eliminarEventoEliminaTodasLasRepeticiones() {
		var id = calendario.crearEventoUnicoDiaCompleto(CUATRODENOVIEMBRE, 1);
		var evento = calendario.buscarEventoPorId(id);

		calendario.agregarRepeticionDiaria(evento, 1, 20);
		var eventos = calendario.obtenerProximosElementos(10, CUATRODENOVIEMBRE.atStartOfDay());

		Evento evento2 = (Evento) eventos.get(9);
		calendario.eliminarEventoPorId(evento2.getId());

		assertEquals(0, calendario.obtenerProximosElementos(10, CUATRODENOVIEMBRE.atStartOfDay()).size());
	}
	@Test

	public void cambiarLaFrecuenciaDeRepeticionesFuncionaCorrectamente() {
		var id1 = calendario.crearEventoUnicoDiaCompleto(CUATRODENOVIEMBRE, 1L);
		var lUnico = calendario.obtenerProximosElementos(10, CUATRODENOVIEMBRE.minusDays(1).atStartOfDay());

		calendario.agregarRepeticionDiaria(calendario.buscarEventoPorId(id1), 1, 2);
		var lDiario = calendario.obtenerProximosElementos(10, CUATRODENOVIEMBRE.minusDays(1).atStartOfDay());

		calendario.agregarRepeticionAnual(calendario.buscarEventoPorId(id1), 3);
		var lAnual = calendario.obtenerProximosElementos(10, CUATRODENOVIEMBRE.minusDays(1).atStartOfDay());
		var e = (Evento) lAnual.get(1);

		assertEquals(1, lUnico.size());
		assertEquals(2, lDiario.size());
		assertEquals(3, lAnual.size());

		assertEquals(CUATRODENOVIEMBRE.plusYears(1), e.getInicio().toLocalDate());
	}

	@Test

	public void obtenerProximosEventosFuncionaCorrectamente() {
		var id1 = calendario.crearTareaDiaCompleto(CUATRODENOVIEMBRE.plusDays(1));
		var id2 = calendario.crearTareaPuntual(CUATRODENOVIEMBRE.atStartOfDay().plusHours(2));
		var id3 = calendario.crearEventoUnicoDiaCompleto(CUATRODENOVIEMBRE, 1L);
		var id4 = calendario.crearEventoUnico(CUATRODENOVIEMBRE.plusDays(2).atTime(LocalTime.NOON), Duration.ofHours(2));

		calendario.agregarRepeticionDiaria(calendario.buscarEventoPorId(id3), 4, 2);
		calendario.agregarRepeticionMensual(calendario.buscarEventoPorId(id4), 3);

		var l = calendario.obtenerProximosElementos(7, CUATRODENOVIEMBRE.minusDays(1).atStartOfDay());

		assertEquals(id3, l.get(0).getId());
		assertEquals(id2, l.get(1).getId());
		assertEquals(id1, l.get(2).getId());
		assertEquals(id4, l.get(3).getId());
		assertEquals(id3, l.get(4).getId());
		assertEquals(id4, l.get(5).getId());
		assertEquals(7, l.size());

	}
}