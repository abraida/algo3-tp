package Controller;

import Model.NuevaTareaModel;
import Model.NuevoEventoModel;

public class NuevoElementoControllerFactory {

    public NuevaTareaController crearNuevaTareaController(NuevaTareaModel model){
        return new NuevaTareaController(model);
    }

    public NuevoElementoController crearNuevoEventoController(NuevoEventoModel model){
        return new NuevoEventoController(model);
    }
}
