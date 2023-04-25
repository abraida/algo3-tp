import java.util.ArrayList;
import java.util.List;


public class RepetidorDeEventosSemanal extends RepetidorDeEventos {

	private final ArrayList<Boolean> diasActivo;

	public RepetidorDeEventosSemanal(Evento e, long repeticiones, Boolean lunesActivo,
		Boolean martesActivo,
		Boolean miercolesActivo,
		Boolean juevesActivo,
		Boolean viernesActivo,
		Boolean sabadoActivo,
		Boolean domingoActivo) {
		super(e, repeticiones);

		this.diasActivo = new ArrayList<>(
			List.of(lunesActivo,
				martesActivo,
				miercolesActivo,
				juevesActivo,
				viernesActivo,
				sabadoActivo,
				domingoActivo));
	}

	@Override
	protected Evento generarInstanciaUnica(int n) {
		var contador = 0;
		var tiempo = elemento.getInicio().minusDays(1);

		while (contador <= n) {
			tiempo = tiempo.plusDays(1);

			if (diasActivo.get(tiempo.getDayOfWeek().getValue() - 1)) {
				contador++;
			}
		}

		return elemento.crearCopiaDesplazada(tiempo);
	}
}