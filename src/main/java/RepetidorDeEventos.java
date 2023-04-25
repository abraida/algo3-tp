import java.time.LocalDateTime;
import java.util.ArrayList;


public abstract class RepetidorDeEventos {
	protected abstract Evento generarInstanciaUnica(int n);
	protected final long repeticiones;

	protected final Evento elemento;

	public Evento getElemento() {
		return elemento;
	}

	public RepetidorDeEventos(Evento elemento, long repeticiones) {
		this.elemento = elemento;
		this.repeticiones = repeticiones;
	}

	public ArrayList<Evento> generarInstancias(int inicio, int fin) {
		var instancias = new ArrayList<Evento>();

		for (int i = inicio; i < fin && i  <  repeticiones; i++) {
			instancias.add(generarInstanciaUnica(i));
		}

		return instancias;
	}

	public ArrayList<Evento> generarEventosPosteriores(int cantidad, LocalDateTime tiempo) {
		var instancias = new ArrayList<Evento>();

		for (int i = 0; i < repeticiones && instancias.size() < cantidad; i++) {
			var instancia = generarInstanciaUnica(i);

			if (!instancia.esAnteriorA(tiempo)) {
				instancias.add(instancia);
			}
		}

		return instancias;
	}
}