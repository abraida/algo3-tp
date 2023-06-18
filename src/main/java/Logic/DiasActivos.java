package Logic;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class DiasActivos {

    private final ArrayList<Boolean> listaDiasActivos;

    public DiasActivos(boolean lunesActivo,
							boolean martesActivo,
							boolean miercolesActivo,
							boolean juevesActivo,
							boolean viernesActivo,
							boolean sabadoActivo,
							boolean domingoActivo){

        this.listaDiasActivos = new ArrayList<>(List.of(
                lunesActivo,
                martesActivo,
                miercolesActivo,
                juevesActivo,
                viernesActivo ,
                sabadoActivo,
                domingoActivo));
    }

    public boolean estaActivo(DayOfWeek dia){
        return listaDiasActivos.get(dia.getValue() - 1);
    }


}
