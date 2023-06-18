package Model;

import Logic.Calendario;

public class NuevoElementoModelFactory {
    Calendario calendario;
    public NuevoElementoModelFactory(Calendario calendario) {
        this.calendario = calendario;
    }

    public NuevaTareaModel crearNuevaTareaModel(){
        return new NuevaTareaModel(calendario);
    }

    public NuevoEventoModel crearNuevoEventoModel(){
        return new NuevoEventoModel(calendario);
    }
}
