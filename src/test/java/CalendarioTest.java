import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CalendarioTest {

    private Calendario calendario;
    private long tareasTotales;
    private final LocalDate CUATRODENOVIEMBRE = LocalDate.parse("2023-11-04");

    @Before
    public void setUp() throws Exception {
        this.calendario = new Calendario();

        var t = new TareaDiaria(CUATRODENOVIEMBRE);
        t.setTitulo("Tarea Diaria 2023-11-04");
        calendario.agregarTarea(t);

        t = new TareaDiaria(CUATRODENOVIEMBRE.plusDays(1));
        t.setTitulo("Tarea Diaria 2023-11-05");
        calendario.agregarTarea(t);

        t = new TareaDiaria(CUATRODENOVIEMBRE.plusDays(2));
        t.setTitulo("Tarea Diaria 2023-11-06");
        calendario.agregarTarea(t);

        t = new TareaDiaria(CUATRODENOVIEMBRE.plusDays(4));
        t.setTitulo("Tarea Diaria 2023-11-07");
        calendario.agregarTarea(t);

        var y = new TareaPuntual(CUATRODENOVIEMBRE.atStartOfDay());
        y.setTitulo("Tarea Puntual 2023-11-04 00:00");
        calendario.agregarTarea(y);

        y = new TareaPuntual(CUATRODENOVIEMBRE.atStartOfDay().plusMinutes(30));
        y.setTitulo("Tarea Puntual 2023-11-04 00:30");
        calendario.agregarTarea(y);

        y = new TareaPuntual(CUATRODENOVIEMBRE.plusDays(1).atStartOfDay().plusMinutes(60));
        y.setTitulo("Tarea Puntual 2023-11-05 01:00");
        calendario.agregarTarea(y);

        y = new TareaPuntual(CUATRODENOVIEMBRE.plusDays(1).atStartOfDay().plusMinutes(30));
        y.setTitulo("Tarea Puntual 2023-11-05 00:30");
        calendario.agregarTarea(y);

        tareasTotales = 9;

    }

    @Test
    public void quitarTareaLaEliminaCorrectamente() {
        var tareas = calendario.obtenerTareasPorTitulo("Tarea Diaria 2023-11-04");

        boolean condicion = calendario.quitarTarea(tareas.get(0));

        assertTrue(condicion);
        assertTrue(calendario.obtenerTareasPorTitulo("Tarea Diaria 2023-11-04").isEmpty());
    }

    @Test
    public void quitarTareaNoEliminaTareasIdenticas() {
        var t = new TareaDiaria(LocalDate.ofEpochDay(1));
        t.setTitulo("Tarea Repetida");
        calendario.agregarTarea(t);

        t = new TareaDiaria(LocalDate.ofEpochDay(1));
        t.setTitulo("Tarea Repetida");
        calendario.agregarTarea(t);

        var tareas = calendario.obtenerTareasPorTitulo("Tarea Repetida");

        calendario.quitarTarea(tareas.get(0));

        assertEquals(1, calendario.obtenerTareasPorTitulo("Tarea Diaria 2023-11-04").size());
    }

    @Test
    public void quitarTareaFueraDelCalendarioNoHaceNada() {
        var t = new TareaDiaria(LocalDate.ofEpochDay(1));
        t.setTitulo("Tarea Sin Agregar");

        boolean condicion = calendario.quitarTarea(t);

        assertFalse(condicion);
    }

    @Test
    public void obtenerProximasTareasDevuelveListaVaciaSiNoHay() {
        var tareas = calendario.obtenerProximasTareas(10, LocalDateTime.MAX);

        int size = tareas.size();

        assertEquals(0, size);
    }

    @Test
    public void obtenerProximasTareasDevuelveListaVaciaSiSePidenCero() {
        var tareas = calendario.obtenerProximasTareas(0, LocalDateTime.MIN);

        int size = tareas.size();

        assertEquals(0, size);
    }

    @Test
    public void obtenerProximasTareasDevuelveTodasSiSePidenMasDeLasQueHay() {
        var tareas = calendario.obtenerProximasTareas(200,
                CUATRODENOVIEMBRE.plusDays(1).atTime(0, 45));

        var t1 = calendario.obtenerTareasPorTitulo("Tarea Diaria 2023-11-05").get(0);
        var t2 = calendario.obtenerTareasPorTitulo("Tarea Diaria 2023-11-06").get(0);
        var t3 = calendario.obtenerTareasPorTitulo("Tarea Diaria 2023-11-07").get(0);
        var t4 = calendario.obtenerTareasPorTitulo("Tarea Puntual 2023-11-05 01:00").get(0);
        var titulos = new ArrayList<>(
                List.of(t1, t2, t3, t4)
        );

        int size = tareas.size();
        boolean con1 = tareas.containsAll(titulos);

        assertEquals(4, size);
        assertTrue(con1);
    }

    @Test
    public void obtenerProximasTareasDevuelveLasPrimeras() {
        var tareas = calendario.obtenerProximasTareas(2,
                CUATRODENOVIEMBRE.plusDays(1).atTime(0, 45));

        var t1 = calendario.obtenerTareasPorTitulo("Tarea Diaria 2023-11-05").get(0);
        var t2 = calendario.obtenerTareasPorTitulo("Tarea Puntual 2023-11-05 01:00").get(0);
        var titulos = new ArrayList<>(
                List.of(t1, t2)
        );

        int size = tareas.size();
        boolean con1 = tareas.containsAll(titulos);

        assertEquals(2, size);
        assertTrue(con1);
    }
}