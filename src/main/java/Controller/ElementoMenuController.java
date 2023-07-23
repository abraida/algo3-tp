package Controller;

import Logic.PeriodoEnum;
import Model.ElementoModel;
import View.ElementoMenuView;

public class ElementoMenuController {

    private final ElementoModel model;
    private final ElementoMenuView menuView;
    private NuevoElementoMVCCreator nuevoElementoMVCCreator;


    public ElementoMenuController(ElementoModel model, ElementoMenuView menuView, NuevoElementoMVCCreator nuevoElementoMVCCreator) {
        this.menuView = menuView;
        this.model = model;
        this.nuevoElementoMVCCreator = nuevoElementoMVCCreator;

        initView();
        initModel();
    }

    public void initView() {
        menuView.registrarNextButtonAccion(e ->
                model.setPaginaActual(model.getPaginaActual() + 1));

        menuView.registrarPreviousButtonAccion(e ->
                model.setPaginaActual(model.getPaginaActual() - 1));

        menuView.registrarTodayButtonAccion(e ->
                model.setPaginaActual(0));

        menuView.registrarNuevaTareaAccion(e -> {
            nuevoElementoMVCCreator.crearNuevaTarea();
            model.generarElementos();

        });

        menuView.registrarNuevoEventoAccion(e -> {
            nuevoElementoMVCCreator.crearNuevoEvento();
            model.generarElementos();
        });
    }

    public void initModel() {
        menuView.registrarPeriodoDeEventosListener((obs, old, n) -> {
            model.setPeriodo(((PeriodoEnum) n.getProperties().get(PeriodoEnum.getEnumName())));
        });

        model.getPeriodoProperty().addListener((obs, old, n) -> {
            if (n != null) {
                menuView.toogleGroup.getToggles()
                        .stream()
                        .filter((rb) -> rb.getProperties().get(PeriodoEnum.getEnumName()).equals(n))
                        .forEach(menuView.toogleGroup::selectToggle);
            }
        });
    }
}
