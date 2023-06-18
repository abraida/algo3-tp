package Controller;

import Logic.Elemento;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class ElementoCellFactory implements Callback<ListView<Elemento>, ListCell<Elemento>> {
    ElementoCellCreatorVisitor visitor;

    ElementoCellFactory(ElementoCellCreatorVisitor visitor) {
        this.visitor = visitor;
    }

    @Override
    public ListCell<Elemento> call(ListView<Elemento> param) {
        return new ListCell<>() {
            @Override
            public void updateItem(Elemento person, boolean empty) {
                super.updateItem(person, empty);
                ElementoCellCreatorVisitor visitor = new ElementoCellCreatorVisitor();
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else if (person != null) {
                    person.aceptar(visitor);
                    setText(null);
                    setGraphic(visitor.crearVista());
                } else {
                    setText("null");
                    setGraphic(null);
                }
            }
        };
    }
}