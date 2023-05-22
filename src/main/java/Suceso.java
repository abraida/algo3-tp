import java.time.LocalDateTime;


public interface Suceso extends Comparable<Suceso> {
	boolean esAnteriorA(LocalDateTime tiempo);

	boolean esSimultaneoA(LocalDateTime tiempo);
	boolean esPosteriorA(LocalDateTime tiempo);
}