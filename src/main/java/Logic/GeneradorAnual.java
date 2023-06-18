package Logic;

public class GeneradorAnual implements GeneradorDeInstancia {

	@Override
	public Evento generarInstanciaUnica(Evento evento, int n) {
		var i = evento.getInicio().plusYears(n);

		return evento.crearCopiaDesplazada(i);
	}
}