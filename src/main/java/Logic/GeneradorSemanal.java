package Logic;

public class GeneradorSemanal implements GeneradorDeInstancia {

	private final DiasActivos dias;

	public GeneradorSemanal(DiasActivos dias) {

		this.dias = dias;

	}

	@Override
	public Evento generarInstanciaUnica(Evento evento, int n) {
		var contador = 0;
		var tiempo = evento.getInicio().minusDays(1);

		while (contador <= n) {
			tiempo = tiempo.plusDays(1);

			if (dias.estaActivo(tiempo.getDayOfWeek())) {
				contador++;
			}
		}

		return evento.crearCopiaDesplazada(tiempo);
	}
}