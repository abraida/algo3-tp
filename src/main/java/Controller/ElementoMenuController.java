package Controller;

import Model.ElementoModel;
import Model.NuevoElementoModelFactory;
import Logic.PeriodoEnum;
import View.ElementoMenuView;

public class ElementoMenuController {

    private final ElementoModel model;
    private final ElementoMenuView menuView;
    private NuevoElementoControllerFactory nuevoElementoControllerFactory;
    private NuevoElementoModelFactory nuevoElementoModelFactory;


    public ElementoMenuController(ElementoModel model,
                                  NuevoElementoModelFactory nuevoElementoModelFactory, ElementoMenuView menuView) {
        this.menuView = menuView;
        this.model = model;
        this.nuevoElementoModelFactory = nuevoElementoModelFactory;
        this.nuevoElementoControllerFactory = new NuevoElementoControllerFactory();

        initView();
        initModel();
    }

    public void initView(){
        menuView.registrarNextButtonAccion(e ->{
            model.setPaginaActual(model.getPaginaActual() + 1);
        });

        menuView.registrarPreviousButtonAccion(e ->{
            model.setPaginaActual(model.getPaginaActual() - 1);
        });

        menuView.registrarTodayButtonAccion(e ->{
            model.setPaginaActual(0);
        });

        menuView.registrarNuevaTareaAccion(e -> {
            var m = nuevoElementoModelFactory.crearNuevaTareaModel();
            var c = nuevoElementoControllerFactory.crearNuevaTareaController(m);
            c.lanzarDialogo();
            model.generarElementos();
        });

        menuView.registrarNuevoEventoAccion(e -> {
            var m = nuevoElementoModelFactory.crearNuevoEventoModel();
            var c = nuevoElementoControllerFactory.crearNuevoEventoController(m);
            c.lanzarDialogo();
            model.generarElementos();
        });
    }

    public void initModel() {
        menuView.registrarPeriodoDeEventosListener((obs, old, n) -> {
            model.setPeriodo(((PeriodoEnum) n.getProperties().get(PeriodoEnum.getEnumName())));
        });

        model.getPeriodoProperty().addListener((obs, old, n) -> {
            if(n != null) {
                menuView.toogleGroup.getToggles()
                        .stream()
                        .filter( (rb) -> rb.getProperties().get(PeriodoEnum.getEnumName()).equals(n) )
                        .forEach( menuView.toogleGroup::selectToggle );
            }
        });
    }
}
