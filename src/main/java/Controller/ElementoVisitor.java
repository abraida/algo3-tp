package Controller;

import Logic.EventoDiario;
import Logic.EventoPuntual;
import Logic.TareaDiaria;
import Logic.TareaPuntual;

public interface ElementoVisitor {
    void visitarTareaPuntual(TareaPuntual tarea);

    void visitarTareaDiaria(TareaDiaria tarea);

    void visitarEventoDiario(EventoDiario evento);

    void visitarEventoPuntual(EventoPuntual evento);

}
