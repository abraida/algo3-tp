package View;

import Logic.TareaPuntual;
import javafx.scene.control.Label;

public class TareaPuntualView extends ElementoView {
    private final Label vencimiento;
    private final Label completada;
    private final Label tipo;


    public TareaPuntualView(TareaPuntual tarea) {
        super(tarea);
        tipo = new Label("Tarea");
        vencimiento = new Label("Vencimiento " + tarea.getVencimiento().toString());
        completada = new Label("Completada: " + ((tarea.getEstaCompletada()) ? "Si" : "No"));

        this.getChildren().add(tipo);
        this.getChildren().add(vencimiento);
        this.getChildren().add(completada);
    }
}
