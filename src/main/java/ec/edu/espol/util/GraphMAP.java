/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.util;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author eduardo
 */
public class GraphMAP<E>{
    private Map<E,Vertex<E>> vertexes;
    private boolean directed;
    
    public GraphMAP(boolean directed){
        this.directed = directed;
        vertexes = new HashMap<>();
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
    
    /*public boolean removeVertex(E data){
        if(data == null || vertexes.isEmpty()) return false;
        ListIterator<Vertex<E>> iv = vertexes.listIterator();
        while(iv.hasNext()){
            Vertex<E> v = iv.next();
            ListIterator<Edge<E>> ie = v.getEdges().listIterator();
            while(ie.hasNext()){
                Edge<E> e = ie.next();
                if(e.getVDestino().getData().equals(data))
                    ie.remove();
            }
        }
        Vertex<E> vi = new Vertex<>(data);
        return vertexes.remove(vi);
    }*/
    
    public boolean addEdge(E src, E dst, String pelicula,int peso){
        if(src == null || dst == null) return false;
        Vertex<E> vs = vertexes.get(src);
        Vertex<E> vd = vertexes.get(dst);
        if(vs == null || vd == null) return false;
        Edge<E> e = new Edge<>(peso,vs,vd,pelicula);
        if(!vs.getEdges().contains(e))
            vs.getEdges().add(e);
        if(!directed){
            Edge<E> ei = new Edge<>(peso,vd,vs,pelicula);
            if(!vd.getEdges().contains(ei))
                vd.getEdges().add(ei);
        }
        return true;
    }
    
    /*public boolean removeEdge(E src, E dst){
        if(src == null || dst == null) return false;
        Vertex<E> vs = searchVertex(src);
        Vertex<E> vd = searchVertex(dst);
        if(vs == null || vd == null) return false;
        Edge<E> e = new Edge<>(0,vs,vd);
        vs.getEdges().remove(e);
        if(!directed){
            e = new Edge<>(0,vd,vs);
            vd.getEdges().remove(e);
        }
        return true;
    }*/
    
    private Vertex<E> searchVertex(E data){
        return vertexes.get(data);
    }
    
    public void bfs(E data){
        //List<E> result=new LinkedList<>();
        if(data!=null){
            Vertex<E> v=vertexes.get(data);
            if(v!=null){
                Queue<Vertex<E>> cola=new LinkedList<>();
                v.setVisited(true);
                cola.offer(v);
                while(!cola.isEmpty()){
                    v=cola.poll();
                    //result.add(v.getData());
                    for(Edge<E> e:v.getEdges()){
                        if(!e.getVDestino().isVisited()){
                            e.getVDestino().setVisited(true);
                            e.getVDestino().setAntecesor(v);
                            cola.offer(e.getVDestino());
                        }    
                    }
                }
            }
        //cleanVertexes();
        //return result;
        }
    }
    
    public void dfs(E data){
        //List<E> result=new LinkedList<>();
        if(data!=null){
            Vertex<E> v=vertexes.get(data);
            if(v!=null){
                Deque<Vertex<E>> pila=new LinkedList<>();
                v.setVisited(true);
                pila.push(v);
                while(!pila.isEmpty()){
                    v=pila.pop();
                    //result.add(v.getData());
                    for(Edge<E> e:v.getEdges()){
                        if(!e.getVDestino().isVisited()){
                            e.getVDestino().setVisited(true);
                            e.getVDestino().setAntecesor(v);
                            pila.push(e.getVDestino());
                        }    
                    }
                }
            }
            //cleanVertexes();
            //return result;
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
    
    private void dijkstra(E inicio){
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

    public List<E> caminoMinimo(E inicio, E fin){
        if(inicio== null || fin==null) return null;
        if(inicio.equals(fin)) return null;
        List<E> camino=new LinkedList<>();
        dijkstra(inicio);
        Vertex<E> v=vertexes.get(fin);
        Deque<Vertex<E>> cola=new LinkedList<>();
        cola.offer(v);
        while(!cola.isEmpty()){
            v=cola.poll();
            if(v.getAntecesor()!=null){
                camino.add(v.getAntecesor().getData());
                cola.offer(v.getAntecesor());
            }
        }
        cleanVertexes();
        return camino;
    }
    
    private void cleanVertexes(){
        for(Vertex<E> v:vertexes.values()){
            v.setVisited(false);
            v.setDistancia(Integer.MAX_VALUE);
            v.setAntecesor(null);
        }
    }
    
}
