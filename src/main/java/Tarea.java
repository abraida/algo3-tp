
public abstract class Tarea extends Elemento {

	private boolean estaCompletada;


	public Tarea(long id) {
		super(id);
		this.estaCompletada = false;
	}

	public boolean getEstaCompletada() {
		return estaCompletada;
	}

	public void setEstaCompletada(boolean estaCompletada) {
		this.estaCompletada = estaCompletada;
	}

}
