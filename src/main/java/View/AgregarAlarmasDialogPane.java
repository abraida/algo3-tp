package View;

import Model.ElementoModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.converter.IntegerStringConverter;

public class AgregarAlarmasDialogPane extends DialogPane {
    GridPane root = new GridPane();
    AlarmaTreeView alarmaTreeView;

    TextField dias = new TextField();

    Spinner<Integer> horas = new Spinner<>();
    Spinner<Integer> minutos = new Spinner<>();

    private final Button buttonCrearAlarma = new Button("Agregar");

    public AgregarAlarmasDialogPane() {
        this.getButtonTypes().addAll(ButtonType.PREVIOUS, ButtonType.FINISH, ButtonType.CANCEL);
        this.setContent(root);
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(20, 150, 10, 10));
        root.setAlignment(Pos.CENTER);
        dias.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0, 1);
        horas.setValueFactory(valueFactory);
        horas.setEditable(true);
        horas.getEditor().setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));

        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0, 1);
        minutos.setValueFactory(valueFactory2);
        minutos.setEditable(true);
        minutos.getEditor().setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));


        root.add(new Label("Dias"), 1, 1);
        root.add(new Label("Horas"), 2, 1);
        root.add(new Label("Minutos"), 3, 1);

        root.add(new Label("Tiempo antes de la tarea:"), 0, 2);
        root.add(dias, 1, 2);
        root.add(horas, 2, 2);
        root.add(minutos, 3, 2);
        root.add(buttonCrearAlarma, 0, 3, 4, 1);
    }

    public void setAlarmaTreeView(AlarmaTreeView alarmasView) {
        this.alarmaTreeView = alarmasView;
        root.add(alarmasView, 0, 0, 4, 1);
    }

    public void registrarPreviousButtonAction(EventHandler<ActionEvent> e) {
        Button b = (Button) this.lookupButton(ButtonType.PREVIOUS);
        b.addEventFilter(ActionEvent.ACTION, e);
    }

    public void registrarFinishButtonAction(EventHandler<ActionEvent> e) {
        Button b = (Button) this.lookupButton(ButtonType.FINISH);
        b.addEventFilter(ActionEvent.ACTION, e);
    }

    public void registrarCrearAlarmaAction(EventHandler<ActionEvent> e) {
        buttonCrearAlarma.setOnAction(e);
    }

    public long getDias() {
        return Long.parseLong(this.dias.getText());
    }

    public int getMinutos() {
        return this.minutos.getValue();
    }

    public int getHoras() {
        return this.horas.getValue();
    }

}
