/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.util;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author eduardo
 */
public class GraphLA <E>{
    private LinkedList<Vertex<E>> vertexes;
    private boolean directed;
    
    public GraphLA(boolean directed){
        this.directed = directed;
        vertexes = new LinkedList<>();
    }
    
    public boolean addVertex(E data){
        Vertex<E> v = new Vertex<>(data);
        return (data == null || vertexes.contains(v))?false:vertexes.add(v);
    }
    
    public boolean removeVertex(E data){
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
    }
    
    public boolean addEdge(E src, E dst, int peso){
        if(src == null || dst == null) return false;
        Vertex<E> vs = searchVertex(src);
        Vertex<E> vd = searchVertex(dst);
        if(vs == null || vd == null) return false;
        Edge<E> e = new Edge<>(peso,vs,vd);
        if(!vs.getEdges().contains(e))
            vs.getEdges().add(e);
        if(!directed){
            Edge<E> ei = new Edge<>(peso,vd,vs);
            if(!vd.getEdges().contains(ei))
                vd.getEdges().add(ei);
        }
        return true;
    }
    
    public boolean removeEdge(E src, E dst){
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
    }
    
    private Vertex<E> searchVertex(E data){
        for(Vertex<E> v : vertexes)
        {
            if(v.getData().equals(data))
                return v;
        }
        return null;
    }
    
    public List<E> bfs(E data){
        List<E> result=new LinkedList<>();
        if(data==null) return result;
        Vertex<E> v=searchVertex(data);
        if(v==null)return result;
        Queue<Vertex<E>> cola=new LinkedList<>();
        v.setVisited(true);
        cola.offer(v);
        while(!cola.isEmpty()){
            v=cola.poll();
            result.add(v.getData());
            for(Edge<E> e:v.getEdges()){
                if(!e.getVDestino().isVisited()){
                    e.getVDestino().setVisited(true);
                    cola.offer(e.getVDestino());
                }    
            }
        }
        cleanVertexes();
        return result;
    }
    
    public List<E> dfs(E data){
        List<E> result=new LinkedList<>();
        if(data==null) return result;
        Vertex<E> v=searchVertex(data);
        if(v==null)return result;
        Deque<Vertex<E>> pila=new LinkedList<>();
        v.setVisited(true);
        pila.push(v);
        while(!pila.isEmpty()){
            v=pila.pop();
            result.add(v.getData());
            for(Edge<E> e:v.getEdges()){
                if(!e.getVDestino().isVisited()){
                    e.getVDestino().setVisited(true);
                    pila.push(e.getVDestino());
                }    
            }
        }
        cleanVertexes();
        return result;
    }
    
    /*private void cleanVertexes(){
        for(Vertex<E> v:vertexes){
            v.setVisited(false);
            
        }
    }*/
    
    public int inDegree(E data){
        if(data==null)return 0;
        ListIterator<Vertex<E>> iv = vertexes.listIterator();
        int cont=0;
        while(iv.hasNext()){
            Vertex<E> v = iv.next();
            ListIterator<Edge<E>> ie = v.getEdges().listIterator();
            while(ie.hasNext()){
                Edge<E> e = ie.next();
                if(e.getVDestino().getData().equals(data))
                    cont++;
            }
        }
        return cont;
    }
    
    public int outDegree(E data){
        if (data==null) return 0;
        Vertex v=searchVertex(data);
        if (v!=null){
            return v.getEdges().size();
        }else return 0;
    }

    @Override
    public String toString() {
        if(vertexes.isEmpty())return "[]\n[]";
        StringBuilder sbe=new StringBuilder();
        sbe.append("[");
        for(Vertex<E> v:vertexes){
            for(Edge<E> e:v.getEdges()){
                sbe.append("(").append(e.getVOrigen().getData()).append(",").append(e.getVDestino().getData()).append(",").append(e.getPeso()).append(")");
            }
        }
        sbe.append("]");
        return vertexes+"\n"+sbe.toString();
    }
    
