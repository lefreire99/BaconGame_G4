/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.util;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author DELL
 */
public class StackMovie extends StackPane {
    
    public StackMovie(String nombreMovie){
        Rectangle r = new Rectangle(200,50);
        r.setFill(Color.LIGHTBLUE);
        this.getChildren().addAll(r,new Label(nombreMovie));
    }
    
}
