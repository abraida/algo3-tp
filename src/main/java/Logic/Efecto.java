package Logic;

import Controller.EfectoVisitor;

public interface Efecto {
    EfectoEnum realizar();

    void recibir(EfectoVisitor v);
}
