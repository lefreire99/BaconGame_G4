/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.util;

import ec.edu.espol.bacongameg4.PrimaryController;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.json.*;

/**
 *
 * @author lfrei
 */
public class Util {
    public static GraphMAP<String> grafo;
    static int j;
    static long startTime;
    static ExecutorService ex = Executors.newSingleThreadExecutor();

    public static void leerArchivo(){
         grafo = new GraphMAP();
        try(FileReader fr = new FileReader(new File("src/archivos/data.txt"));
                BufferedReader br = new BufferedReader(fr)){

            int  i = 0;
            startTime = System.nanoTime();
            String line;
            while((line=br.readLine())!=null){
                    parseAndAdd(grafo,line);
                
                if (++i%10000 == 0){
                    long estimatedTime = System.nanoTime() - startTime;
                    System.out.println(((double) estimatedTime) / 1E9);
                }
            }
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private static void parseAndAdd(GraphMAP<String> graph, String line){
        ex.submit(() -> {
            try{
        
                JSONObject obj = new JSONObject(line);
            String pelicula = obj.getString("title");
            JSONArray actores = obj.getJSONArray("cast");
            LinkedList<String> parsedActores = parseJSONArray(actores);
            if(!actores.isEmpty()){
                ponerVertices(graph,parsedActores);
                ponerEdges(graph,parsedActores,pelicula);
            }
            if (++j%10000 == 0){
                    long estimatedTime = System.nanoTime() - startTime;
                    System.out.println(((double) estimatedTime) / 1E9);
                    System.out.println(grafo.vertexes.size());
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });
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
