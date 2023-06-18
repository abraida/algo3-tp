package Controller;

import Logic.*;
import javafx.scene.control.Alert;

public class RealizadorDeAlarmas implements EfectoVisitor{

    @Override
    public void visitarEfectoSinEfecto(EfectoSinEfecto e) {

    }

    @Override
    public void visitarEfectoNotificacion(EfectoNotificacion e) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Notificacion");

		alert.setHeaderText(null);
		alert.setContentText("Alarma");

		alert.show();
    }

    @Override
    public void visitarEfectoMail(EfectoEnviarMail e) {

    }

    @Override
    public void visitarEfectoSonido(EfectoReproducirSonido e) {

    }
}