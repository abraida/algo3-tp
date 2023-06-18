package View;

import Logic.Elemento;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public abstract class ElementoView extends VBox {
    Elemento elemento;
    Label titulo;
    Label descripcion;

    public ElementoView(Elemento elemento) {
        this.elemento = elemento;
        this.titulo = new Label(elemento.getTitulo());
        this.descripcion = new Label(elemento.getDescripcion());
        descripcion.setWrapText(true);

        this.getChildren().add(titulo);
        this.getChildren().add(descripcion);
    }

}
