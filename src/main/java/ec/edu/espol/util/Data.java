/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.util;

/**
 *
 * @author lfrei
 */
public class Data {
    private static final Data instance=new Data();
    private final GraphMAP<String> grafo=new GraphMAP<>();
    private Data(){
        
    }
    public static Data getInstance(){
        return instance;
    }
    
    public GraphMAP<String> getGrafo(){
        return grafo;
    }
}
