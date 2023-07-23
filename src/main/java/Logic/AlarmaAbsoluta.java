package Logic;

import java.time.LocalDateTime;


public class AlarmaAbsoluta extends Alarma {

    private final LocalDateTime tiempo;

    public AlarmaAbsoluta(long id, LocalDateTime tiempo, Efecto efecto) {
        super(efecto);
        this.id = id;
        this.tiempo = tiempo;
    }

    public AlarmaAbsoluta(LocalDateTime tiempo, Efecto efecto) {
        super(efecto);
        this.id = 0;
        this.tiempo = tiempo;
    }

    @Override

    public LocalDateTime getTiempo(LocalDateTime tiempo) {
        return this.tiempo;
    }
}