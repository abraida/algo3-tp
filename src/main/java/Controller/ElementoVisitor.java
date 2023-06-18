package Controller;

import Logic.*;

public interface ElementoVisitor {
    void visitarTareaPuntual(TareaPuntual tarea);
    void visitarTareaDiaria(TareaDiaria tarea);
    void visitarEventoDiario(EventoDiario evento);
    void visitarEventoPuntual(EventoPuntual evento);

}
