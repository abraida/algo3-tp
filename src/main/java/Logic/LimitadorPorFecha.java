package Logic;

import java.time.LocalDateTime;

public class LimitadorPorFecha implements LimitadorDeRepeticion {
    LocalDateTime fecha;

    public LimitadorPorFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean esInstanciaValida(Evento evento, long i) {
        return evento.esAnteriorA(fecha);
    }
}
