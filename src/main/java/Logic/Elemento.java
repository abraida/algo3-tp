package Logic;

import java.time.LocalDateTime;
import java.util.ArrayList;


public abstract class Elemento implements Identificable, Alarmable, Suceso, Visitable {
    protected final ArrayList<Alarma> alarmas;
    private String titulo;
    private String descripcion;
    private long id;
    private long idActual;

    public Elemento(long id) {
        this.id = id;
        this.titulo = "";
        this.descripcion = "";
        this.alarmas = new ArrayList<>();
        this.idActual = 0;

    }

    public Elemento(String titulo, String descripcion) {
        this.id = 0;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.alarmas = new ArrayList<>();
        this.idActual = 0;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int compareTo(Suceso otroSuceso) {
        if (otroSuceso.getInicio().isBefore(this.getInicio()))
            return 1;
        if (otroSuceso.getInicio().isAfter(this.getInicio()))
            return -1;
        if (otroSuceso.getFin().isBefore(this.getFin())) {
            return 1;
        }
        if (otroSuceso.getFin().isAfter(this.getFin())) {
            return 1;
        }
        return 0;
    }

    @Override
    public long agregarAlarma(Alarma alarma) {
        alarma.setID(idActual);
        this.alarmas.add(alarma);

        this.idActual++;
        return this.idActual - 1;
    }

    @Override
    public boolean esAnteriorA(LocalDateTime tiempo) {
        return this.getFin().isBefore(tiempo);
    }

    @Override

    public boolean esSimultaneoA(LocalDateTime tiempo) {
        return !esPosteriorA(tiempo) && !esAnteriorA(tiempo);
    }

    @Override

    public boolean esPosteriorA(LocalDateTime tiempo) {
        return this.getInicio().isAfter(tiempo);
    }

    @Override
    public boolean eliminarAlarmaPorID(long alarmaID) {
        return this.alarmas.removeIf(e -> e.getID() == alarmaID);
    }

    @Override
    public Alarma obtenerAlarmaPorID(long alarmaID) {
        return alarmas.stream()
                .filter(x -> x.getID() == alarmaID)
                .findAny()
                .orElse(null);
    }

    @Override
    public long getID() {
        return this.id;
    }

    @Override
    public void setID(long id) {
        this.id = id;
    }

    @Override
    public ArrayList<Alarma> obtenerAlarmas() {
        return this.alarmas;
    }

}