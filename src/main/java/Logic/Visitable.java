package Logic;

import Controller.ElementoVisitor;

public interface Visitable {
    void aceptar(ElementoVisitor visitor);
}
