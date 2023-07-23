package Controller;

import Model.NuevoElementoModel;
import Model.NuevoEventoModel;
import View.NuevoElementoDialogPane;
import View.NuevoEventoDialogPane;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class NuevoEventoController extends NuevoElementoController {
    NuevoEventoDialogPane pane;
    NuevoEventoModel model;
    boolean eventoConRepeticion = false;


    public NuevoEventoController(NuevoEventoModel model, NuevoEventoDialogPane pane) {
        super();
        this.pane = pane;
        this.model = model;

        pane.ocultarRepeticion();

        pane.registrarConRepeticionListener((obs, old, n) -> {
            eventoConRepeticion = n;
            if (n) {
                pane.mostrarRepeticion();
                return;
            }
            pane.ocultarRepeticion();
        });
    }

    @Override
    public void crearDialogo() {
        dialog.setDialogPane(pane);
        dialog.setTitle("Nuevo Evento");
        dialog.setHeaderText("Crear nuevo evento");
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
        String titulo = pane.getTitulo();
        String descripcion = pane.getDescripcion();
        LocalDate fechaInicio = pane.getFechaInicio();
        LocalDate fechaFin = pane.getFechaFin();

        if (elementoEsDeDiaCompleto) {
            model.crearEventoDiaCompleto(titulo, descripcion, fechaInicio, fechaFin);
        } else {
            LocalDateTime inicio = fechaInicio.atTime(pane.getHoraInicio(), pane.getMinutosInicio());
            LocalDateTime fin = fechaFin.atTime(pane.getHoraFin(), pane.getMinutosFin());

            model.crearEventoPuntual(titulo, descripcion, inicio, fin);
        }

        if (eventoConRepeticion) {
            model.crearRepeticion(pane.getFrecuencia());
        } else {
            model.crearRepeticionUnica();
        }
    }
}