import java.time.LocalDateTime;


public interface Alarmable {

	long agregarAlarma(Alarma alarma);

	boolean eliminarAlarmaPorID(long alarmaID);

	Alarma obtenerAlarmaPorID(long alarmaID);
	LocalDateTime obtenerTiempoDeAlarma(Alarma alarma);

}