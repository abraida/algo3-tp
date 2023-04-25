public class RepetidorDeEventosDiario extends RepetidorDeEventos {

	private final long dias;

	public RepetidorDeEventosDiario(Evento elemento, long dias, long repeticiones) {
		super(elemento, repeticiones);
		this.dias = dias;
	}
	@Override
	protected Evento generarInstanciaUnica(int n) {
		var i = elemento.getInicio().plusDays(dias * n);

		return elemento.crearCopiaDesplazada(i);
	}

}