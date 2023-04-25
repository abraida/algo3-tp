import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class Calendario {

	private final ArrayList<Tarea> listaDeTareas;

	private final ArrayList<RepetidorDeEventos> listaDeRepetidores;

	private long idActual;

	public Calendario() {
		this.idActual = 0;
		this.listaDeTareas = new ArrayList<>();
		this.listaDeRepetidores = new ArrayList<>();
	}

	public boolean eliminarAlarma(Elemento elemento, Alarma alarma) {
		var repetidor = buscarRepetidorPorIdDeEvento(elemento.getId());

		if (repetidor != null) {
			var e = repetidor.getElemento();
			return e.eliminarAlarma(alarma);
		}

		return elemento.eliminarAlarma(alarma);
	}

	public long crearTareaPuntual(LocalDateTime vencimiento) {
		var t = new TareaPuntual(this.idActual, vencimiento);

		this.listaDeTareas.add(t);
		var id = this.idActual;
		this.idActual++;
		return id;
	}

	public long crearTareaDiaCompleto(LocalDate fecha) {
		var t = new TareaDiaria(this.idActual, fecha);

		this.listaDeTareas.add(t);
		var id = this.idActual;
		this.idActual++;
		return id;
	}

	private void modificarAtributosTarea(Tarea tarea, String titulo, String descripcion, boolean estado) {
		tarea.setTitulo(titulo);
		tarea.setDescripcion(descripcion);
		tarea.setEstado(estado);
	}

	public Tarea buscarTareaPorId(long id) {
		return listaDeTareas.stream()
			.filter(x -> x.getId() == id)
			.findAny()
			.orElse(null);
	}

	public void modificarTareaDiaCompleto(TareaDiaria tarea,
		String titulo,
		String descripcion,
		LocalDate dia,
		boolean estado) {

		this.modificarAtributosTarea(tarea, titulo, descripcion, estado);
		tarea.setFecha(dia);
	}

	public void modificarTareaPuntual(TareaPuntual tarea,
		String titulo,
		String descripcion,
		LocalDateTime vencimiento,
		boolean estado) {

		this.modificarAtributosTarea(tarea, titulo, descripcion, estado);
		tarea.setVencimiento(vencimiento);
	}

	public void marcarTareaComoCompletada(Tarea tarea, boolean completada) {
		tarea.setEstado(completada);
	}

	public boolean eliminarTareaPorId(long id) {
		return this.listaDeTareas.removeIf(e -> e.getId() == id);
	}

	private Efecto obtenerEfectoAPartirDeEnum(EfectoEnum e) {
		Efecto efecto;

		if (e == EfectoEnum.EMAIL)
			efecto = new EfectoEnviarMail();
		else if (e == EfectoEnum.SONIDO)
			efecto = new EfectoReproducirSonido();
		else if (e == EfectoEnum.NOTIFICACION)
			efecto = new EfectoNotificacion();
		else
			efecto = new EfectoSinEfecto();

		return efecto;
	}

	public Alarma agregarAlarmaRelativa(Alarmable objeto, Duration tiempoRelativo, EfectoEnum tipoDeEfecto) {
		var efecto = this.obtenerEfectoAPartirDeEnum(tipoDeEfecto);

		AlarmaRelativa alarma = new AlarmaRelativa(tiempoRelativo, efecto);

		objeto.agregarAlarma(alarma);
		return alarma;
	}

	public Alarma agregarAlarmaAbsoluta(Alarmable objeto, LocalDateTime tiempo, EfectoEnum tipoDeEfecto) {
		var efecto = this.obtenerEfectoAPartirDeEnum(tipoDeEfecto);

		AlarmaAbsoluta alarma = new AlarmaAbsoluta(tiempo, efecto);

		objeto.agregarAlarma(alarma);
		return alarma;
	}

	public long crearEventoUnico(LocalDateTime inicio, Duration duracion) {
		var e = new Evento(this.idActual, inicio, duracion);
		var repetidorUnico = new RepetidorDeEventosUnico(e);
		listaDeRepetidores.add(repetidorUnico);
		var id = this.idActual;
		this.idActual++;
		return id;
	}

	public long crearEventoUnicoDiaCompleto(LocalDate fecha, long duracionDias) {
		var e = new Evento(this.idActual, fecha, duracionDias);
		var repetidorUnico = new RepetidorDeEventosUnico(e);
		listaDeRepetidores.add(repetidorUnico);
		var id = this.idActual;
		this.idActual++;
		return id;
	}

	public boolean eliminarEventoPorId(long id) {
		return this.listaDeRepetidores.removeIf(r -> r.getElemento().getId() == id);
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

	public void modificarAtributosDeEvento(Evento e, String titulo, String descripcion) {
		e.setTitulo(titulo);
		e.setDescripcion(descripcion);
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

		this.modificarAtributosDeEvento(e, titulo, descripcion);
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

		this.modificarAtributosDeEvento(e, titulo, descripcion);
		e.setInicio(inicio);
		e.setDuracion(duracion);

		return true;
	}

	public void agregarRepeticionDiaria(Evento e,
		int periodoEnDias, int repeticiones) {

		var repetidorExistente = buscarRepetidorPorIdDeEvento(e.getId());

		if (repetidorExistente != null) {
			listaDeRepetidores.removeIf(rep -> rep.getElemento().getId() == e.getId());
		}

		var repetible = new RepetidorDeEventosDiario(e, periodoEnDias, repeticiones);
		this.listaDeRepetidores.add(repetible);
	}

	public void agregarRepeticionSemanal(Evento e,
		int repeticiones,
		Boolean lunesActivo,
		Boolean martesActivo,
		Boolean miercolesActivo,
		Boolean juevesActivo,
		Boolean viernesActivo,
		Boolean sabadoActivo,
		Boolean domingoActivo) {

		var repetidorExistente = buscarRepetidorPorIdDeEvento(e.getId());

		if (repetidorExistente != null) {
			listaDeRepetidores.removeIf(rep -> rep.getElemento().getId() == e.getId());
		}

		var repetible = new RepetidorDeEventosSemanal(e,
			repeticiones,
			lunesActivo,
			martesActivo,
			miercolesActivo,
			juevesActivo,
			viernesActivo,
			sabadoActivo,
			domingoActivo);
		this.listaDeRepetidores.add(repetible);
	}

	public void agregarRepeticionMensual(Evento e, int repeticiones) {

		var repetidorExistente = buscarRepetidorPorIdDeEvento(e.getId());

		if (repetidorExistente != null) {
			listaDeRepetidores.removeIf(rep -> rep.getElemento().getId() == e.getId());
		}

		var repetible = new RepetidorDeEventosMensual(e, repeticiones);
		this.listaDeRepetidores.add(repetible);

	}

	public void agregarRepeticionAnual(Evento e, int repeticiones) {

		var repetidorExistente = buscarRepetidorPorIdDeEvento(e.getId());

		if (repetidorExistente != null) {
			listaDeRepetidores.removeIf(rep -> rep.getElemento().getId() == e.getId());
		}

		var repetible = new RepetidorDeEventosAnual(e, repeticiones);
		this.listaDeRepetidores.add(repetible);

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

	enum EfectoEnum {
		NOTIFICACION, SONIDO, EMAIL
	}

}