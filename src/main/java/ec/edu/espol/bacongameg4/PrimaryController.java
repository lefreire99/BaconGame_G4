package ec.edu.espol.bacongameg4;

import ec.edu.espol.util.Edge;
import ec.edu.espol.util.DialogMessage;
import ec.edu.espol.util.StackActor;
import ec.edu.espol.util.StackMovie;
import ec.edu.espol.util.Util;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Deque;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

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
        System.out.println(Util.grafo.vertexes.size());
        if(txtActor1.getText().isEmpty()|| txtActor2.getText().isEmpty()){
            DialogMessage.nullAlert();
        }else{
            if(scrollPane.getContent()!=null)
                scrollPane.setContent(null);
            if(Util.grafo.hasActor(txtActor1.getText()) && Util.grafo.hasActor(txtActor2.getText())){
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
            Util.grafo.dijkstra(txtActor1.getText());
            Deque<Edge<String>> rutaDijkstra = Util.grafo.caminoMinimo(txtActor1.getText(), txtActor2.getText());
            Label numActores1=new Label("Cantidad: "+rutaDijkstra.size());
            VBox diagrama = createVBox();
            while(!rutaDijkstra.isEmpty()){
                Edge<String> arco = rutaDijkstra.pop();
                diagrama.getChildren().addAll(new StackActor(arco.getVDestino().getData()),mostrarImagen(),new StackMovie(arco.getPelicula()),mostrarImagen());
                System.out.println(arco.getVDestino());
                System.out.println(arco.getPelicula()); //Se podria invertir el arco
                System.out.println(arco.getVOrigen());
                System.out.println("-------------------");
            }
            diagrama.getChildren().remove(diagrama.getChildren().size()-1);
            dijkstraBox.setOnMouseClicked((ev)->{
                refreshScrollPane(diagrama);
            });
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
            VBox diagrama = createVBox();
            while(!rutaBfs.isEmpty()){
                Edge<String> arco = rutaBfs.pop();
                diagrama.getChildren().addAll(new StackActor(arco.getVDestino().getData()),mostrarImagen(),new StackMovie(arco.getPelicula()),mostrarImagen());
                System.out.println(arco.getVDestino());
                System.out.println(arco.getPelicula()); //Se podria invertir el arco
                System.out.println(arco.getVOrigen());
                System.out.println("-------------------");
            }
            diagrama.getChildren().remove(diagrama.getChildren().size()-1);
            bfsBox.setOnMouseClicked((ev)->{
                refreshScrollPane(diagrama);
            });
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
            VBox diagrama = createVBox();
            while(!rutaDfs.isEmpty()){
                Edge<String> arco = rutaDfs.pop();
                diagrama.getChildren().addAll(new StackActor(arco.getVDestino().getData()),mostrarImagen(),new StackMovie(arco.getPelicula()),mostrarImagen());
                System.out.println(arco.getVDestino());
                System.out.println(arco.getPelicula()); //Se podria invertir el arco
                System.out.println(arco.getVOrigen());
                System.out.println("-------------------");
            }
            diagrama.getChildren().remove(diagrama.getChildren().size()-1);
            dfsBox.setOnMouseClicked((ev)->{
                refreshScrollPane(diagrama);
            });
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
            diagrama.getChildren().addAll(
                    new StackActor(arco.getVDestino().getData()),new StackMovie(arco.getPelicula()));
            /*System.out.println(arco.getVDestino());
            System.out.println(arco.getPelicula()); //Se podria invertir el arco
            System.out.println(arco.getVOrigen());
            System.out.println("-------------------");*/
        }
        diagrama.getChildren().add(new StackActor(ruta.pop().getVDestino().getData()));
        return diagrama;
    }
    
    private void refreshScrollPane(VBox diagrama){
        if(scrollPane.getContent()!=null)
            scrollPane.setContent(null);
        scrollPane.setContent(diagrama);
    }
    
    private ImageView mostrarImagen(){
        File file = new File("src/archivos/arrow.png");
        Image image = new Image(file.toURI().toString());
        ImageView imageview = new ImageView(image);
        imageview.setFitHeight(50);imageview.setFitWidth(50);
        return imageview;
    }
    
}
