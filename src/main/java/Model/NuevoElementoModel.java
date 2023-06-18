package Model;

import Logic.AlarmaRelativa;
import Logic.Calendario;
import Logic.EfectoNotificacion;
import Logic.Elemento;

import java.time.Duration;

public abstract class NuevoElementoModel {

    protected Elemento elemento;
    protected Calendario calendario;

    public NuevoElementoModel(Calendario calendario) {
        this.calendario = calendario;
    }

    public Elemento getElemento() {
        return elemento;
    }

    public void crearAlarma(long dias, int horas, int minutos) {
        var antelacion = Duration.ofDays(dias).plusHours(horas).plusMinutes(minutos);
        AlarmaRelativa a = new AlarmaRelativa(antelacion, new EfectoNotificacion());
        elemento.agregarAlarma(a);
    }

    public abstract void mandarACalendario();

}
