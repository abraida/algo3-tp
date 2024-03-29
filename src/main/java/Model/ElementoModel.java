package Model;

import Logic.Calendario;
import Logic.Elemento;
import Logic.PeriodoEnum;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;

public class ElementoModel {
    public final LocalDate hoy;
    private final Calendario calendario;
    private final ObservableList<Elemento> listaDeElementos = FXCollections.observableArrayList();
    private final ObjectProperty<Elemento> elementoActual = new SimpleObjectProperty<>(null);
    private final IntegerProperty paginaActual = new SimpleIntegerProperty();
    private final ObjectProperty<PeriodoEnum> periodo = new SimpleObjectProperty<>();

    public ElementoModel(Calendario calendario) {
        this.calendario = calendario;
        this.hoy = LocalDate.now();
        this.periodo.set(PeriodoEnum.DIARIO);
        this.paginaActual.set(0);
        generarElementos();
    }

    public ObjectProperty<Elemento> getElementoActualProperty() {
        return elementoActual;
    }

    public final void setElementoActual(Elemento e) {
        elementoActual.set(e);
    }

    public final void setPeriodo(PeriodoEnum p) {
        periodo.set(p);
        generarElementos();
    }

    public ObservableList<Elemento> getListaDeElementos() {
        return listaDeElementos;
    }

    public int getPaginaActual() {
        return paginaActual.get();
    }

    public void setPaginaActual(int i) {
        paginaActual.set(i);
        generarElementos();
    }

    public void generarElementos() {
        var elementos = new ArrayList<Elemento>();
        switch (periodo.get()) {
            case DIARIO -> elementos = calendario.obtenerElementosDiarios(hoy.plusDays(getPaginaActual()));
            case SEMANAL -> elementos = calendario.obtenerElementosSemanales(hoy.plusWeeks(getPaginaActual()));
            case MENSUAL -> elementos = calendario.obtenerElementosMensuales(hoy.plusMonths(getPaginaActual()));
        }
        listaDeElementos.setAll(elementos);
    }

    public ObjectProperty<PeriodoEnum> getPeriodoProperty() {
        return periodo;
    }
}
