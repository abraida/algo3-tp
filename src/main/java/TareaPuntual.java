import java.time.LocalDate;
import java.time.LocalDateTime;

public class TareaPuntual extends Tarea {
    private final LocalDateTime vencimiento;

    public TareaPuntual(LocalDateTime vencimiento) {
        this.vencimiento = vencimiento;
    }

    public TareaDiaria toTareaPuntal(LocalDate fecha) {
        var tareaDiaria = new TareaDiaria(fecha);
        tareaDiaria.setTitulo(this.getTitulo());
        tareaDiaria.setDescripcion(this.getDescripcion());
        tareaDiaria.setEstado(this.getEstado());

        return tareaDiaria;
    }
    public TareaDiaria toTareaPuntal() {
        var tareaDiaria = new TareaDiaria(vencimiento.toLocalDate());
        tareaDiaria.setTitulo(this.getTitulo());
        tareaDiaria.setDescripcion(this.getDescripcion());
        tareaDiaria.setEstado(this.getEstado());

        return tareaDiaria;
    }

    @Override
    public boolean  esAnteriorA(LocalDateTime tiempo) {
        return vencimiento.isBefore(tiempo);
    }

    @Override
    public boolean  esSimultaneoA(LocalDateTime tiempo) {
        return false;
    }

    @Override
    public boolean  esPosteriorA(LocalDateTime tiempo) {
        return vencimiento.isAfter(tiempo);
    }

    @Override
    public int compareTo(Suceso otroSuceso) {
        if (otroSuceso.esPosteriorA(vencimiento)) {
            return -1;
        }

        return (otroSuceso.esAnteriorA(vencimiento)) ? 1 : 0;

    }
}