    public boolean isConnected(){
        if(!directed)
            return isConnectedNoDirected();
        return isConnectedDirected();
        
    }
    
    private boolean isConnectedNoDirected(){
        Set<E> result=new HashSet<>();
        result.addAll(bfs(vertexes.getFirst().getData()));
        Set<E> original=new HashSet<>();
        for(Vertex<E> v:vertexes){
            original.add(v.getData());
        }
        original.removeAll(result);
        return original.isEmpty();
    }
    
    private boolean isConnectedDirected(){
        Set<E> original=new HashSet<>();
        for(Vertex<E> v:vertexes){
            original.add(v.getData());
        }
        GraphLA<E> reverso= reverse();
        E data=reverso.vertexes.getFirst().getData();
        Set<E> result=new HashSet<>();
        result.addAll(reverso.bfs(data));
        original.removeAll(result);
        return original.isEmpty();
    }
    
    public GraphLA<E> reverse(){
        if(!directed)
            return this;
        GraphLA<E> reverso=new GraphLA<>(true);
        for(Vertex<E> v:vertexes){
            reverso.addVertex(v.getData());
        }
        for(Vertex<E> v:vertexes){
            for(Edge<E> e:v.getEdges()){
                reverso.addEdge(e.getVDestino().getData(), e.getVOrigen().getData(), e.getPeso());
            }
        }
        return reverso;
    }
    
    public List<Set<E>> connectedComponents(){
        List<Set<E>> components=new LinkedList<>();
        if(isConnected()){
            Set<E> set=new HashSet<>();
            for(Vertex<E> v:vertexes){
                set.add(v.getData());
            }
            components.add(set);
        }else{
            if(!directed){
                connectedComponentsNotDirected(components);
            }else{
                connectedComponentsDirected(components);
            }
        }
        return components;
        
    }
    
    private void connectedComponentsNotDirected(List<Set<E>> c){
        ArrayList<Vertex<E>>av=new ArrayList<>();
        while(true){
            Vertex<E> v=vertexNotVisited(av);
            av.add(v);
            if(v!=null){
                Set<E> set=new HashSet<>();
                set.addAll(bfs(v.getData()));
                c.add(set);
            }else break;
        }
        cleanVertexes();
    }
    
    private void connectedComponentsDirected(List<Set<E>> c){
        GraphLA<E> reverso=reverse();
        ArrayList<Vertex<E>>av=new ArrayList<>();
        while(true){
            Vertex<E> v=vertexNotVisited(av);
            av.add(v);
            if(v!=null){
                Vertex<E> vr=reverso.searchVertex(v.getData());
                Set<E> setreverse=new HashSet<>();
                setreverse.addAll(reverso.bfs(vr.getData()));
                Set<E> setoriginal=new HashSet<>();
                setoriginal.addAll(bfs(v.getData()));
                if(setoriginal.containsAll(setreverse) && setreverse.containsAll(setoriginal)){
                    c.add(setreverse);
                }
            }else break;
        }
        cleanVertexes();
        reverso.cleanVertexes();
    }
    
    private Vertex<E> vertexNotVisited(ArrayList<Vertex<E>> av){
        for(Vertex<E> v:vertexes){
            if(!v.isVisited() && !av.contains(v))
                return v;
        }
        return null;
    }
    
    public GraphLA<E> unirGrafos(GraphLA<E> g1){
        if(g1.directed || this.directed) return null;
        GraphLA<E> resultante=new GraphLA<>(false);
        PriorityQueue<Edge<E>> edgepq= new PriorityQueue<>((Edge<E> e1,Edge<E> e2)-> e1.getPeso()-e2.getPeso());
        for(Vertex<E> v: vertexes){
            for(Edge<E> e:v.getEdges()){
                edgepq.offer(e);
            }
        }
        for(Vertex<E> v: g1.vertexes){
            for(Edge<E> e:v.getEdges()){
                edgepq.offer(e);
            }
        }
        while(!edgepq.isEmpty()){
            Edge<E> e=edgepq.poll();
            E origen=e.getVOrigen().getData();
            E destino=e.getVDestino().getData();
            int peso=e.getPeso();
            resultante.addVertex(origen);
            resultante.addVertex(destino);
            resultante.addEdge(origen, destino, peso);
        }
        return resultante;
    }
    
