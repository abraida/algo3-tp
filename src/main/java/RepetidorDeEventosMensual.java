public class RepetidorDeEventosMensual extends RepetidorDeEventos {

	public RepetidorDeEventosMensual(Evento elemento, long repeticiones) {
		super(elemento, repeticiones);
	}

	@Override
	protected Evento generarInstanciaUnica(int n) {
		var i = elemento.getInicio().plusMonths(n);

		return elemento.crearCopiaDesplazada(i);
	}
}