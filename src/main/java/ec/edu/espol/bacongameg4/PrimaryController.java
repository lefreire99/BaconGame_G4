package ec.edu.espol.bacongameg4;

import ec.edu.espol.util.Edge;
import ec.edu.espol.util.DialogMessage;
import ec.edu.espol.util.Util;
import java.io.IOException;
import java.net.URL;
import java.util.Deque;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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
    private VBox dijkstraBox;
    
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
        if(txtActor1.getText()==null || txtActor2.getText()==null){
            DialogMessage.nullAlert();
        }else{
            if(!rightBox.getChildren().isEmpty())
                rightBox.getChildren().removeAll();
            dijkstra();
            bfs();
            //dfs();
        }
    }
    
    private void dijkstra(){
        if(dijkstraBox.getChildren().size()>1){
                dijkstraBox.getChildren().remove(1,dijkstraBox.getChildren().size());
            }
            Long timeStart1=System.currentTimeMillis();
            Util.grafo.dijkstra(txtActor1.getText());
            Deque<Edge<String>> rutaDijkstra = Util.grafo.caminoMinimo(txtActor1.getText(), txtActor2.getText());
            Label numActores1=new Label("Cantidad: "+rutaDijkstra.size());
            while(!rutaDijkstra.isEmpty()){
                Edge<String> arco = rutaDijkstra.pop();
                System.out.println(arco.getVDestino());
                System.out.println(arco.getPelicula()); //Se podria invertir el arco
                System.out.println(arco.getVOrigen());
                System.out.println("-------------------");
            }
            Long timeEnd1=System.currentTimeMillis();
            Long timeFinal1=timeEnd1-timeStart1;
            Label time1=new Label("Tiempo: "+timeFinal1);
            dijkstraBox.getChildren().addAll(numActores1,time1);
    }
    
    private void bfs(){
        if(bfsBox.getChildren().size()>1){
                bfsBox.getChildren().remove(1,bfsBox.getChildren().size());
            }
            Long timeStart2=System.currentTimeMillis();
            Util.grafo.bfs(txtActor1.getText());
            Deque<Edge<String>> rutaBfs = Util.grafo.caminoMinimo(txtActor1.getText(), txtActor2.getText());
            Label numActores2=new Label("Cantidad: "+rutaBfs.size());
            while(!rutaBfs.isEmpty()){
                Edge<String> arco = rutaBfs.pop();
                System.out.println(arco.getVDestino());
                System.out.println(arco.getPelicula()); //Se podria invertir el arco
                System.out.println(arco.getVOrigen());
                System.out.println("-------------------");
            }
            Long timeEnd2=System.currentTimeMillis();
            Long timeFinal2=timeEnd2-timeStart2;
            Label time2=new Label("Tiempo: "+timeFinal2);
            bfsBox.getChildren().addAll(numActores2,time2);
    }
    
    private void dfs(){
        if(dfsBox.getChildren().size()>1){
                dfsBox.getChildren().remove(1,dfsBox.getChildren().size());
            }
            Long timeStart3=System.currentTimeMillis();
            Util.grafo.dfs(txtActor1.getText());
            Deque<Edge<String>> rutaDfs = Util.grafo.caminoMinimo(txtActor1.getText(), txtActor2.getText());
            Label numActores3=new Label("Cantidad: "+rutaDfs.size());
            while(!rutaDfs.isEmpty()){
                Edge<String> arco = rutaDfs.pop();
                System.out.println(arco.getVDestino());
                System.out.println(arco.getPelicula()); //Se podria invertir el arco
                System.out.println(arco.getVOrigen());
                System.out.println("-------------------");
            }
            Long timeEnd3=System.currentTimeMillis();
            Long timeFinal3=timeEnd3-timeStart3;
            Label time3=new Label("Tiempo: "+timeFinal3);
            dfsBox.getChildren().addAll(numActores3,time3);
    }
    
}
