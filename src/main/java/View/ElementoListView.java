package View;

import Logic.Elemento;
import Model.ElementoModel;
import javafx.scene.control.ListView;

public class ElementoListView extends ListView<Elemento> {
    ElementoModel model;

    public ElementoListView(ElementoModel model) {
        this.model = model;
    }
}
