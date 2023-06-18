package View;

import Logic.TareaDiaria;
import javafx.scene.control.Label;

public class TareaDiariaCellView extends ElementoCellView{

    private final Label fecha;
    private final Label tipo;

    private final Label completada;

    public TareaDiariaCellView(TareaDiaria tarea) {
        super(tarea);
        tipo = new Label("Tarea Diaria");
        fecha = new Label("Fecha " + tarea.getFecha().toString());
        completada = new Label("Completada: " + ((tarea.getEstaCompletada()) ? "Si" : "No"));

        this.getChildren().add(tipo);
        this.getChildren().add(fecha);
        this.getChildren().add(completada);
    }
}
