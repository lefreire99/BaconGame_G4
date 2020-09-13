/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.util;

import javafx.scene.control.Alert;

/**
 *
 * @author lfrei
 */
public class DialogMessage {
    private DialogMessage(){   
    }
    
    public static void nullAlert(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Llenar campos de texto");
        alert.setContentText("Campos de texto vacios, por favor llenar.");
        alert.showAndWait();
    }
}
