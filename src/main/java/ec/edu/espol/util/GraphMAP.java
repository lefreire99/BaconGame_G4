/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.util;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author eduardo
 */
public class GraphMAP<E>{
    public Map<E,Vertex<E>> vertexes;
    
    public GraphMAP(){
        vertexes = new ConcurrentHashMap<>();
    }
    
    public boolean addVertex(E data){
        Vertex<E> v = new Vertex<>(data);
        if(data==null || vertexes.containsKey(data)){
            return false;
        }else{
            vertexes.put(data, v);
            return true;
        }
    }
    
    public boolean addEdge(E src, E dst, String pelicula,int peso){
        if(src == null || dst == null) return false;
        Vertex<E> vs = vertexes.get(src);
        Vertex<E> vd = vertexes.get(dst);
        if(vs == null || vd == null) return false;
        Edge<E> e = new Edge<>(peso,vs,vd,pelicula);
        if(!vs.getEdges().contains(e)){
            vs.getEdges().add(e);
            Edge<E> ei = new Edge<>(peso,vd,vs,pelicula);
            if(!vd.getEdges().contains(ei))
                vd.getEdges().add(ei);
        }
        return true;
    }
    
    private Vertex<E> searchVertex(E data){
        return vertexes.get(data);
    }
    
    public void bfs(E data){
        if(data!=null){
            Vertex<E> v = vertexes.get(data);
            if(v!=null){
                Queue<Vertex<E>> cola = new LinkedList<>();
                v.setVisited(true);
                cola.offer(v);
                while(!cola.isEmpty()){
                    v=cola.poll();
                    for(Edge<E> e:v.getEdges()){
                        if(!e.getVDestino().isVisited()){
                            e.getVDestino().setVisited(true);
                            e.getVDestino().setAntecesor(v);
                            cola.offer(e.getVDestino());
                        }    
                    }
                }
            }
        }
    }
    
    public void dfs(E data){
        if(data!=null){
            Vertex<E> v=vertexes.get(data);
            if(v!=null){
                Deque<Vertex<E>> pila=new LinkedList<>();
                v.setVisited(true);
                pila.push(v);
                while(!pila.isEmpty()){
                    v=pila.pop();
                    for(Edge<E> e:v.getEdges()){
                        if(!e.getVDestino().isVisited()){
                            e.getVDestino().setVisited(true);
                            e.getVDestino().setAntecesor(v);
                            pila.push(e.getVDestino());
                        }    
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        if(vertexes.isEmpty())return "[]\n[]";
        StringBuilder sbe=new StringBuilder();
        sbe.append("[");
        for(Vertex<E> v:vertexes.values()){
            for(Edge<E> e:v.getEdges()){
                sbe.append("(").append(e.getVOrigen().getData()).append(",").append(e.getVDestino().getData()).append(",").append(e.getPeso()).append(")");
            }
        }
        sbe.append("]");
        return vertexes+"\n"+sbe.toString();
    }
    
    public void dijkstra(E inicio){
        Vertex<E> v=vertexes.get(inicio);
        PriorityQueue<Vertex<E>> cola=new PriorityQueue<>((Vertex<E> v1,Vertex<E> v2)->v1.getDistancia()-v2.getDistancia());
        v.setDistancia(0);
        cola.offer(v);
        while(!cola.isEmpty()){
            v=cola.poll();
            v.setVisited(true);
            for(Edge<E> e:v.getEdges()){
                if(!e.getVDestino().isVisited()){
                    if(v.getDistancia()+e.getPeso()<e.getVDestino().getDistancia()){
                        e.getVDestino().setDistancia(e.getPeso()+v.getDistancia());
                        e.getVDestino().setAntecesor(v);
                        cola.offer(e.getVDestino());
                    }
                }
            }
        }    
    }
    
    public int menorDistancia(E inicio, E fin){
        if(inicio== null || fin==null) return -1;
        if(inicio.equals(fin)) return 0;
        dijkstra(inicio);
        int distancia=vertexes.get(fin).getDistancia();
        cleanVertexes();
        return distancia;
    }

    public Deque<Edge<E>> caminoMinimo(E inicio, E fin){
        if(inicio== null || fin==null) return null;
        if(inicio.equals(fin)) return null;
        Vertex<E> v = vertexes.get(fin);
        Deque<Edge<E>> pila = new LinkedList<>();
        //pila.push(v.getData());
        while(v.getAntecesor()!=null){
            for(Edge e: v.getEdges()){
                if(e.getVDestino().equals(v.getAntecesor()))
                    pila.push(e);
            }
            v = v.getAntecesor();
            //pila.push(v.getData());
        }
        cleanVertexes();
        return pila;
    }
    
    
    private void cleanVertexes(){
        for(Vertex<E> v:vertexes.values()){
            v.setVisited(false);
            v.setDistancia(Integer.MAX_VALUE);
            v.setAntecesor(null);
        }
    }
    
}
