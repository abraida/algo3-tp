public class GeneradorMensual implements GeneradorDeInstancia {

	@Override
	public Evento generarInstanciaUnica(Evento evento, int n) {
		var i = evento.getInicio().plusMonths(n);

		return evento.crearCopiaDesplazada(i);
	}
}