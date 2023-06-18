import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Calendario implements Serializable {

	private final ArrayList<Tarea> listaDeTareas;

	private final ArrayList<RepetidorDeEventos> listaDeRepetidores;

	private long idActual;

	private final CreadorDeAlarmas creadorDeAlarmas;
	transient private final Gson gson;

	public Calendario() {
		this.idActual = 0;
		this.listaDeTareas = new ArrayList<>();
		this.listaDeRepetidores = new ArrayList<>();
		this.creadorDeAlarmas = new CreadorDeAlarmas();
		this.gson = new CreadorDeGson().crearGson();
	}

	public long crearTareaPuntual(LocalDateTime vencimiento) {
		var t = new TareaPuntual(this.idActual, vencimiento);

		this.listaDeTareas.add(t);

		this.idActual++;
		return this.idActual - 1;
	}

	public long crearTareaDiaCompleto(LocalDate fecha) {
		var t = new TareaDiaria(this.idActual, fecha);

		this.listaDeTareas.add(t);

		this.idActual++;
		return this.idActual - 1;
	}

	public long crearEvento(LocalDateTime inicio, Duration duracion) {
		var e = new Evento(this.idActual, inicio, duracion);
		var repetidor = new RepetidorDeEventos(e, new LimitadorPorCantidad(1), new GeneradorUnico());

		listaDeRepetidores.add(repetidor);

		this.idActual++;
		return this.idActual - 1;
	}

	public long crearEventoDiaCompleto(LocalDate fecha, long duracionDias) {
		var e = new Evento(this.idActual, fecha, duracionDias);
		var repetidor = new RepetidorDeEventos(e, new LimitadorPorCantidad(1), new GeneradorUnico());

		listaDeRepetidores.add(repetidor);

		this.idActual++;
		return this.idActual - 1;
	}

	public Tarea buscarTareaPorId(long id) {
		return listaDeTareas.stream()
				.filter(x -> x.getId() == id)
				.findAny()
				.orElse(null);
	}

	private RepetidorDeEventos buscarRepetidorPorIdDeEvento(long id) {
		return listaDeRepetidores.stream()
				.filter(x -> x.getElemento().getId() == id)
				.findAny()
				.orElse(null);
	}

	public Evento buscarEventoPorId(long id) {
		var r = listaDeRepetidores.stream()
				.filter(x -> x.getElemento().getId() == id)
				.findAny()
				.orElse(null);

		if (r != null)
			return r.getElemento();
		return null;
	}

	private void modificarAtributosElemento(Elemento e, String titulo, String descripcion) {
		e.setTitulo(titulo);
		e.setDescripcion(descripcion);
	}

	public void modificarTareaDiaCompleto(TareaDiaria tarea,
										  String titulo,
										  String descripcion,
										  LocalDate dia,
										  boolean estaCompletada) {

		this.modificarAtributosElemento(tarea, titulo, descripcion);
		tarea.setEstaCompletada(estaCompletada);
		tarea.setFecha(dia);
	}

	public void modificarTareaPuntual(TareaPuntual tarea,
									  String titulo,
									  String descripcion,
									  LocalDateTime vencimiento,
									  boolean estaCompletada) {

		this.modificarAtributosElemento(tarea, titulo, descripcion);
		tarea.setEstaCompletada(estaCompletada);
		tarea.setVencimiento(vencimiento);
	}


	public void marcarTareaComoCompletada(Tarea tarea, boolean completada) {
		tarea.setEstaCompletada(completada);
	}

	public void modificarReglaDeRepeticion(Evento e, GeneradorDeInstancia reglaDeRepeticion){
		var repetidorExistente = buscarRepetidorPorIdDeEvento(e.getId());

		if (repetidorExistente != null) {
			repetidorExistente.modificarFrecuenciaDeRepeticion(reglaDeRepeticion);
		}
	}

	public void modificarCantidadDeRepeticiones(Evento e, LimitadorDeRepeticion limitador){
		var repetidorExistente = buscarRepetidorPorIdDeEvento(e.getId());

		if (repetidorExistente != null) {
			repetidorExistente.modificarLimiteDeRepeticiones(limitador);
		}
	}

	public boolean modificarEventoDiaCompleto(Evento evento,
											  String titulo,
											  String descripcion,
											  LocalDate fecha,
											  long duracionDias) {

		var repetidor = buscarRepetidorPorIdDeEvento(evento.getId());

		if (repetidor == null)
			return false;

		var e = repetidor.getElemento();

		this.modificarAtributosElemento(e, titulo, descripcion);
		e.setInicio(fecha.atStartOfDay());
		e.setDuracion(Duration.ofDays(duracionDias));

		return true;
	}

	public boolean modificarEvento(Evento evento,
								   String titulo,
								   String descripcion,
								   LocalDateTime inicio,
								   Duration duracion) {

		var repetidor = buscarRepetidorPorIdDeEvento(evento.getId());

		if (repetidor == null)
			return false;

		var e = repetidor.getElemento();

		this.modificarAtributosElemento(e, titulo, descripcion);
		e.setInicio(inicio);
		e.setDuracion(duracion);

		return true;
	}

	public boolean eliminarTareaPorId(long id) {
		return this.listaDeTareas.removeIf(e -> e.getId() == id);
	}

	public boolean eliminarEventoPorId(long id) {
		return this.listaDeRepetidores.removeIf(r -> r.getElemento().getId() == id);
	}


	public long agregarAlarmaRelativa(Alarmable objeto, Duration antelacion, EfectoEnum efecto) {

		Alarma alarma = creadorDeAlarmas.crearAlarmaRelativa(antelacion, efecto);

		return objeto.agregarAlarma(alarma);
	}

	public long agregarAlarmaAbsoluta(Alarmable objeto, LocalDateTime tiempo, EfectoEnum efecto) {

		Alarma alarma = creadorDeAlarmas.crearAlarmaAbsoluta(tiempo, efecto);

		return objeto.agregarAlarma(alarma);


	}

	public boolean eliminarAlarma(Elemento elemento, long alarmaId) {
		var repetidor = buscarRepetidorPorIdDeEvento(elemento.getId());

		if (repetidor != null) {
			var e = repetidor.getElemento();
			return e.eliminarAlarmaPorID(alarmaId);
		}

		return elemento.eliminarAlarmaPorID(alarmaId);
	}


	public ArrayList<Elemento> obtenerProximosElementos(int cantidad, LocalDateTime tiempo) {
		if (cantidad <= 0)
			return new ArrayList<>();

		var elementos = new ArrayList<Elemento> (this.listaDeTareas.stream()
				.filter(tarea -> tarea.esPosteriorA(tiempo) || tarea.esSimultaneoA(tiempo))
				.sorted()
				.toList());

		for (RepetidorDeEventos e: this.listaDeRepetidores) {
			elementos.addAll(e.generarEventosPosteriores(cantidad, tiempo));
		}

		elementos = new ArrayList<>(elementos.stream()
				.sorted()
				.toList());

		if (elementos.size() == 0)
			return new ArrayList<>();

		return new ArrayList<>(elementos.subList(0, Math.min(cantidad, elementos.size())));
	}

	public void serializar(OutputStream os) throws IOException {
		String json = this.gson.toJson(this);
		os.write(json.getBytes());
		os.flush();
    }

    public static Calendario deserializar(InputStream is) throws JsonParseException {
		var c = new Calendario();
		var reader = new InputStreamReader(is);
		return c.gson.fromJson(reader, Calendario.class);
    }
}