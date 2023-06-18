package Logic;

import Controller.EfectoVisitor;

public class EfectoNotificacion implements Efecto {
	@Override
	public EfectoEnum realizar() {
		return EfectoEnum.NOTIFICACION;
	}

	@Override
	public void recibir(EfectoVisitor v){
		v.visitarEfectoNotificacion(this);
	}
}