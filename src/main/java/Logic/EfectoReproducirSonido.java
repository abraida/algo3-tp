package Logic;

import Controller.EfectoVisitor;

public class EfectoReproducirSonido implements Efecto {
    @Override

    public EfectoEnum realizar() {
        return EfectoEnum.SONIDO;
    }

    @Override
    public void recibir(EfectoVisitor v) {
        v.visitarEfectoSonido(this);
    }
}