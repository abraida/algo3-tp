package Controller;

import Logic.*;
import View.*;
import javafx.scene.layout.Pane;


public class ElementoCellCreatorVisitor implements ElementoVisitor {
    private ElementoCellView view;
    @Override
    public void visitarTareaPuntual(TareaPuntual tarea) {
        view = new TareaPuntualCellView(tarea);
    }

    @Override
    public void visitarTareaDiaria(TareaDiaria tarea) {
        view = new TareaDiariaCellView(tarea);
    }

    @Override
    public void visitarEventoDiario(EventoDiario evento) {
        view = new EventoDiarioCellView(evento);

    }

    @Override
    public void visitarEventoPuntual(EventoPuntual evento) {
        view = new EventoPuntualCellView(evento);

    }


    public Pane crearVista(){
        return this.view;
    }

}
