package Controller;

import Model.NuevaTareaModel;
import View.NuevaTareaDialogPane;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class NuevaTareaController extends NuevoElementoController{
    public NuevaTareaController(NuevaTareaModel model) {
        super(model, new NuevaTareaDialogPane());

        dialog.setTitle("Nueva Tarea");
        dialog.setHeaderText("Crear nueva tarea");
    }

    @Override
    public void crearElemento() {
            var NTview = (NuevaTareaDialogPane) nuevoElementoDialogPane;
            var NTmodel = (NuevaTareaModel) model;
            String titulo = NTview.getTitulo();
            String descripcion = NTview.getDescripcion();
            LocalDate fecha = NTview.getFecha();

            if (elementoEsDeDiaCompleto){
                NTmodel.crearTareaDiaCompleto(titulo, descripcion, fecha);
            } else {
                LocalDateTime vencimiento = fecha.atTime(NTview.getHora(), NTview.getMinutos());
                NTmodel.crearTareaPuntual(titulo, descripcion, vencimiento);
            }

    }
}
