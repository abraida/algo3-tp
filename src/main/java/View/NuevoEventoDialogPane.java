package View;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.*;
import javafx.util.converter.IntegerStringConverter;

import java.time.LocalDate;

public class NuevoEventoDialogPane extends NuevoElementoDialogPane {
    DatePicker fechaInicio = new DatePicker();
    DatePicker fechaFin = new DatePicker();

    Spinner<Integer> horaInicio = new Spinner<>();
    Spinner<Integer> horaFin = new Spinner<>();

    Spinner<Integer> minutoInicio = new Spinner<>();
    Spinner<Integer> minutoFin = new Spinner<>();

    CheckBox conRepeticion = new CheckBox("Repetible");

    TextField dias = new TextField();

    public NuevoEventoDialogPane() {
        super();
        crearPanel();
    }

    private void crearPanel() {
        titulo.setPromptText("Titulo");
        descripcion.setPromptText("Descripcion");

        fechaInicio.setPromptText("Fecha");

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0, 1);
        horaInicio.setValueFactory(valueFactory);
        horaInicio.setEditable(true);
        horaInicio.getEditor().setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        horaInicio.getEditor().setPromptText("horas");

        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0, 1);
        minutoInicio.setValueFactory(valueFactory2);
        minutoInicio.setEditable(true);
        minutoInicio.getEditor().setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        minutoInicio.getEditor().setPromptText("minutos");

        fechaFin.setPromptText("Fecha");

        SpinnerValueFactory<Integer> valueFactory3 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0, 1);
        horaFin.setValueFactory(valueFactory3);
        horaFin.setEditable(true);
        horaFin.getEditor().setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        horaFin.getEditor().setPromptText("horas");

        SpinnerValueFactory<Integer> valueFactory4 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0, 1);
        minutoFin.setValueFactory(valueFactory4);
        minutoFin.setEditable(true);
        minutoFin.getEditor().setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        minutoFin.getEditor().setPromptText("minutos");

        dias.setPromptText("Frecuencia");
        dias.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));

        root.add(new Label("Titulo:"), 0, 0);
        root.add(titulo, 1, 0, 2, 1);
        root.add(descripcion, 0, 1, 3, 1);
        root.add(new Label("Inicio:"), 0, 2);
        root.add(fechaInicio, 1, 2, 1, 1);
        root.add(horaInicio, 2, 2);
        root.add(minutoInicio, 3, 2);
        root.add(new Label("Fin:"), 0, 3);
        root.add(fechaFin, 1, 3);
        root.add(horaFin, 2, 3);
        root.add(minutoFin, 3, 3);
        root.add(esDiaCompleto, 0, 4);
        root.add(conRepeticion, 1, 4);
        root.add(dias, 2, 4);

    }

    public LocalDate getFechaInicio() {
        return fechaInicio.getValue();
    }

    public int getHoraInicio() {
        return horaInicio.getValue();
    }

    public int getMinutosInicio() {
        return minutoInicio.getValue();
    }

    public LocalDate getFechaFin() {
        return fechaFin.getValue();
    }

    public int getHoraFin() {
        return horaFin.getValue();
    }

    public int getMinutosFin() {
        return minutoFin.getValue();
    }

    public void registrarConRepeticionListener(ChangeListener<Boolean> listener) {
        this.conRepeticion.selectedProperty().addListener(listener);
    }

    @Override
    public void ocultarHora() {
        esDiaCompleto.setSelected(true);
        root.getChildren().remove(horaInicio);
        root.getChildren().remove(minutoInicio);
        root.getChildren().remove(horaFin);
        root.getChildren().remove(minutoFin);
    }

    @Override
    public void mostrarHora() {
        esDiaCompleto.setSelected(false);
        root.add(horaInicio, 2, 2);
        root.add(minutoInicio, 3, 2);
        root.add(horaFin, 2, 3);
        root.add(minutoFin, 3, 3);
    }

    @Override
    public boolean estanCamposObligatoriosDiaCompleto() {
        return fechaInicio.getValue() != null &&
                fechaFin.getValue() != null;
    }

    @Override
    public boolean estanCamposObligatoriosPuntual() {
        return fechaInicio.getValue() != null &&
                !horaInicio.getEditor().getText().equals("") &&
                !minutoInicio.getEditor().getText().equals("") &&
                fechaFin.getValue() != null &&
                !horaFin.getEditor().getText().equals("") &&
                !minutoFin.getEditor().getText().equals("");
    }

    public void ocultarRepeticion() {
        root.getChildren().remove(dias);

    }

    public void mostrarRepeticion() {
        root.add(dias, 2, 4);

    }

    public long getFrecuencia() {
        return Long.parseLong(dias.getText());
    }
}
