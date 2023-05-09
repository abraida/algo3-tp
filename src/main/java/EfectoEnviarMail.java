public class EfectoEnviarMail implements Efecto {

	@Override

	public EfectoEnum realizar() {
		return EfectoEnum.EMAIL;
	}
}