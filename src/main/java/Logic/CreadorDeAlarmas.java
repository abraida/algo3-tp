package Logic;

import java.time.Duration;
import java.time.LocalDateTime;

public  class CreadorDeAlarmas {


    public Alarma crearAlarmaAbsoluta(LocalDateTime tiempo, EfectoEnum tipoDeEfecto){
        var efecto = crearEfecto(tipoDeEfecto);
        return new AlarmaAbsoluta(0, tiempo, efecto);
    }

    public Alarma crearAlarmaRelativa(Duration antelacion, EfectoEnum tipoDeEfecto){
        var efecto = crearEfecto(tipoDeEfecto);
        return new AlarmaRelativa(0, antelacion, efecto);
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
