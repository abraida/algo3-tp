package View;

import Logic.EventoDiario;
import javafx.scene.control.Label;

public class EventoDiarioView extends ElementoView {
    private final Label fin;
    private final Label inicio;
    private final Label tipo;

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
