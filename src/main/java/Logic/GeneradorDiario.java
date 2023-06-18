package Logic;

public class GeneradorDiario implements GeneradorDeInstancia {

    private final long dias;

    public GeneradorDiario(long dias) {
        this.dias = dias;
    }

    @Override
    public Evento generarInstanciaUnica(Evento elemento, int n) {
        var i = elemento.getInicio().plusDays(dias * n);

        return elemento.crearCopiaDesplazada(i);
    }

}