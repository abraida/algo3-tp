package Logic;

import java.time.LocalDateTime;
import java.util.ArrayList;


public interface Alarmable {

    long agregarAlarma(Alarma alarma);

    boolean eliminarAlarmaPorID(long alarmaID);

    Alarma obtenerAlarmaPorID(long alarmaID);

    LocalDateTime obtenerTiempoDeAlarma(Alarma alarma);

    ArrayList<Alarma> obtenerAlarmas();
}