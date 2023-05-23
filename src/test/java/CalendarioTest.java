import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
		var gen = new GeneradorDiario(1);
		var lim = new LimitadorPorCantidad(3);

		var id3 = calendario.crearEventoDiaCompleto(CUATRODENOVIEMBRE,  1L);
		calendario.modificarReglaDeRepeticion(calendario.buscarEventoPorId(id3), gen);
		calendario.modificarCantidadDeRepeticiones(calendario.buscarEventoPorId(id3),lim);

		calendario.obtenerProximosElementos(100, CUATRODENOVIEMBRE.atStartOfDay());

		var id4 = calendario.crearEventoDiaCompleto(CUATRODENOVIEMBRE, 1L);

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
		var id2 = calendario.crearEventoDiaCompleto(CUATRODENOVIEMBRE, 1L);

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
		var id2 = calendario.crearEventoDiaCompleto(CUATRODENOVIEMBRE, 1L);

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
		var gen = new GeneradorDiario(1);
		var lim = new LimitadorPorCantidad(20);

		var id = calendario.crearEventoDiaCompleto(CUATRODENOVIEMBRE, 1);
		calendario.modificarReglaDeRepeticion(calendario.buscarEventoPorId(id), gen);
		calendario.modificarCantidadDeRepeticiones(calendario.buscarEventoPorId(id),lim);

		var evento = calendario.buscarEventoPorId(id);
		var alarmaID = calendario.agregarAlarmaRelativa(evento, Duration.ofMinutes(30), EfectoEnum.EMAIL);

		var eventos = calendario.obtenerProximosElementos(10, CUATRODENOVIEMBRE.atStartOfDay());

		Evento evento2 = (Evento) eventos.get(9);
		calendario.modificarEventoDiaCompleto(evento2,
			"ASD",
			"asd",
			CUATRODENOVIEMBRE.plusDays(1),
			2);

		assertTrue(calendario.eliminarAlarma(evento2, alarmaID));

		assertEquals("ASD", evento.getTitulo());
		assertEquals(CUATRODENOVIEMBRE.plusDays(1).atStartOfDay(), evento.getInicio());
		assertFalse(evento.eliminarAlarmaPorID(alarmaID));
	}
	@Test

	public void eliminarEventoEliminaTodasLasRepeticiones() {
		var id = calendario.crearEventoDiaCompleto(CUATRODENOVIEMBRE, 1);
		var evento = calendario.buscarEventoPorId(id);

		var gen = new GeneradorDiario(1);
		var lim = new LimitadorPorCantidad(20);

		calendario.modificarReglaDeRepeticion(evento,  gen);
		calendario.modificarCantidadDeRepeticiones(evento,lim);

		var eventos = calendario.obtenerProximosElementos(10, CUATRODENOVIEMBRE.atStartOfDay());

		Evento evento2 = (Evento) eventos.get(9);
		calendario.eliminarEventoPorId(evento2.getId());

		assertEquals(0, calendario.obtenerProximosElementos(10, CUATRODENOVIEMBRE.atStartOfDay()).size());
	}
	@Test

	public void cambiarLaFrecuenciaDeRepeticionesFuncionaCorrectamente() {
		var id1 = calendario.crearEventoDiaCompleto(CUATRODENOVIEMBRE, 1L);
		var lUnico = calendario.obtenerProximosElementos(10, CUATRODENOVIEMBRE.minusDays(1).atStartOfDay());

		var gen1 = new GeneradorDiario(1);
		var lim1 = new LimitadorPorCantidad(2);
		calendario.modificarReglaDeRepeticion(calendario.buscarEventoPorId(id1), gen1);
		calendario.modificarCantidadDeRepeticiones(calendario.buscarEventoPorId(id1),lim1);

		var lDiario = calendario.obtenerProximosElementos(10, CUATRODENOVIEMBRE.minusDays(1).atStartOfDay());

		var gen2 = new GeneradorAnual();
		var lim2 = new LimitadorPorCantidad(3);

		calendario.modificarReglaDeRepeticion(calendario.buscarEventoPorId(id1), gen2);
		calendario.modificarCantidadDeRepeticiones(calendario.buscarEventoPorId(id1),lim2);

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

		var gen1 = new GeneradorDiario(4);
		var lim1 = new LimitadorPorCantidad(2);
		var id3 = calendario.crearEventoDiaCompleto(CUATRODENOVIEMBRE, 1L);

		calendario.modificarReglaDeRepeticion(calendario.buscarEventoPorId(id3), gen1);
		calendario.modificarCantidadDeRepeticiones(calendario.buscarEventoPorId(id3),lim1);

		var gen2 = new GeneradorMensual();
		var lim2 = new LimitadorPorCantidad(3);

		var id4 = calendario.crearEvento(CUATRODENOVIEMBRE.plusDays(2).atTime(LocalTime.NOON), Duration.ofHours(2));
		calendario.modificarReglaDeRepeticion(calendario.buscarEventoPorId(id4), gen2);
		calendario.modificarCantidadDeRepeticiones(calendario.buscarEventoPorId(id4), lim2);

		var l = calendario.obtenerProximosElementos(7, CUATRODENOVIEMBRE.minusDays(1).atStartOfDay());

		assertEquals(id3, l.get(0).getId());
		assertEquals(id2, l.get(1).getId());
		assertEquals(id1, l.get(2).getId());
		assertEquals(id4, l.get(3).getId());
		assertEquals(id3, l.get(4).getId());
		assertEquals(id4, l.get(5).getId());
		assertEquals(7, l.size());

	}

	@Test

	public void agregarAlarmaLaCreaConElEfectoDeseado(){
		var id = calendario.crearEventoDiaCompleto(CUATRODENOVIEMBRE, 1);
		var evento = calendario.buscarEventoPorId(id);
		var efectoEnum = EfectoEnum.EMAIL;
		var alarmaID = calendario.agregarAlarmaRelativa(evento, Duration.ofMinutes(30), efectoEnum);

		assertEquals(efectoEnum, evento.obtenerAlarmaPorID(alarmaID).disparar());
	}

	@Test
	public void persistenciaFuncionaCorrectamente() throws IOException {
		var id1 = calendario.crearTareaDiaCompleto(CUATRODENOVIEMBRE);
		var id2 = calendario.crearTareaDiaCompleto(CUATRODENOVIEMBRE);
		var id3 = calendario.crearEventoDiaCompleto(CUATRODENOVIEMBRE, 1);

		var t1 = calendario.buscarTareaPorId(id1);
		t1.setTitulo("t1");

		var e1 = calendario.buscarEventoPorId(id3);
		e1.setTitulo("e1");

		var h = LocalDateTime.of(2023, 11, 4, 0, 0);

		var alarma = new AlarmaAbsoluta(0, h, new EfectoSinEfecto());
		t1.agregarAlarma(alarma);
		e1.agregarAlarma(alarma);

		var alarma2 = new AlarmaRelativa(1, Duration.ofMinutes(20), new EfectoEnviarMail());
		t1.agregarAlarma(alarma2);
		e1.agregarAlarma(alarma2);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        calendario.serializar(bytes);

        Calendario cDes = Calendario.deserializar(new ByteArrayInputStream(bytes.toByteArray()));

		var t1Des = cDes.buscarTareaPorId(id1);
		var e1Des = cDes.buscarEventoPorId(id3);

        assertNotNull(cDes);
		assertEquals(t1.getTitulo(), t1Des.getTitulo());
		assertEquals(t1.getDescripcion(), t1Des.getDescripcion());
		assertEquals(t1.getEstaCompletada(), t1Des.getEstaCompletada());

		assertEquals(alarma.getTiempo(h), t1Des.obtenerAlarmaPorID(0).getTiempo(h));
		assertEquals(alarma2.getTiempo(h), t1Des.obtenerAlarmaPorID(1).getTiempo(h));
		assertEquals(alarma.efecto.realizar(), t1Des.obtenerAlarmaPorID(0).efecto.realizar());

		assertEquals(e1.getTitulo(), e1Des.getTitulo());
		assertEquals(e1.getDescripcion(), e1Des.getDescripcion());

		assertEquals(alarma.getTiempo(h), e1Des.obtenerAlarmaPorID(0).getTiempo(h));
		assertEquals(alarma2.getTiempo(h), e1Des.obtenerAlarmaPorID(1).getTiempo(h));
		assertEquals(alarma.efecto.realizar(), e1Des.obtenerAlarmaPorID(0).efecto.realizar());

    }

	@Test
	public void repeticionDeEventosFuncionaCorrectamenteConPersistencia() throws IOException {
		var id1 = calendario.crearTareaDiaCompleto(CUATRODENOVIEMBRE.plusDays(1));
		var id2 = calendario.crearTareaPuntual(CUATRODENOVIEMBRE.atStartOfDay().plusHours(2));

		var diasActivo =  new DiasActivos(true,
                false,
                false,
                true,
                false,
                true,
                false);
		var gen1 = new GeneradorSemanal(diasActivo);
		var lim1 = new LimitadorPorFecha(CUATRODENOVIEMBRE.plusDays(8).atStartOfDay());
		var id3 = calendario.crearEventoDiaCompleto(CUATRODENOVIEMBRE, 1L);

		calendario.modificarReglaDeRepeticion(calendario.buscarEventoPorId(id3), gen1);
		calendario.modificarCantidadDeRepeticiones(calendario.buscarEventoPorId(id3),lim1);

		var gen2 = new GeneradorMensual();
		var lim2 = new LimitadorPorCantidad(3);
		var id4 = calendario.crearEvento(CUATRODENOVIEMBRE.plusDays(2).atTime(LocalTime.NOON), Duration.ofHours(2));

		calendario.modificarReglaDeRepeticion(calendario.buscarEventoPorId(id4), gen2);
		calendario.modificarCantidadDeRepeticiones(calendario.buscarEventoPorId(id4), lim2);

		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        calendario.serializar(bytes);
        Calendario cDes = Calendario.deserializar(new ByteArrayInputStream(bytes.toByteArray()));

		var l = calendario.obtenerProximosElementos(7, CUATRODENOVIEMBRE.minusDays(1).atStartOfDay());
		var lDes = cDes.obtenerProximosElementos(7, CUATRODENOVIEMBRE.minusDays(1).atStartOfDay());

		assertEquals(l.get(0).getId(), lDes.get(0).getId());
		assertEquals(l.get(1).getId(), lDes.get(1).getId());
		assertEquals(l.get(2).getId(), lDes.get(2).getId());
		assertEquals(l.get(3).getId(), lDes.get(3).getId());
		assertEquals(l.get(4).getId(), lDes.get(4).getId());
		assertEquals(l.get(5).getId(), lDes.get(5).getId());
		assertEquals(l.size(), lDes.size());

	}
}