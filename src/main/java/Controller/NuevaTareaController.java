package Controller;

import Model.NuevaTareaModel;
import Model.NuevoElementoModel;
import View.NuevaTareaDialogPane;
import View.NuevoElementoDialogPane;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class NuevaTareaController extends NuevoElementoController {
    NuevaTareaModel model;
    NuevaTareaDialogPane pane;
    public NuevaTareaController(NuevaTareaModel model, NuevaTareaDialogPane pane) {
        super();
        this.model = model;
        this.pane = pane;
    }

    @Override
    public void crearDialogo() {
        dialog.setDialogPane(pane);
        dialog.setTitle("Nueva Tarea");
        dialog.setHeaderText("Crear nueva tarea");
    }

    @Override
    public NuevoElementoModel crearModel() {
        return model;
    }

    @Override
    public NuevoElementoDialogPane crearDialogPane() {
        return this.pane;
    }

    @Override
    public void crearElemento() {
        var pane = (NuevaTareaDialogPane) dialogPane;
        String titulo = pane.getTitulo();
        String descripcion = pane.getDescripcion();
        LocalDate fecha = pane.getFecha();

        if (elementoEsDeDiaCompleto) {
            model.crearTareaDiaCompleto(titulo, descripcion, fecha);
        } else {
            LocalDateTime vencimiento = fecha.atTime(pane.getHora(), pane.getMinutos());
            model.crearTareaPuntual(titulo, descripcion, vencimiento);
        }

    }
}
