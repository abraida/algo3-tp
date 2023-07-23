package Controller;

import Logic.Elemento;
import Model.ElementoModel;
import javafx.scene.control.ListView;

public class ElementoListController {
    private final ListView<Elemento> listView;

    private final ElementoModel model;
    private final ElementoCellFactory elementoCellFactory;
    private final ElementoCellCreatorVisitor elementoCellCreatorVisitor;

    public ElementoListController(ElementoModel model, ListView<Elemento> listView) {
        this.listView = listView;
        this.model = model;
        this.elementoCellCreatorVisitor = new ElementoCellCreatorVisitor();
        this.elementoCellFactory = new ElementoCellFactory(elementoCellCreatorVisitor);

        initView();
        initModel();
    }

    public void initView() {
        listView.setItems(model.getListaDeElementos());
        listView.setCellFactory(elementoCellFactory);

        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->
                model.setElementoActual(newSelection));
    }

    public void initModel() {
        model.getElementoActualProperty().addListener((obs, oldPerson, newPerson) -> {
            if (newPerson == null) {
                listView.getSelectionModel().clearSelection();
            } else {
                listView.getSelectionModel().select(newPerson);
            }
        });
    }
}
