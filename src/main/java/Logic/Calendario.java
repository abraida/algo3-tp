package Logic;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

	public Tarea buscarTareaPorId(long id) {
		return listaDeTareas.stream()
				.filter(x -> x.getID() == id)
				.findAny()
				.orElse(null);
	}

	private RepetidorDeEventos buscarRepetidorPorIdDeEvento(long id) {
		return listaDeRepetidores.stream()
				.filter(x -> x.getElemento().getID() == id)
				.findAny()
				.orElse(null);
	}

	public Evento buscarEventoPorId(long id) {
		var r = listaDeRepetidores.stream()
				.filter(x -> x.getElemento().getID() == id)
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
		var repetidorExistente = buscarRepetidorPorIdDeEvento(e.getID());

		if (repetidorExistente != null) {
			repetidorExistente.modificarFrecuenciaDeRepeticion(reglaDeRepeticion);
		}
	}

	public void modificarCantidadDeRepeticiones(Evento e, LimitadorDeRepeticion limitador){
		var repetidorExistente = buscarRepetidorPorIdDeEvento(e.getID());

		if (repetidorExistente != null) {
			repetidorExistente.modificarLimiteDeRepeticiones(limitador);
		}
	}

	public boolean modificarEventoDiaCompleto(EventoDiario evento,
											  String titulo,
											  String descripcion,
											  LocalDate fecha,
											  LocalDate fin) {

		var repetidor = buscarRepetidorPorIdDeEvento(evento.getID());

		if (repetidor == null)
			return false;

		var e = (EventoDiario) repetidor.getElemento();

		this.modificarAtributosElemento(e, titulo, descripcion);
		e.setFecha(fecha);
		e.setFechaFinal(fin);

		return true;
	}

	public boolean modificarEventoPuntual(EventoPuntual evento,
								   String titulo,
								   String descripcion,
								   LocalDateTime inicio,
								   LocalDateTime fin) {

		var repetidor = buscarRepetidorPorIdDeEvento(evento.getID());

		if (repetidor == null)
			return false;

		var e = (EventoPuntual) repetidor.getElemento();

		this.modificarAtributosElemento(e, titulo, descripcion);
		e.setInicio(inicio);
		e.setFin(fin);

		return true;
	}


	public boolean eliminarTareaPorId(long id) {
		return this.listaDeTareas.removeIf(e -> e.getID() == id);
	}

	public boolean eliminarEventoPorId(long id) {
		return this.listaDeRepetidores.removeIf(r -> r.getElemento().getID() == id);
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
		var repetidor = buscarRepetidorPorIdDeEvento(elemento.getID());

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

	public ArrayList<Elemento> generarElementos(LocalDateTime inicio, LocalDateTime fin) {
		var elementos = new ArrayList<Elemento>(this.listaDeTareas);

		for (RepetidorDeEventos e: this.listaDeRepetidores) {
			elementos.addAll(e.generarEventosPosteriores(1000, inicio));
		}

		var elementosFiltrados = elementos.stream()
				.filter(e -> e.esSimultaneoA(fin) ||
						e.esSimultaneoA(inicio) ||
						(e.esAnteriorA(fin) &&
								e.esPosteriorA(inicio)))
				.sorted().toList();

		if (elementosFiltrados.size() == 0)
			return new ArrayList<>();

		return new ArrayList<>(elementosFiltrados);
	}

	public ArrayList<Elemento> obtenerElementosDiarios(LocalDate fecha) {
                return generarElementos(fecha.atStartOfDay(),
						fecha.atTime(LocalTime.MAX));
	}

	public ArrayList<Elemento> obtenerElementosSemanales(LocalDate fecha) {
		var inicio = fecha.minusDays(fecha.getDayOfWeek().getValue() - 1).atStartOfDay();
		var fin = fecha.plusDays(7 - fecha.getDayOfWeek().getValue()).atTime(LocalTime.MAX);
		return generarElementos(inicio, fin);
	}

	public ArrayList<Elemento> obtenerElementosMensuales(LocalDate fecha) {
		var inicio = fecha.withDayOfMonth(1).atStartOfDay();
		var fin = fecha.withDayOfMonth(fecha.getMonth().length(fecha.isLeapYear())).atTime(LocalTime.MAX);
		return generarElementos(inicio, fin);
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

    public long agregarTarea(Tarea t) {
		t.setID(idActual);
		this.listaDeTareas.add(t);

		this.idActual++;
		return this.idActual - 1;
	}

	public long agregarEvento(RepetidorDeEventos repetidor) {
		repetidor.getElemento().setID(idActual);
		this.listaDeRepetidores.add(repetidor);

		this.idActual++;
		return this.idActual - 1;
	}
}