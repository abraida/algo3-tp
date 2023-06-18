package Logic;

import Controller.EfectoVisitor;

public class EfectoEnviarMail implements Efecto {

	@Override

	public EfectoEnum realizar() {
		return EfectoEnum.EMAIL;
	}

	@Override
	public void recibir(EfectoVisitor v) {
		v.visitarEfectoMail(this);
	}
}