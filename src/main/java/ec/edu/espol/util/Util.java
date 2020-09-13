/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author lfrei
 */
public class Util {
    
    public static GraphMAP<String> leerArchivo(){
        GraphMAP<String> grafo = new GraphMAP(false);
        try(FileReader fr = new FileReader(new File("src/archivos/data.txt"));
                BufferedReader br = new BufferedReader(fr)){
            String line;
            while((line=br.readLine())!=null){
                //Hay registros con : en el titulo, revisar
                String[] datos=line.split(":");
                String pelicula=datos[1].split(",")[0];
                //La palabra "directors" aun se encuentra en el arreglo actores
                //nombres con este formato revisar "Brad Johnson (television actor)|Brad Johnson"
                String[] actores=datos[2].replaceAll("\\[", "").replaceAll("\\]", "").split(",");
                if(actores.length!=1){
                    ponerVertices(grafo,actores);
                    ponerEdges(grafo,actores,pelicula);
                }
                System.out.println(actores[0]);
            }
            return grafo;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public static void ponerVertices(GraphMAP<String> grafo, String[] datos){
        for(String s: datos){
            grafo.addVertex(s);
        }
    }
    
    public static void ponerEdges(GraphMAP<String> grafo, String[] datos,String pelicula){
        int salteador = 1;
        for(int j = 0; j<datos.length-1; j++){
            for(int i = salteador; i<datos.length; i++){
                grafo.addEdge(datos[j], datos[i], pelicula, 1);
            }
            salteador++;
        }
        
        /*for(String origen: datos){
            for(String destino: datos){
                grafo.addEdge(origen, destino, 1);
            }
        }*/
    }
    
    /*public static void agregarLista(List<String> lista, String[] datos){
        for(int i=0;i<datos.length-1;i++){
            lista.add(datos[i]);
        }
    }*/
    
}