    public GraphLA prim(){
        if(!this.isConnected() || directed) return null;   
        GraphLA<E> grafoprim=new GraphLA<>(false);
        Vertex<E> v=vertexes.getFirst();
        PriorityQueue<Edge<E>> cola=new PriorityQueue<>((Edge<E> e1,Edge<E> e2)->e1.getPeso()-e2.getPeso());
        cola.addAll(v.getEdges());
        grafoprim.addVertex(v.getData());
        v.setVisited(true);
        while(!cola.isEmpty()){
            Edge<E> e=cola.poll();
            grafoprim.addVertex(e.getVDestino().getData());
            if(!e.getVDestino().isVisited()){
                grafoprim.addEdge(e.getVOrigen().getData(),e.getVDestino().getData(),e.getPeso());
            }
            e.getVDestino().setVisited(true);
            for(Edge<E> evd:e.getVDestino().getEdges()){
                if(!evd.getVDestino().isVisited()){
                    cola.offer(evd);
                }
            }
        }
        cleanVertexes();
        return grafoprim;
    }
    
    public GraphLA kruskal(){
        if(directed||!this.isConnected()) return null;
        GraphLA<E> grafokruskal=new GraphLA<>(false);
        PriorityQueue<Edge<E>> cola=new PriorityQueue<>((Edge<E> e1,Edge<E> e2)->e1.getPeso()-e2.getPeso());
        for(Vertex<E> v:vertexes){
            grafokruskal.addVertex(v.getData());
            for(Edge<E> e:v.getEdges()){
                cola.offer(e);
            }
        }
        while(!grafokruskal.isConnected()){
            Edge<E> e=cola.poll();
            List<Set<E>> componentes=grafokruskal.connectedComponents();
            for(Set<E> s:componentes){
                if(s.contains(e.getVOrigen().getData()) && !s.contains(e.getVDestino().getData())){
                    grafokruskal.addEdge(e.getVOrigen().getData(),e.getVDestino().getData(),e.getPeso());
                }
            }
        }
        return grafokruskal;
    }
    
    private void dijkstra(E inicio){
        Vertex<E> v=searchVertex(inicio);
        PriorityQueue<Vertex<E>> cola=new PriorityQueue<>((Vertex<E> v1,Vertex<E> v2)->v1.getDistancia()-v2.getDistancia());
        v.setDistancia(0);
        cola.offer(v);
        while(!cola.isEmpty()){
            v=cola.poll();
            v.setVisited(true);
            for(Edge<E> e:v.getEdges()){
                if(!e.getVDestino().isVisited()){
                    if(v.getDistancia()+e.getPeso()<e.getVDestino().getDistancia())
                    e.getVDestino().setDistancia(e.getPeso()+v.getDistancia());
                    e.getVDestino().setAntecesor(v);
                    cola.offer(e.getVDestino());
                }
            }
        }    
    }
    
    public int menorDistancia(E inicio, E fin){
        if(inicio== null || fin==null) return -1;
        if(inicio.equals(fin)) return 0;
        dijkstra(inicio);
        int distancia=searchVertex(fin).getDistancia();
        cleanVertexes();
        return distancia;
    }

    public List<E> caminoMinimo(E inicio, E fin){
        if(inicio== null || fin==null) return null;
        if(inicio.equals(fin)) return null;
        List<E> camino=new LinkedList<>();
        dijkstra(inicio);
        Vertex<E> v=searchVertex(fin);
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
        for(Vertex<E> v:vertexes){
            v.setVisited(false);
            v.setDistancia(Integer.MAX_VALUE);
            v.setAntecesor(null);
        }
    }
    
}
