import java.util.ArrayList;


public abstract class Elemento implements Identificable, Alarmable, Suceso {
	private String titulo;
	private String descripcion;

	private final long id;
	protected final ArrayList<Alarma> alarmas;

    public Elemento (long id) {
		this.id = id;
		this.titulo = "";
		this.descripcion = "";
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

	@Override
    public long agregarAlarma(Alarma alarma) {
		alarmas.add(alarma);
		return alarma.getId();
	}

	@Override
	public boolean eliminarAlarmaPorID(long alarmaID) {
		return this.alarmas.removeIf(e -> e.getId() == id);
	}

	@Override
	public Alarma obtenerAlarmaPorID(long alarmaID) {
		return alarmas.stream()
				.filter(x -> x.getId() == alarmaID)
				.findAny()
				.orElse(null);
	}

	@Override
	public long getId() {
		return this.id;
	}
}