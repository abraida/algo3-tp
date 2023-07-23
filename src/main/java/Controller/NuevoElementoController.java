package Controller;

import Model.NuevoElementoModel;
import View.AgregarAlarmasDialogPane;
import View.AlarmaTreeView;
import View.NuevoElementoDialogPane;
import javafx.scene.control.Dialog;

public abstract class NuevoElementoController {
    protected  Dialog<String> dialog;
    protected NuevoElementoModel model;
    protected NuevoElementoDialogPane dialogPane;
    protected AgregarAlarmasDialogPane agregarAlarmasDialogPane;
    protected boolean elementoEsDeDiaCompleto;

    public NuevoElementoController() {
        this.agregarAlarmasDialogPane = new AgregarAlarmasDialogPane();
    }

    public void lanzarDialogo() {
        dialog = new Dialog<>();
        this.model = crearModel();
        this.dialogPane = crearDialogPane();

        init();

        dialog.showAndWait();
    }
    public void init(){
        crearDialogo();
        initNuevoElementoView();
        initAlarmasView();
    }

    public abstract void crearDialogo();

    public abstract NuevoElementoModel crearModel();
    public abstract NuevoElementoDialogPane crearDialogPane();
    public abstract void crearElemento();
    public void initNuevoElementoView() {
        elementoEsDeDiaCompleto = false;

        dialogPane.registrarEsDiaCompletoListener((obs, old, n) -> {
            elementoEsDeDiaCompleto = n;
            if (n) {
                dialogPane.ocultarHora();
                return;
            }
            dialogPane.mostrarHora();
        });

        dialogPane.registrarNextButtonAccion(e -> {
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
            dialog.setDialogPane(dialogPane);
            e.consume();
        });

        agregarAlarmasDialogPane.registrarFinishButtonAction(e ->
                model.mandarACalendario());

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
                && dialogPane.estanCamposObligatoriosDiaCompleto())
                || (!elementoEsDeDiaCompleto
                && dialogPane.estanCamposObligatoriosPuntual());
    }


    private void pasarAMenuDeAlarmas() {
        dialog.setDialogPane(agregarAlarmasDialogPane);
        agregarAlarmasDialogPane.setAlarmaTreeView(new AlarmaTreeView(model.getElemento()));
    }
}
