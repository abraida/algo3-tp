package Controller;

import Model.NuevoElementoModel;
import View.AgregarAlarmasDialogPane;
import View.AlarmaTreeView;
import View.NuevoElementoDialogPane;
import javafx.scene.control.Dialog;

public abstract class NuevoElementoController {
    protected final Dialog<Object> dialog;
    protected NuevoElementoModel model;
    protected NuevoElementoDialogPane nuevoElementoDialogPane;
    protected AgregarAlarmasDialogPane agregarAlarmasDialogPane;
    protected boolean elementoEsDeDiaCompleto;

    public NuevoElementoController(NuevoElementoModel model, NuevoElementoDialogPane pane) {
        this.model = model;
        this.nuevoElementoDialogPane = pane;
        this.agregarAlarmasDialogPane = new AgregarAlarmasDialogPane();

        dialog = new Dialog<>();
        dialog.setDialogPane(pane);

        initNuevoElementoView();
        initAlarmasView();
    }

    public void initNuevoElementoView() {
        elementoEsDeDiaCompleto = false;

        nuevoElementoDialogPane.registrarEsDiaCompletoListener((obs, old, n) -> {
            elementoEsDeDiaCompleto = n;
            if (n) {
                nuevoElementoDialogPane.ocultarHora();
                return;
            }
            nuevoElementoDialogPane.mostrarHora();
        });

        nuevoElementoDialogPane.registrarNextButtonAccion(e -> {
            e.consume();
            if (!validarCamposObligatorios()) {
                return;
            }
            crearElemento();
            pasarAMenuDeAlarmas();
        });
    }

    public void initAlarmasView() {
        agregarAlarmasDialogPane.registrarPreviousButtonAction(e -> {
            dialog.setDialogPane(nuevoElementoDialogPane);
            e.consume();
        });

        agregarAlarmasDialogPane.registrarFinishButtonAction(e -> {
            model.mandarACalendario();
        });

        agregarAlarmasDialogPane.registrarCrearAlarmaAction(e -> {
            var dias = agregarAlarmasDialogPane.getDias();
            var horas = agregarAlarmasDialogPane.getHoras();
            var minutos = agregarAlarmasDialogPane.getMinutos();

            model.crearAlarma(dias, horas, minutos);
            agregarAlarmasDialogPane.setAlarmaTreeView(new AlarmaTreeView(model.getElemento()));
            dialog.setDialogPane(agregarAlarmasDialogPane);
        });
    }

    private boolean validarCamposObligatorios() {
        return (elementoEsDeDiaCompleto
                && nuevoElementoDialogPane.estanCamposObligatoriosDiaCompleto())
                || (!elementoEsDeDiaCompleto
                && nuevoElementoDialogPane.estanCamposObligatoriosPuntual());
    }

    public abstract void crearElemento();

    private void pasarAMenuDeAlarmas() {
        dialog.setDialogPane(agregarAlarmasDialogPane);
        agregarAlarmasDialogPane.setAlarmaTreeView(new AlarmaTreeView(model.getElemento()));
    }

    public void lanzarDialogo() {
        dialog.showAndWait();
    }
}
