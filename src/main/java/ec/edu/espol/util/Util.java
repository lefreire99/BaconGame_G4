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
import java.util.LinkedList;
import org.json.*;

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
            int i = 0;
            while((line=br.readLine())!=null){
                //Hay registros con : en el titulo, revisar
                JSONObject obj = new JSONObject(line);
                String pelicula = obj.getString("title");
                JSONArray actores = obj.getJSONArray("cast");
                LinkedList<String> parsedActores = parseJSONArray(actores);
                if(!actores.isEmpty()){
                    ponerVertices(grafo,parsedActores);
                    ponerEdges(grafo,parsedActores,pelicula);
                    
                }
                i++;
                if (i%1000 == 0){
                    System.out.println(i);
                }
            }
            return grafo;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    private static LinkedList<String> parseJSONArray(JSONArray actores){
        LinkedList<String> parsed = new LinkedList<>();
        for (Object s: actores){
            String p = (String) s;
            parsed.add(p.replaceAll(".*\\||\\[|\\]", ""));
        }
        return parsed;
    }
    private static void ponerVertices(GraphMAP<String> grafo, LinkedList<String> datos){
        for(String s: datos){
            grafo.addVertex(s);
        }
    }
    
    private static void ponerEdges(GraphMAP<String> grafo, LinkedList<String> datos,String pelicula){
        for(String origen: datos){
            for(String destino: datos){
                grafo.addEdge(origen, destino,pelicula, 1);
            }
        }
    }
}
