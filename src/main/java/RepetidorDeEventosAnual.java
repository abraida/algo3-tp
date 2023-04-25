public class RepetidorDeEventosAnual extends RepetidorDeEventos {

	public RepetidorDeEventosAnual(Evento elemento, long repeticiones) {
		super(elemento, repeticiones);
	}

	@Override
	protected Evento generarInstanciaUnica(int n) {
		var i = elemento.getInicio().plusYears(n);

		return elemento.crearCopiaDesplazada(i);
	}
}