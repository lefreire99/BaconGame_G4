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
    public static GraphLA<String> leerArchivo(){
        GraphLA<String> grafo=new GraphLA(false);
        FileReader fr= null;
        try {
            fr = new FileReader(new File("src/archivo/data.txt"));
            BufferedReader br=new BufferedReader(fr);
            String line;
            while((line=br.readLine())!=null){
                String[] datos=line.split(":");
                String pelicula=datos[1].split(",")[0];
                String[] actores=datos[2].replaceAll("\\[", "").replaceAll("\\]", "").split(",");
                List<String> actors=new LinkedList<>();
                if(actores.length!=1){
                    agregarLista(actors,actores);
                    ponerVertices(grafo,actors);
                    ponerEdges(grafo,actors);
                }
                System.out.println(grafo);
            }
            return grafo;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
    
    public static void ponerVertices(GraphLA<String> grafo,List<String> lista){
        for(String s:lista){
            grafo.addVertex(s);
        }
    }
    
    public static void ponerEdges(GraphLA<String> grafo,List<String> lista){
        for(String origen:lista){
            for(String destino:lista){
                grafo.addEdge(origen, destino, 1);
            }
        }
    }
    
    public static void agregarLista(List<String> lista, String[]datos){
        for(int i=0;i<datos.length-1;i++){
                lista.add(datos[i]);
            }
    }
    
}
