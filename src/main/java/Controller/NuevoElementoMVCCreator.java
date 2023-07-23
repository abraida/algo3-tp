package Controller;

import Logic.Calendario;
import Model.NuevaTareaModel;
import Model.NuevoEventoModel;
import View.NuevaTareaDialogPane;
import View.NuevoEventoDialogPane;

public class NuevoElementoMVCCreator {
    Calendario calendario;

    public NuevoElementoMVCCreator(Calendario calendario) {
        this.calendario = calendario;
    }

    public void crearNuevaTarea() {
            var m = new NuevaTareaModel(calendario);
            var v = new NuevaTareaDialogPane();
            var c = new NuevaTareaController(m, v);
            c.lanzarDialogo();
    }

    public void crearNuevoEvento() {
            var m = new NuevoEventoModel(calendario);
            var v = new NuevoEventoDialogPane();
            var c = new NuevoEventoController(m, v);
            c.lanzarDialogo();
    }
}
