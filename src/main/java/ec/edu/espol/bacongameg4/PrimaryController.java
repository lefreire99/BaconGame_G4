package ec.edu.espol.bacongameg4;

import ec.edu.espol.util.Data;
import ec.edu.espol.util.Edge;
import ec.edu.espol.util.DialogMessage;
import ec.edu.espol.util.StackActor;
import ec.edu.espol.util.StackMovie;
import java.io.IOException;
import java.util.Deque;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

public class PrimaryController {
    
    @FXML
    private ScrollPane scrollPane;
    
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
    
    @FXML
    private void buscar() throws IOException {
        System.out.println(Data.getInstance().getGrafo().getVertexes().size());
        if(txtActor1.getText().isEmpty()|| txtActor2.getText().isEmpty()){
            DialogMessage.nullAlert();
        }else{
            if(scrollPane.getContent()!=null)
                scrollPane.setContent(null);
            if(Data.getInstance().getGrafo().hasActor(txtActor1.getText()) && Data.getInstance().getGrafo().hasActor(txtActor2.getText())){
                dijkstra();
                bfs();
                dfs();
            }else{
                DialogMessage.errorAlert();
            }
        }
    }
    
    private void dijkstra(){
        if(dijkstraBox.getChildren().size()>1){
                dijkstraBox.getChildren().remove(1,dijkstraBox.getChildren().size());
        }
        Long timeStart1=System.currentTimeMillis();
        Data.getInstance().getGrafo().dijkstra(txtActor1.getText());
        Deque<Edge<String>> rutaDijkstra = Data.getInstance().getGrafo().caminoMinimo(txtActor1.getText(), txtActor2.getText());
        Label numActores1=new Label("Cantidad: "+rutaDijkstra.size());
        VBox diagrama = createVBox(rutaDijkstra);
        dijkstraBox.setOnMouseClicked(ev->
            refreshScrollPane(diagrama)
        );
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
        Data.getInstance().getGrafo().bfs(txtActor1.getText());
        Deque<Edge<String>> rutaBfs = Data.getInstance().getGrafo().caminoMinimo(txtActor1.getText(), txtActor2.getText());
        Label numActores2=new Label("Cantidad: "+rutaBfs.size());
        VBox diagrama = createVBox(rutaBfs);
        bfsBox.setOnMouseClicked(ev->
            refreshScrollPane(diagrama)
        );
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
        Data.getInstance().getGrafo().dfs(txtActor1.getText());
        Deque<Edge<String>> rutaDfs = Data.getInstance().getGrafo().caminoMinimo(txtActor1.getText(), txtActor2.getText());
        Label numActores3=new Label("Cantidad: "+rutaDfs.size());
        VBox diagrama = createVBox(rutaDfs);
        dfsBox.setOnMouseClicked(ev->
            refreshScrollPane(diagrama)
        );
        Long timeEnd3=System.currentTimeMillis();
        Long timeFinal3=timeEnd3-timeStart3;
        Label time3=new Label("Tiempo: "+timeFinal3);
        dfsBox.getChildren().addAll(numActores3,time3);
    }
    
    private VBox createVBox(Deque<Edge<String>> ruta){
        VBox diagrama = new VBox();
        diagrama.setAlignment(Pos.TOP_CENTER);
        diagrama.setPrefWidth(400);
        diagrama.setPrefHeight(300);
        while(ruta.size()>1){
            Edge<String> arco = ruta.pop();
            diagrama.getChildren().addAll(new StackActor(arco.getVDestino().getData()), mostrarLinea(),
                    new StackMovie(arco.getPelicula()), mostrarLinea());
        }
        Edge<String> arco = ruta.pop();
        diagrama.getChildren().addAll(new StackActor(arco.getVDestino().getData()), mostrarLinea(),
                    new StackMovie(arco.getPelicula()), mostrarLinea(), new StackActor(arco.getVOrigen().getData()));
        return diagrama;
    }
    
    private void refreshScrollPane(VBox diagrama){
        if(scrollPane.getContent()!=null)
            scrollPane.setContent(null);
        scrollPane.setContent(diagrama);
    }
    
    private Line mostrarLinea(){
        return new Line(50,0,50,30);
    }
    
}
