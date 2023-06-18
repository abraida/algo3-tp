package View;

import Logic.TareaDiaria;
import javafx.scene.control.Label;

public class TareaDiariaView extends ElementoView {
    private Label fecha;
    private Label completada;

    private Label tipo;
    public TareaDiariaView(TareaDiaria tarea) {
        super(tarea);
        tipo = new Label("Tarea Diaria");
        fecha = new Label("Vencimiento " + tarea.getFecha().toString());
        completada = new Label("Completada: " + ((tarea.getEstaCompletada()) ? "Si" : "No"));

        this.getChildren().add(tipo);
        this.getChildren().add(fecha);
        this.getChildren().add(completada);
    }
}
