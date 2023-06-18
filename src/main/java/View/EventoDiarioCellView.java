package View;

import Logic.EventoDiario;
import javafx.scene.control.Label;

public class EventoDiarioCellView extends ElementoCellView{

    private final Label inicio;
    private final Label tipo;
    private final Label fin;

    public EventoDiarioCellView(EventoDiario e) {
        super(e);
        tipo = new Label("Evento Diario");
        inicio = new Label("Inicio " + e.getFecha().toString());
        fin = new Label("Fin " + e.getFechaFinal().toString());

        this.getChildren().add(tipo);
        this.getChildren().add(inicio);
        this.getChildren().add(fin);

    }
}
