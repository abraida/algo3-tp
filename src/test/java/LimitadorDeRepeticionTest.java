import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class LimitadorDeRepeticionTest {
	private final LocalDate CUATRODENOVIEMBRE = LocalDate.parse("2023-11-04");

    @Test
	public void generarEventosLimitadosPorFechaGeneraLaCantidadCorrecta() {
		var evento1 = new Evento(0, CUATRODENOVIEMBRE, 1L );
		var generador = new GeneradorDiario(1);
		var limitador = new LimitadorPorFecha(CUATRODENOVIEMBRE.plusDays(4).atStartOfDay());

		var r = new RepetidorDeEventos(evento1, limitador, generador);

		var eventos = r.generarInstancias(0, 10);

		assertEquals(4, eventos.size());
		assertEquals(CUATRODENOVIEMBRE.plusDays(3), eventos.get(eventos.size()-1).getInicio().toLocalDate());
	}

	@Test
	public void generarEventosInfinitosGeneraLaCantidadCorrecta() {
		var evento1 = new Evento(0, CUATRODENOVIEMBRE, 1L );
		var generador = new GeneradorDiario(1);
		var limitador = new LimitadorInfinito();

		var r = new RepetidorDeEventos(evento1, limitador, generador);

		var eventos = r.generarInstancias(0, 10);

		assertEquals(10, eventos.size());
		assertEquals(CUATRODENOVIEMBRE.plusDays(9), eventos.get(eventos.size()-1).getInicio().toLocalDate());
	}

	@Test
	public void generarEventosLimitadosPorCantidadGeneraLaCantidadCorrecta() {
		var evento1 = new Evento(0, CUATRODENOVIEMBRE, 1L );
		var generador = new GeneradorDiario(1);
		var limitador = new LimitadorPorCantidad(8);

		var r = new RepetidorDeEventos(evento1, limitador, generador);

		var eventos = r.generarInstancias(0, 10);

		assertEquals(8, eventos.size());
		assertEquals(CUATRODENOVIEMBRE.plusDays(7), eventos.get(eventos.size()-1).getInicio().toLocalDate());
	}
}