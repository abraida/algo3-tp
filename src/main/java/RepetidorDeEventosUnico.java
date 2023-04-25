public class RepetidorDeEventosUnico extends RepetidorDeEventos {

	public RepetidorDeEventosUnico(Evento elemento) {
		super(elemento, 1);
	}
	@Override
	protected Evento generarInstanciaUnica(int n) {

		var i = elemento.getInicio();

		return elemento.crearCopiaDesplazada(i);
	}
}