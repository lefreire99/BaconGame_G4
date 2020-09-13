package ec.edu.espol.bacongameg4;

import ec.edu.espol.util.Edge;
import ec.edu.espol.util.Util;
import java.io.IOException;
import java.net.URL;
import java.util.Deque;
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
        //Util.leerArchivo();
    }
    
    @FXML
    private void buscar() throws IOException {
        System.out.println(Util.grafo.vertexes.size());
        
        Util.grafo.dijkstra(txtActor1.getText());
        Deque<Edge<String>> ruta = Util.grafo.caminoMinimo(txtActor1.getText(), txtActor2.getText());
        while(!ruta.isEmpty()){
            Edge<String> arco = ruta.pop();
            System.out.println("dest "+arco.getVDestino());
            
            System.out.println("Pelicula "+arco.getPelicula());
            System.out.println("Org "+arco.getVOrigen());
            System.out.println("--------------");
            
        }
            
        /*Util.grafo.bfs(txtActor1.getText());
        ruta = Util.grafo.caminoMinimo(txtActor1.getText(), txtActor2.getText());
        while(!ruta.isEmpty())
            System.out.println(ruta.pop());
        
        Util.grafo.dfs(txtActor1.getText());
        ruta = Util.grafo.caminoMinimo(txtActor1.getText(), txtActor2.getText());
        while(!ruta.isEmpty())
            System.out.println(ruta.pop());*/
    }
    
}
