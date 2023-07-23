package View;

import Logic.Elemento;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class RootView {

    ElementoMenuView menuView;
    HBox root;
    VBox mainPanel;
    ElementoPane elementoPane;
    ListView<Elemento> listView;

    public RootView(ElementoListView elementoListView, ElementoPane elementoPane, ElementoMenuView elementoMenuView) {
        this.listView = elementoListView;
        this.elementoPane = elementoPane;
        this.menuView = elementoMenuView;

        crearVista();
    }

    private void crearVista() {
        this.root = new HBox();
        this.mainPanel = new VBox();

        root.getChildren().add(elementoPane);
        root.getChildren().add(mainPanel);

        mainPanel.getChildren().add(listView);
        mainPanel.getChildren().add(menuView);

        HBox.setHgrow(mainPanel, Priority.ALWAYS);
        VBox.setVgrow(listView, Priority.ALWAYS);
    }

    public Parent asParent() {
        return this.root;
    }


}
