import java.time.LocalDateTime;


public class TareaPuntual extends Tarea {

	private LocalDateTime vencimiento;

	public TareaPuntual(long id, LocalDateTime vencimiento) {
		super(id);
		this.vencimiento = vencimiento;
	}

	public void setVencimiento(LocalDateTime vencimiento) {
		this.vencimiento = vencimiento;
	}

	public LocalDateTime getVencimiento() {
		return this.vencimiento;
	}

	@Override

	public boolean esAnteriorA(LocalDateTime tiempo) {
		return vencimiento.isBefore(tiempo);
	}

	@Override

	public boolean esSimultaneoA(LocalDateTime tiempo) {
		return false;
	}

	@Override

	public boolean esPosteriorA(LocalDateTime tiempo) {
		return vencimiento.isAfter(tiempo);
	}

	@Override

	public int compareTo(Suceso otroSuceso) {
		if (otroSuceso.esPosteriorA(vencimiento)) {
			return -1;
		}

		return (otroSuceso.esAnteriorA(vencimiento)) ? 1 : 0;

	}

	@Override

	public LocalDateTime obtenerTiempoDeAlarma(Alarma alarma) {
		return alarma.getTiempo(this.vencimiento);
	}
}