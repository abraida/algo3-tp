package Controller;

import Model.ElementoModel;
import View.ElementoPane;

public class ElementoPaneController {
    private final ElementoPane elementoPane;
    private final ElementoModel model;

    public ElementoPaneController(ElementoModel model, ElementoPane elementoPane) {
        this.elementoPane = elementoPane;
        this.model = model;
        initModel();
    }

    public void initModel() {
        model.getElementoActualProperty().addListener((obs, o, n) -> {
            if (n != null) {
                ElementoPaneCrearViewVisitor visitor = new ElementoPaneCrearViewVisitor();
                n.aceptar(visitor);
                visitor.poblarPane(elementoPane);
            }
        });
    }
}
