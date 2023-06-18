package View;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class ElementoPane extends ScrollPane {
    ElementoView elementoView;
    VBox root;
    AlarmaTreeView alarmasView;

    public ElementoPane() {
        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.setFitToWidth(true);
        this.setPrefWidth(500);
        this.setPadding(new Insets(5, 20, 5, 20));
        this.root = new VBox();
        this.setContent(root);
    }

    public void setElementoView(ElementoView elementoView) {
        this.root.getChildren().remove(this.elementoView);
        this.elementoView = elementoView;
        this.root.getChildren().add(this.elementoView);
    }

    public void setAlarmasView(AlarmaTreeView alarmasView) {
        this.root.getChildren().remove(this.alarmasView);
        this.alarmasView = alarmasView;
        this.root.getChildren().add(this.alarmasView);
    }
}
