package Model;

import Logic.CreadorDeAlarmas;
import Logic.EfectoEnum;
import Logic.TareaDiaria;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class ElementoTest {
    @Test

    public void modificarTituloConCadenaVacia() {
        var fecha = LocalDate.MAX;
        var tarea = new TareaDiaria("", "", fecha);

        tarea.setTitulo("");
        var titulo = tarea.getTitulo();

        assertEquals("", titulo);
    }

    @Test

    public void modificarTituloFuncionaCorrectamente() {
        var fecha = LocalDate.MAX;
        var tarea = new TareaDiaria("", "", fecha);

        tarea.setTitulo("Nombre B1");
        var titulo = tarea.getTitulo();

        assertEquals("Nombre B1", titulo);
    }

    @Test

    public void modificarDescripcionConCadenaVacia() {
        var fecha = LocalDate.MAX;
        var tarea = new TareaDiaria("", "", fecha);

        tarea.setDescripcion("");
        var descripcion = tarea.getDescripcion();

        assertEquals("", descripcion);
    }

    @Test

    public void modificarDescripcionFuncionaCorrectamente() {
        var fecha = LocalDate.MAX;
        var tarea = new TareaDiaria("", "", fecha);

        tarea.setDescripcion("aaaaaaaaaaaaaa \n\n\n 000000000000000");
        var descripcion = tarea.getDescripcion();

        assertEquals("aaaaaaaaaaaaaa \n\n\n 000000000000000", descripcion);
    }

    @Test

    public void modificarEstadoFuncionaCorrectamente() {
        var fecha = LocalDate.MAX;
        var tarea = new TareaDiaria("", "", fecha);

        tarea.setEstaCompletada(true);
        var estado = tarea.getEstaCompletada();

        assertEquals(true, estado);
    }

    @Test

    public void puedoAgregarVariasAlarmasAElemento() {
        var fecha = LocalDate.MAX;
        var creador = new CreadorDeAlarmas();
        var tarea = new TareaDiaria("", "", fecha);

        var fechaAlarma = LocalDateTime.MIN;
        var id1 = tarea.agregarAlarma(creador.crearAlarmaAbsoluta(fechaAlarma, EfectoEnum.EMAIL));
        var id2 = tarea.agregarAlarma(creador.crearAlarmaAbsoluta(fechaAlarma, EfectoEnum.SONIDO));
        var id3 = tarea.agregarAlarma(creador.crearAlarmaAbsoluta(fechaAlarma, EfectoEnum.NOTIFICACION));


        assertEquals(EfectoEnum.EMAIL, tarea.obtenerAlarmaPorID(id1).disparar());
        assertEquals(EfectoEnum.SONIDO, tarea.obtenerAlarmaPorID(id2).disparar());
        assertEquals(EfectoEnum.NOTIFICACION, tarea.obtenerAlarmaPorID(id3).disparar());
    }
}