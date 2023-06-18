package Logic;

public class LimitadorPorCantidad implements LimitadorDeRepeticion {
    private final long repeticiones;

    public LimitadorPorCantidad(long repeticiones) {
        this.repeticiones = repeticiones;
    }

    @Override
    public boolean esInstanciaValida(Evento evento, long i) {
        return i < repeticiones;
    }
}
