package ec.edu.espol.bacongameg4;

import ec.edu.espol.util.Util;
import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void buscar() throws IOException {
        Util.leerArchivo();
    }
}
