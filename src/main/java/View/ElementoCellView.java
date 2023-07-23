package View;

import Logic.Elemento;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public abstract class ElementoCellView extends VBox {
    Label titulo;

    public ElementoCellView(Elemento elemento) {
        titulo = new Label(elemento.getTitulo());
        this.getChildren().add(titulo);
    }

}
