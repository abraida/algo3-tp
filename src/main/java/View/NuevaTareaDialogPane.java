package View;

import javafx.scene.control.*;
import javafx.util.converter.IntegerStringConverter;

import java.time.LocalDate;

public class NuevaTareaDialogPane extends NuevoElementoDialogPane {

    DatePicker fecha = new DatePicker();
    Spinner<Integer> horaTareaPuntual = new Spinner<>();
    Spinner<Integer> minutoTareaPuntual = new Spinner<>();
    Label labelVencimiento;
    public NuevaTareaDialogPane() {
        super();
        crearPanel();
    }

    private void crearPanel() {
        fecha.setPromptText("Fecha");
        titulo.setPromptText("Titulo");
        descripcion.setPromptText("Descripcion");

        labelVencimiento = new Label("Vencimiento: ");

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0, 1);
        horaTareaPuntual.setValueFactory(valueFactory);
        horaTareaPuntual.setEditable(true);
        horaTareaPuntual.getEditor().setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        horaTareaPuntual.getEditor().setPromptText("horas");

        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0, 1);
        minutoTareaPuntual.setValueFactory(valueFactory2);
        minutoTareaPuntual.setEditable(true);
        minutoTareaPuntual.getEditor().setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        minutoTareaPuntual.getEditor().setPromptText("minutos");

        root.add(new Label("Titulo:"), 0, 0);
        root.add(titulo, 1, 0, 2, 1);
        root.add(descripcion, 0, 1,3, 1);
        root.add(new Label("Fecha:"), 0, 2);
        root.add(fecha, 1, 2, 2, 1);
        root.add(labelVencimiento, 0, 3);
        root.add(horaTareaPuntual, 1, 3);
        root.add(minutoTareaPuntual, 2, 3);
        root.add(esDiaCompleto,0, 4, 3, 1);

    }
    public void ocultarHora() {
        this.root.getChildren().remove(this.horaTareaPuntual);
        this.root.getChildren().remove(this.minutoTareaPuntual);
        this.root.getChildren().remove(this.labelVencimiento);
    }

    public void mostrarHora() {
        this.root.add(this.horaTareaPuntual, 1, 3);
        this.root.add(this.minutoTareaPuntual, 2, 3);
        this.root.add(this.labelVencimiento, 0, 3);

    }

    public LocalDate getFecha() {
        return fecha.getValue();
    }

    public int getHora() {
        return horaTareaPuntual.getValue();
    }

    public int getMinutos() {
        return minutoTareaPuntual.getValue();
    }

    public boolean estanCamposObligatoriosDiaCompleto() {
        return fecha.getValue() != null;
    }

    public boolean estanCamposObligatoriosPuntual() {
        return fecha.getValue() != null &&
                horaTareaPuntual.getValue() != null &&
                minutoTareaPuntual.getValue() != null;
    }
}
