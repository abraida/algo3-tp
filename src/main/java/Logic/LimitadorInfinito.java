package Logic;

public class LimitadorInfinito implements LimitadorDeRepeticion{
    @Override
    public boolean esInstanciaValida(Evento evento, long i) {
        return true;
    }


}
