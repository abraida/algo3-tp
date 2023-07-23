package Model;

import Controller.RealizadorDeAlarmas;
import Logic.Alarma;
import Logic.Alarmable;
import Logic.Calendario;
import Logic.Elemento;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class NotificacionModel {
    private final Calendario calendario;
    private LocalDateTime ahora;
    private ArrayList<Elemento> elementos;

    public NotificacionModel(Calendario calendario) {
        this.calendario = calendario;
        ahora = LocalDateTime.now();
        elementos = calendario.obtenerProximosElementos(1000, ahora);
    }

    public void sonarAlarma(RealizadorDeAlarmas realizador) {
        ahora = LocalDateTime.now();
        elementos = calendario.obtenerProximosElementos(1000, ahora);

        for (Alarmable e : elementos) {
            var alarmas = e.obtenerAlarmas();
            for (Alarma a : alarmas) {
                if (e.obtenerTiempoDeAlarma(a).truncatedTo(ChronoUnit.MINUTES).
                        isEqual(ahora.truncatedTo(ChronoUnit.MINUTES))) {
                    a.recibir(realizador);
                }
            }
        }
    }
}
