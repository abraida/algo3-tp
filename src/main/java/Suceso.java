import java.time.LocalDateTime;

public interface Suceso extends Comparable<Suceso> {
    public boolean  esAnteriorA(LocalDateTime tiempo);

    public boolean  esSimultaneoA(LocalDateTime tiempo);
    public boolean  esPosteriorA(LocalDateTime tiempo);
}
