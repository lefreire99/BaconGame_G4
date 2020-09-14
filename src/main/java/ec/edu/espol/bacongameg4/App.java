package ec.edu.espol.bacongameg4;

import ec.edu.espol.util.Data;
import ec.edu.espol.util.Util;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class App extends Application {

    private Scene scene;

    @Override
    public void start(Stage stage){
        Util.leerArchivo(Data.getInstance().getGrafo());
        try {
            scene = new Scene(loadFXML("primary"), 640, 480);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        stage.setScene(scene);
        stage.show();
    }

    private void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}