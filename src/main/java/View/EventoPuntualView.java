package View;

import Logic.EventoPuntual;
import javafx.scene.control.Label;

public class EventoPuntualView extends ElementoView {
    private final Label fin;
    private Label inicio;
    private Label tipo;

    public EventoPuntualView(EventoPuntual evento) {
        super(evento);
        tipo = new Label("Evento");
        inicio = new Label("Inicio: " + evento.getInicio().toString());
        fin = new Label("Fin " + evento.getFin().toString());

        this.getChildren().add(tipo);
        this.getChildren().add(inicio);
        this.getChildren().add(fin);
    }
}
