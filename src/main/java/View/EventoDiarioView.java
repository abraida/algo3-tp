package View;

import Logic.EventoDiario;
import javafx.scene.control.Label;

public class EventoDiarioView extends ElementoView {
    private Label inicio;
    private Label tipo;
    private final Label fin;

    public EventoDiarioView(EventoDiario evento) {
        super(evento);
        tipo = new Label("Evento Diario");
        inicio = new Label("Inicio: " + evento.getFecha().toString());
        fin = new Label("Fin " + evento.getFechaFinal().toString());

        this.getChildren().add(tipo);
        this.getChildren().add(inicio);
        this.getChildren().add(fin);
    }
}
