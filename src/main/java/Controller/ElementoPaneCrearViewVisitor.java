package Controller;

import Logic.EventoDiario;
import Logic.EventoPuntual;
import Logic.TareaDiaria;
import Logic.TareaPuntual;
import View.*;

public class ElementoPaneCrearViewVisitor implements ElementoVisitor {
    private ElementoView elementoView;
    private AlarmaTreeView alarmaTreeView;

    @Override
    public void visitarTareaPuntual(TareaPuntual tarea) {
        elementoView = new TareaPuntualView(tarea);
        alarmaTreeView = new AlarmaTreeView(tarea);
    }

    @Override
    public void visitarTareaDiaria(TareaDiaria tarea) {
        elementoView = new TareaDiariaView(tarea);
        alarmaTreeView = new AlarmaTreeView(tarea);

    }

    @Override
    public void visitarEventoDiario(EventoDiario evento) {
        elementoView = new EventoDiarioView(evento);
        alarmaTreeView = new AlarmaTreeView(evento);
    }

    @Override
    public void visitarEventoPuntual(EventoPuntual evento) {
        elementoView = new EventoPuntualView(evento);
        alarmaTreeView = new AlarmaTreeView(evento);

    }

    public void poblarPane(ElementoPane pane) {
        pane.setElementoView(elementoView);
        pane.setAlarmasView(alarmaTreeView);

    }
}
