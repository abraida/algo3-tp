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
		var alarma = new AlarmaAbsoluta(0, tiempo, mail);

		var t = alarma.getTiempo(LocalDateTime.MAX);

		assertEquals(tiempo, t);
	}

	@Test

	public void alarmaRelativaSuenaALaHoraMarcadaSiTiempoRelativoEsCero() {
		var tiempo = Duration.ZERO;
		var alarma = new AlarmaRelativa(0, tiempo, mail);
		var tiempoMarcado = LocalDateTime.MAX;
		var t = alarma.getTiempo(tiempoMarcado);

		assertEquals(tiempoMarcado, t);
	}

	@Test

	public void alarmaRelativaSuenaEnElMinimoPosibleSiHaceFalta() {
		var tiempo = Duration.ofHours(2);
		var alarma = new AlarmaRelativa(0, tiempo, mail);
		var tiempoMarcado = LocalDateTime.MIN;
		var t = alarma.getTiempo(tiempoMarcado);

		assertEquals(tiempoMarcado, t);
	}

	@Test

	public void alarmaRelativaSuenaALaHoraCorrecta() {
		var tiempo = Duration.ofHours(2);
		var alarma = new AlarmaRelativa(0, tiempo, mail);
		var tiempoMarcado = LocalDateTime.MAX;

		var t = alarma.getTiempo(tiempoMarcado);

		assertEquals(tiempoMarcado.minus(tiempo), t);
	}

	@Test

	public void alarmaRealizaElEfectoDeseado(){
		var tiempo = Duration.ofHours(2);
		var alarma = new AlarmaRelativa(0, tiempo, mail);
		var enumEfecto = alarma.disparar();
		assertEquals(EfectoEnum.EMAIL, enumEfecto);
	}
}