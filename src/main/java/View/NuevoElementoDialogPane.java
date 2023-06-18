package View;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public abstract class NuevoElementoDialogPane extends DialogPane {
    TextField titulo = new TextField();
    TextArea descripcion = new TextArea();
    CheckBox esDiaCompleto = new CheckBox("Dia completo");
    GridPane root = new GridPane();
    public NuevoElementoDialogPane() {
        this.getButtonTypes().addAll(ButtonType.NEXT, ButtonType.CANCEL);

        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(20, 150, 10, 10));
        root.setAlignment(Pos.CENTER);

        this.setContent(root);
    }

    public void registrarEsDiaCompletoListener(ChangeListener<Boolean> listener) {
        this.esDiaCompleto.selectedProperty().addListener(listener);
    }

    public String getTitulo() {
        return titulo.getText();
    }

    public String getDescripcion() {
        return descripcion.getText();
    }

    public void registrarNextButtonAccion(EventHandler<ActionEvent> e) {
        Button b = (Button) this.lookupButton(ButtonType.NEXT);
        b.addEventFilter(ActionEvent.ACTION, e);
    }

    public abstract boolean estanCamposObligatoriosDiaCompleto();

    public abstract boolean estanCamposObligatoriosPuntual();
    public abstract void mostrarHora();
    public abstract void ocultarHora();

}
