package View;

import Logic.TareaPuntual;
import javafx.scene.control.Label;

public class TareaPuntualCellView extends ElementoCellView{

    private final Label vencimiento;
    private final Label tipo;
    private final Label completada;

    public TareaPuntualCellView(TareaPuntual tarea) {
        super(tarea);
        tipo = new Label("Tarea");
        vencimiento = new Label("Vencimiento " + tarea.getVencimiento().toString());
        completada = new Label("Completada: " + ((tarea.getEstaCompletada()) ? "Si" : "No"));

        this.getChildren().add(tipo);
        this.getChildren().add(vencimiento);
        this.getChildren().add(completada);
    }
}
