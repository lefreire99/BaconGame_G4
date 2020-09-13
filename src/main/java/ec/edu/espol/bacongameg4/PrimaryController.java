package ec.edu.espol.bacongameg4;

import ec.edu.espol.util.Util;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class PrimaryController implements Initializable {
    
    @FXML
    private VBox rightBox;
    
    @FXML
    private VBox bfsBox;
    
    @FXML
    private VBox dfsBox;
    
    @FXML
    private VBox dijkstra;
    
    @FXML
    private TextField txtActor1;
    
    @FXML
    private TextField txtActor2;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        Util.leerArchivo();
    }
    
    @FXML
    private void buscar() throws IOException {
        System.out.println(Util.grafo.vertexes.size());
    }
    
}
