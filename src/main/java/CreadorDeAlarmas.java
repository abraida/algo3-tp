import java.time.Duration;
import java.time.LocalDateTime;

public  class CreadorDeAlarmas {

    private long idActual;

    public CreadorDeAlarmas() {
        this.idActual = 0;
    }
    public Alarma crearAlarmaAbsoluta(LocalDateTime tiempo, EfectoEnum tipoDeEfecto){
        var efecto = crearEfecto(tipoDeEfecto);
        this.idActual++;
        return new AlarmaAbsoluta(this.idActual-1, tiempo, efecto);
    }

    public Alarma crearAlarmaRelativa(Duration antelacion, EfectoEnum tipoDeEfecto){
        var efecto = crearEfecto(tipoDeEfecto);
        this.idActual++;
        return new AlarmaRelativa(this.idActual-1, antelacion, efecto);
    }

    private Efecto crearEfecto(EfectoEnum tipoDeEfecto){
        switch (tipoDeEfecto) {

            case NOTIFICACION -> {
                return new EfectoNotificacion();
            }
            case SONIDO -> {
                return new EfectoReproducirSonido();
            }
            case EMAIL -> {
                return new EfectoEnviarMail();
            }
        }

        return new EfectoSinEfecto();
    }
}
