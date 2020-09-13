package ec.edu.espol.bacongameg4;

import ec.edu.espol.util.Util;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PrimaryController {
    
    @FXML
    public static Label title;
    
    @FXML
    private void buscar() throws IOException {
        System.out.println(Util.grafo.vertexes.size());
    }
    
}
