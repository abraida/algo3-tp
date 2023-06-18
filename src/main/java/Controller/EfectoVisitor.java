package Controller;

import Logic.EfectoEnviarMail;
import Logic.EfectoNotificacion;
import Logic.EfectoReproducirSonido;
import Logic.EfectoSinEfecto;

public interface EfectoVisitor {
    void visitarEfectoSinEfecto(EfectoSinEfecto e);

    void visitarEfectoNotificacion(EfectoNotificacion e);

    void visitarEfectoMail(EfectoEnviarMail e);

    void visitarEfectoSonido(EfectoReproducirSonido e);
}
