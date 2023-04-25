import org.junit.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.*;


public class TareaTest {

	@Test

	public void modificarTituloConCadenaVacia() {
		var fecha = LocalDate.now();
		var tarea = new TareaDiaria(0, fecha);

		tarea.setTitulo("");
		var titulo = tarea.getTitulo();

		assertEquals("", titulo);
	}

	@Test

	public void modificarTituloFuncionaCorrectamente() {
		var fecha = LocalDate.now();
		var tarea = new TareaDiaria(0, fecha);

		tarea.setTitulo("Nombre B1");
		var titulo = tarea.getTitulo();

		assertEquals("Nombre B1", titulo);
	}

	@Test

	public void modificarDescripcionConCadenaVacia() {
		var fecha = LocalDate.now();
		var tarea = new TareaDiaria(0, fecha);

		tarea.setDescripcion("");
		var descripcion = tarea.getDescripcion();

		assertEquals("", descripcion);
	}

	@Test

	public void modificarDescripcionFuncionaCorrectamente() {
		var fecha = LocalDate.now();
		var tarea = new TareaDiaria(0, fecha);

		tarea.setDescripcion("aaaaaaaaaaaaaa \n\n\n 000000000000000");
		var descripcion = tarea.getDescripcion();

		assertEquals("aaaaaaaaaaaaaa \n\n\n 000000000000000", descripcion);
	}

	@Test

	public void modificarEstadoFuncionaCorrectamente() {
		var fecha = LocalDate.now();
		var tarea = new TareaDiaria(0, fecha);

		tarea.setEstado(true);
		var estado = tarea.getEstado();

		assertEquals(true, estado);
	}

	@Test

	public void tareaDiariaEsPosteriorADiaAnterior() {
		var fecha = LocalDate.parse("2023-11-04");
		var fechaYHora = fecha.minusDays(1).atTime(LocalTime.MAX);
		var tarea = new TareaDiaria(0, fecha);

		var condicion = tarea.esPosteriorA(fechaYHora);

		assertTrue(condicion);
	}

	@Test

	public void tareaDiariaNoEsPosteriorAlMismoDia() {
		var fecha = LocalDate.parse("2023-11-04");
		var fechaYHora = fecha.atStartOfDay();
		var tarea = new TareaDiaria(0, fecha);

		var condicion = tarea.esPosteriorA(fechaYHora);

		assertFalse(condicion);
	}

	@Test

	public void tareaDiariaEsSimultaneaAlMismoDia() {
		var fecha = LocalDate.parse("2023-11-04");
		var fechaYHora = fecha.atStartOfDay();
		var tarea = new TareaDiaria(0, fecha);

		var condicion = tarea.esSimultaneoA(fechaYHora);

		assertTrue(condicion);
	}

	@Test

	public void tareaDiariaNoEsSimultaneaADiaAnterior() {
		var fecha = LocalDate.parse("2023-11-04");
		var fechaYHora = fecha.minusDays(1).atTime(LocalTime.MAX);
		var tarea = new TareaDiaria(0, fecha);

		var condicion = tarea.esSimultaneoA(fechaYHora);

		assertFalse(condicion);
	}

	@Test

	public void tareaDiariaNoEsSimultaneaADiaPosterior() {
		var fecha = LocalDate.parse("2023-11-04");
		var fechaYHora = fecha.plusDays(1).atStartOfDay();
		var tarea = new TareaDiaria(0, fecha);

		var condicion = tarea.esPosteriorA(fechaYHora);

		assertFalse(condicion);
	}

	@Test

	public void tareaDiariaEsAnteriorADiaPosterior() {
		var fecha = LocalDate.parse("2023-11-04");
		var fechaYHora = fecha.plusDays(1).atStartOfDay();
		var tarea = new TareaDiaria(0, fecha);

		var condicion = tarea.esAnteriorA(fechaYHora);

		assertTrue(condicion);
	}

	@Test

	public void tareaDiariaNoEsAnteriorAlMismoDia() {
		var fecha = LocalDate.parse("2023-11-04");
		var fechaYHora = fecha.atStartOfDay();
		var tarea = new TareaDiaria(0, fecha);

		var condicion = tarea.esAnteriorA(fechaYHora);

		assertFalse(condicion);
	}

	@Test

	public void tareaDiariaEsMenorATareaPuntalPosterior() {
		var fecha = LocalDate.parse("2023-11-04");
		var tarea = new TareaDiaria(0, fecha);

		var fechaYHora = fecha.atStartOfDay().plusDays(1);
		var tareaP = new TareaPuntual(0, fechaYHora);

		var condicion1 = tarea.compareTo(tareaP);
		var condicion2 = tareaP.compareTo(tarea);

		assertEquals(-1, condicion1);
		assertEquals(1, condicion2);
	}

	@Test

	public void tareaDiariaEsIgualATareaPuntalSimultanea() {
		var fecha = LocalDate.parse("2023-11-04");
		var tarea = new TareaDiaria(0, fecha);

		var fechaYHora = fecha.atTime(LocalTime.NOON);
		var tareaP = new TareaPuntual(0, fechaYHora);

		var condicion1 = tarea.compareTo(tareaP);
		var condicion2 = tareaP.compareTo(tarea);

		assertEquals(0, condicion1);
		assertEquals(0, condicion2);
	}

	@Test

	public void tareaDiariaEsMayorATareaPuntualAnterior() {
		var fecha = LocalDate.parse("2023-11-04");
		var tarea = new TareaDiaria(0, fecha);

		var fechaYHora = fecha.minusDays(1).atTime(LocalTime.MAX);
		var tareaP = new TareaPuntual(0, fechaYHora);

		var condicion1 = tarea.compareTo(tareaP);
		var condicion2 = tareaP.compareTo(tarea);

		assertEquals(1, condicion1);
		assertEquals(-1, condicion2);
	}

	@Test

	public void alarmaRelativaDeTareaDiariaSuenaAntesDeComenzarElDia() {
		var fecha = LocalDate.parse("2023-11-04");
		var tarea = new TareaDiaria(0, fecha);
		var tiempoRelativo = Duration.ofMinutes(30);
		var alarma = new AlarmaRelativa(tiempoRelativo, new EfectoSinEfecto());
		tarea.agregarAlarma(alarma);

		var t = tarea.obtenerTiempoDeAlarma(alarma);

		assertEquals(t, fecha.atStartOfDay().minusMinutes(30));
	}

	@Test

	public void alarmaRelativaDeTareaPuntualSuenaAntesDeVencimiento() {
		var fechaYHora = LocalDateTime.now();
		var tarea = new TareaPuntual(0, fechaYHora);
		var tiempoRelativo = Duration.ofMinutes(30);
		var alarma = new AlarmaRelativa(tiempoRelativo, new EfectoSinEfecto());
		tarea.agregarAlarma(alarma);

		var t = tarea.obtenerTiempoDeAlarma(alarma);

		assertEquals(t, fechaYHora.minusMinutes(30));
	}

	@Test

	public void alarmaAbsolutaDeTareaSuenaALaHoraMarcada() {
		var fecha = LocalDate.parse("2023-11-04");
		var tarea = new TareaDiaria(0, fecha);
		var horaMarcada = LocalDateTime.now();
		var alarma = new AlarmaAbsoluta(horaMarcada, new EfectoSinEfecto());
		tarea.agregarAlarma(alarma);

		var t = tarea.obtenerTiempoDeAlarma(alarma);

		assertEquals(t, horaMarcada);
	}

}