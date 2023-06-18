package Logic;

import java.time.LocalDateTime;
import java.util.ArrayList;


public class RepetidorDeEventos {

    protected final Evento elemento;
    private GeneradorDeInstancia generadorDeInstancia;
    private LimitadorDeRepeticion limitador;

    public RepetidorDeEventos(Evento elemento, LimitadorDeRepeticion limitador, GeneradorDeInstancia generadorDeInstancia) {
        this.elemento = elemento;
        this.limitador = limitador;
        this.generadorDeInstancia = generadorDeInstancia;
    }

    public Evento getElemento() {
        return elemento;
    }

    public void modificarLimiteDeRepeticiones(LimitadorDeRepeticion limitador) {
        this.limitador = limitador;
    }

    public void modificarFrecuenciaDeRepeticion(GeneradorDeInstancia freqDeRepeticion) {
        this.generadorDeInstancia = freqDeRepeticion;
    }

    public ArrayList<Evento> generarInstancias(int inicio, int fin) {
        var instancias = new ArrayList<Evento>();

        for (int i = inicio; i < fin; i++) {
            var instancia = generadorDeInstancia.generarInstanciaUnica(elemento, i);
            if (!limitador.esInstanciaValida(instancia, i))
                break;
            instancias.add(instancia);
        }


        return instancias;
    }


    public ArrayList<Evento> generarEventosPosteriores(int cantidad, LocalDateTime tiempo) {
        var instancias = new ArrayList<Evento>();

        for (int i = 0; instancias.size() < cantidad; i++) {
            var instancia = generadorDeInstancia.generarInstanciaUnica(elemento, i);

            if (!limitador.esInstanciaValida(instancia, i))
                break;

            if (!instancia.esAnteriorA(tiempo))
                instancias.add(instancia);
        }

        return instancias;
    }

}