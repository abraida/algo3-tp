package Logic;

import Controller.EfectoVisitor;

public class EfectoSinEfecto implements Efecto {
    @Override

    public EfectoEnum realizar() {
        return null;
    }

    @Override
    public void recibir(EfectoVisitor v) {
        v.visitarEfectoSinEfecto(this);
    }
}