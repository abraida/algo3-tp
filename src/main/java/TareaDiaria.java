import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TareaDiaria extends Tarea{

    private LocalDate fecha;

    public TareaDiaria(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public TareaPuntual toTareaPuntal(LocalDateTime vencimiento) {
        var tareaPuntual = new TareaPuntual(vencimiento);
        tareaPuntual.setTitulo(this.getTitulo());
        tareaPuntual.setDescripcion(this.getDescripcion());
        tareaPuntual.setEstado(this.getEstado());

        return tareaPuntual;
    }

    @Override
    public boolean  esAnteriorA(LocalDateTime tiempo) {
        return this.fecha.isBefore(tiempo.toLocalDate());
    }

    @Override
    public boolean  esSimultaneoA(LocalDateTime tiempo) {
        return this.fecha.isEqual(tiempo.toLocalDate());
    }

    @Override
    public boolean  esPosteriorA(LocalDateTime tiempo) {
        return this.fecha.isAfter(tiempo.toLocalDate());
    }


    @Override
    public int compareTo(Suceso otroSuceso) {
        if (otroSuceso.esPosteriorA(this.fecha.atTime(LocalTime.MAX))){
            return -1;
        }

        return otroSuceso.esAnteriorA(this.fecha.atStartOfDay()) ? 1 : 0;
    }
}
