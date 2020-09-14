/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.bacongameg4;

import javafx.application.Platform;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 *
 * @author mbpretina
 */
public class LineChartOOB{
    
    private LineChart lc;
    private XYChart.Series<String, Number> bfsSerie = new XYChart.Series<>();
    private XYChart.Series<String, Number> dfsSerie = new XYChart.Series<>();
    private XYChart.Series<String, Number> dijkstraSerie = new XYChart.Series<>();
    private long startTime;
    private int seq;
    public LineChartOOB(){
        bfsSerie.setName("BFS");
        dfsSerie.setName("DFS");
        dijkstraSerie.setName("Dikjstra");
        
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time/s");
        xAxis.setAnimated(false);
        yAxis.setLabel("Value");
        yAxis.setAnimated(false);
        
        lc = new LineChart<>(xAxis, yAxis);
        lc.setTitle("Busquedas en tiempo real");
        lc.setAnimated(false);
        lc.getData().addAll(bfsSerie,dfsSerie,dijkstraSerie);
    }
    
    public void startTimerBFS(){
        startTime = System.nanoTime();
        bfsSerie.getData().add(new XYChart.Data<>(String.valueOf(startTime), 0));
    }
    public void startTimerDFS(){
        startTime = System.nanoTime();
        dfsSerie.getData().add(new XYChart.Data<>(String.valueOf(startTime), 0));
    }
    public void startTimerDijkstra(){
        startTime = System.nanoTime();
        dijkstraSerie.getData().add(new XYChart.Data<>(String.valueOf(startTime), 0));
    }
    
    public void sendBFS(int i){
        Platform.runLater(() -> {
                // get current time
                seq = seq + i;
                 bfsSerie.getData().add(new XYChart.Data<>(String.valueOf(System.nanoTime()), seq));
            });
        
    }
    public void sendDFS(int i){
        dfsSerie.getData().add(new XYChart.Data<>(String.valueOf(System.nanoTime()), i));
    }
    public void sendDijkstra(int i){
        dijkstraSerie.getData().add(new XYChart.Data<>(String.valueOf(System.nanoTime()), i));
    }

    public LineChart getLc() {
        return lc;
    }
    
}
