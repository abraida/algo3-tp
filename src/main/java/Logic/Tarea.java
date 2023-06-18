package Logic;

public abstract class Tarea extends Elemento {

    private boolean estaCompletada;

    public Tarea(String titulo, String descripcion) {
        super(titulo, descripcion);
        this.estaCompletada = false;
    }

    public boolean getEstaCompletada() {
        return estaCompletada;
    }

    public void setEstaCompletada(boolean estaCompletada) {
        this.estaCompletada = estaCompletada;
    }

}
