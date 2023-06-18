package View;

import Logic.EventoPuntual;
import javafx.scene.control.Label;

public class EventoPuntualCellView extends ElementoCellView{

    private final Label inicio;
    private final Label tipo;
    private final Label fin;

    public EventoPuntualCellView(EventoPuntual e) {
        super(e);
        tipo = new Label("Evento");
        inicio = new Label("Inicio " + e.getInicio().toString());
        fin = new Label("Fin " + e.getFin().toString());

        this.getChildren().add(tipo);
        this.getChildren().add(inicio);
        this.getChildren().add(fin);

    }
}
