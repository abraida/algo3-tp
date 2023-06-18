package View;

import Logic.Alarma;
import Logic.Alarmable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;

public class AlarmaTreeView extends VBox {
    Alarmable alarmable;
    public AlarmaTreeView(Alarmable alarmable) {
        super();
        this.alarmable = alarmable;

        TreeView<String> tv = new TreeView<>();
        TreeItem<String> root = new TreeItem<>("Alarmas");
        tv.setShowRoot(false);

        for (Alarma alarma: alarmable.obtenerAlarmas()) {
            TreeItem<String> nombreAlarma = new TreeItem<>("Alarma N°" + alarma.getID());
            TreeItem<String> horaAlarma = new TreeItem<String>("Hora: " + alarmable.obtenerTiempoDeAlarma(alarma));
            TreeItem<String> efectoAlarma = new TreeItem<String>("Efecto: " + alarma.disparar());

            root.getChildren().add(nombreAlarma);
            nombreAlarma.getChildren().add(horaAlarma);
            nombreAlarma.getChildren().add(efectoAlarma);
        }
        tv.setRoot(root);
        this.getChildren().add(tv);
    }
}
