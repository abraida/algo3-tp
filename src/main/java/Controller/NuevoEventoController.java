package Controller;

import Model.NuevoElementoModel;
import Model.NuevoEventoModel;
import View.NuevoEventoDialogPane;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class NuevoEventoController extends NuevoElementoController{
    NuevoEventoDialogPane DPview;
    NuevoEventoModel DPmodel;
    boolean eventoConRepeticion = false;
    public NuevoEventoController(NuevoEventoModel model) {
        super(model, new NuevoEventoDialogPane());

        dialog.setTitle("Nuevo Evento");
        dialog.setHeaderText("Crear nuevo evento");

        DPmodel = model;
        DPview = (NuevoEventoDialogPane) nuevoElementoDialogPane;
        DPview.ocultarRepeticion();

        DPview.registrarConRepeticionListener((obs, old, n) -> {
            eventoConRepeticion = n;
            if (n){
                DPview.mostrarRepeticion();
                return;
            }
                DPview.ocultarRepeticion();
        });
    }


    @Override
    public void crearElemento() {
            String titulo = DPview.getTitulo();
            String descripcion = DPview.getDescripcion();
            LocalDate fechaInicio = DPview.getFechaInicio();
            LocalDate fechaFin = DPview.getFechaFin();
            if (elementoEsDeDiaCompleto){
                DPmodel.crearEventoDiaCompleto(titulo, descripcion, fechaInicio, fechaFin);
            } else {
                LocalDateTime inicio = fechaInicio.atTime(DPview.getHoraInicio(), DPview.getMinutosInicio());
                LocalDateTime fin = fechaFin.atTime(DPview.getHoraFin(), DPview.getMinutosFin());

                DPmodel.crearEventoPuntual(titulo, descripcion, inicio, fin);
            }

            if (eventoConRepeticion){
                DPmodel.crearRepeticion(DPview.getFrecuencia());
            } else {
                DPmodel.crearRepeticionUnica();
            }
    }
}