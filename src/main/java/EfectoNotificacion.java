public class EfectoNotificacion implements Efecto {
	@Override

	public EfectoEnum realizar() {
		return EfectoEnum.NOTIFICACION;
	}
}