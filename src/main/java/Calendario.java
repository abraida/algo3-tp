import java.time.LocalDateTime;
import java.util.ArrayList;

public class Calendario {
    private final ArrayList<Tarea> tareasList;
    private final ArrayList<Evento> eventosList;

    public Calendario() {
        this.tareasList = new ArrayList<>();
        this.eventosList = new ArrayList<>();
    }

    public void agregarTarea(Tarea tarea) {
        this.tareasList.add(tarea);
    }

    public boolean quitarTarea(Tarea tarea) {
        return this.tareasList.remove(tarea);
    }


    public ArrayList<Tarea> obtenerTareasPorTitulo(String titulo) {

        var tareas = this.tareasList.stream()
          .filter(tarea -> titulo.equals(tarea.getTitulo()))
          .toList();

        return new ArrayList<>(tareas);

    }

    public ArrayList<Tarea> obtenerProximasTareas(int cantidad, LocalDateTime tiempo) {
        if (cantidad <= 0)
            return new ArrayList<Tarea>();

        var tareas = this.tareasList.stream()
                .filter(tarea -> tarea.esPosteriorA(tiempo) || tarea.esSimultaneoA(tiempo))
                .sorted()
                .toList();

        if (tareas.size() <= 0)
            return new ArrayList<Tarea>();


        tareas = tareas.subList(0, Math.min(cantidad, tareas.size()));

        return new ArrayList<>(tareas);
    }

    public ArrayList<Tarea> obtenerTareasEnIntervalo(LocalDateTime inicio, LocalDateTime fin) {
        var tareas = this.tareasList.stream()
                .filter(tarea -> tarea.esPosteriorA(inicio) & tarea.esAnteriorA(fin))
                .sorted()
                .toList();

        return new ArrayList<>(tareas);
    }
}
