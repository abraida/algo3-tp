package View;

import Logic.PeriodoEnum;
import Model.ElementoModel;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ElementoMenuView extends VBox {

    public ToggleGroup toogleGroup;
    private ElementoModel model;
    private HBox pagination;
    private ToolBar toolBar;
    private Button buttonCrearEvento;
    private Button buttonCrearTarea;

    private Button buttonNext;
    private Button buttonToday;
    private Button buttonPrevious;

    public ElementoMenuView(ElementoModel elementoModel) {
        this.model = elementoModel;

        this.toolBar = new ToolBar();

        buttonCrearEvento = new Button("Nuevo Evento");
        toolBar.getItems().add(buttonCrearEvento);

        buttonCrearTarea = new Button("Nueva Tarea");
        toolBar.getItems().add(buttonCrearTarea);

        toogleGroup = new ToggleGroup();

        RadioButton r1 = new RadioButton("Eventos Diarios");
        r1.getProperties().put(PeriodoEnum.getEnumName(), PeriodoEnum.DIARIO);
        RadioButton r2 = new RadioButton("Eventos Semanales");
        r2.getProperties().put(PeriodoEnum.getEnumName(), PeriodoEnum.SEMANAL);
        RadioButton r3 = new RadioButton("Eventos Mensuales");
        r3.getProperties().put(PeriodoEnum.getEnumName(), PeriodoEnum.MENSUAL);

        r1.setToggleGroup(toogleGroup);
        r2.setToggleGroup(toogleGroup);
        r3.setToggleGroup(toogleGroup);
        toolBar.getItems().add(r1);
        toolBar.getItems().add(r2);
        toolBar.getItems().add(r3);
        r1.setSelected(true);

        this.pagination = new HBox();
        this.buttonPrevious = new Button("Anterior");
        this.buttonToday = new Button("Hoy");
        this.buttonNext = new Button("Siguiente");

        this.pagination.getChildren().add(buttonPrevious);
        this.pagination.getChildren().add(buttonToday);
        this.pagination.getChildren().add(buttonNext);

        this.getChildren().add(toolBar);
        this.getChildren().add(pagination);
    }

    public void registrarNuevaTareaAccion(EventHandler<ActionEvent> e) {
        buttonCrearTarea.setOnAction(e);
    }

    public void registrarPeriodoDeEventosListener(ChangeListener<Toggle> listener) {
        toogleGroup.selectedToggleProperty().addListener(listener);
    }

    public void registrarNextButtonAccion(EventHandler<ActionEvent> e) {
        buttonNext.setOnAction(e);
    }

    public void registrarPreviousButtonAccion(EventHandler<ActionEvent> e) {
        buttonPrevious.setOnAction(e);
    }

    public void registrarTodayButtonAccion(EventHandler<ActionEvent> e) {
        buttonToday.setOnAction(e);
    }

    public void registrarNuevoEventoAccion(EventHandler<ActionEvent> e) {
        buttonCrearEvento.setOnAction(e);
    }

}
