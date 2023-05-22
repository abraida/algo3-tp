public class GeneradorUnico implements GeneradorDeInstancia {

	@Override
	public Evento generarInstanciaUnica(Evento evento, int n) {

		var i = evento.getInicio();

		return evento.crearCopiaDesplazada(i);
	}
}