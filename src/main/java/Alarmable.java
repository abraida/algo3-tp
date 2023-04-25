import java.time.LocalDateTime;


public interface Alarmable {

	void agregarAlarma(Alarma alarma);

	boolean eliminarAlarma(Alarma alarma);

	LocalDateTime obtenerTiempoDeAlarma(Alarma alarma);

}