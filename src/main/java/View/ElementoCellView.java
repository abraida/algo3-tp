package View;

import Logic.Elemento;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public abstract class ElementoCellView extends VBox {
    private Elemento elemento;
    Label titulo;

    public ElementoCellView(Elemento elemento) {
        this.elemento = elemento;
        titulo =  new Label(this.elemento.getTitulo());
        this.getChildren().add(titulo);
    }

}
