package Logic;

import java.time.LocalDateTime;

public abstract class Evento extends Elemento {
    public Evento(String titulo, String descripcion) {
        super(titulo, descripcion);
    }

    public abstract Evento crearCopiaDesplazada(LocalDateTime inicio);

    public abstract LocalDateTime getInicio();
}