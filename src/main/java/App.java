import Controller.ElementoListController;
import Controller.ElementoMenuController;
import Controller.ElementoPaneController;

import Controller.RealizadorDeAlarmas;
import Model.ElementoModel;
import Logic.Calendario;
import Model.NotificacionModel;
import Model.NuevoElementoModelFactory;
import View.ElementoMenuView;
import View.ElementoListView;
import View.ElementoPane;
import View.RootView;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDateTime;

public class App extends Application {
    Calendario calendario;
    String path = System.getProperty("user.dir") + "/data.txt";
    NotificacionModel notificacionModel;

    @Override
    public void start(Stage stage) throws Exception {
        calendario = cargar();
        ElementoModel elementoModel = new ElementoModel(calendario);
        notificacionModel = new NotificacionModel(calendario);
        var nuevoElementoModelFactory = new NuevoElementoModelFactory(calendario);

        ElementoListView elementoListView = new ElementoListView(elementoModel);
        ElementoPane elementoPane =  new ElementoPane();
        ElementoMenuView elementoMenuView = new ElementoMenuView(elementoModel);
        RootView view = new RootView(elementoListView, elementoPane, elementoMenuView);

        ElementoListController listController = new ElementoListController(elementoModel, elementoListView);
        ElementoPaneController treeController = new ElementoPaneController(elementoModel, elementoPane);
        ElementoMenuController menuController = new ElementoMenuController(elementoModel, nuevoElementoModelFactory, elementoMenuView);

        Scene scene = new Scene(view.asParent(), 1200, 800);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        stage.setOnCloseRequest(e -> {
            this.guardar();
        });

        crearAnimatioTimer();

    }

    private void guardar() {
        BufferedOutputStream bos;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(path));
            this.calendario.serializar(bos);
        } catch (IOException ignored) {

        }
    }

    private Calendario cargar() {
        BufferedInputStream bfr;
        try {
            var fr = new FileInputStream(path);
            bfr = new BufferedInputStream(fr);
            return Calendario.deserializar(bfr);
        } catch (FileNotFoundException e) {
            return new Calendario();
        }
    }

    private void crearAnimatioTimer() {
        new AnimationTimer() {
            private long ultimaActualizacion = 0 ;

            @Override
            public void handle(long now) {
                    if (now - ultimaActualizacion >= 2_000_000_000) {
                        notificacionModel.sonarAlarma(new RealizadorDeAlarmas());
                        ultimaActualizacion = now ;
                    }
            }
        }.start();
    }



    public static void main(String[] args) {
        launch();
    }

}