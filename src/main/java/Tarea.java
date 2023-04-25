import java.util.ArrayList;


public abstract class Tarea implements Elemento {

	protected String titulo;
	protected String descripcion;
	protected Boolean estado;

	private final long id;

	private final ArrayList<Alarma> alarmas;

	public Tarea(long id) {
		this.id = id;
		this.titulo = "";
		this.descripcion = "";
		this.estado = false;
		this.alarmas = new ArrayList<>();
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public void agregarAlarma(Alarma alarma) {
		alarmas.add(alarma);
	}

	@Override

	public boolean eliminarAlarma(Alarma alarma) {
		return alarmas.remove(alarma);
	}

	public long getId() {
		return this.id;
	}
}
