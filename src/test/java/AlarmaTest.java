import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.Assert.*;


public class AlarmaTest {
	final Efecto mail = new EfectoEnviarMail();
	Efecto sonido = new EfectoReproducirSonido();
	Efecto notificacion = new EfectoNotificacion();

	Efecto sinEfecto = new EfectoSinEfecto();

	@Test

	public void alarmaAbsolutaSuenaSiempreALaHoraMarcadaParaCualquierHora() {
		var tiempo = LocalDateTime.MIN;
		var alarma = new AlarmaAbsoluta(tiempo, mail);

		var t = alarma.getTiempo(LocalDateTime.now());

		assertEquals(tiempo, t);
	}

	@Test

	public void alarmaRelativaSuenaALaHoraMarcadaSiTiempoRelativoEsCero() {
		var tiempo = Duration.ZERO;
		var alarma = new AlarmaRelativa(tiempo, mail);
		var tiempoMarcado = LocalDateTime.now();
		var t = alarma.getTiempo(tiempoMarcado);

		assertEquals(tiempoMarcado, t);
	}

	@Test

	public void alarmaRelativaSuenaEnElMinimoPosibleSiHaceFalta() {
		var tiempo = Duration.ofHours(2);
		var alarma = new AlarmaRelativa(tiempo, mail);
		var tiempoMarcado = LocalDateTime.MIN;
		var t = alarma.getTiempo(tiempoMarcado);

		assertEquals(tiempoMarcado, t);
	}

	@Test

	public void alarmaRelativaSuenaALaHoraCorrecta() {
		var tiempo = Duration.ofHours(2);
		var alarma = new AlarmaRelativa(tiempo, mail);
		var tiempoMarcado = LocalDateTime.now();

		var t = alarma.getTiempo(tiempoMarcado);

		assertEquals(tiempoMarcado.minus(tiempo), t);
	}
}