package ec.edu.espol.util;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author eduardo
 */
public class Vertex <E>{
    private E data;
    private ConcurrentLinkedQueue<Edge<E>> edges;
    private boolean visited;
    private int distancia;
    private Vertex<E> antecesor;

    public Vertex(E data) {
        this.data = data;
        edges = new ConcurrentLinkedQueue<>();
        distancia=Integer.MAX_VALUE;
    }
    
    public Vertex<E> getAntecesor() {
        return antecesor;
    }

    public void setAntecesor(Vertex<E> antecesor) {
        this.antecesor = antecesor;
    }
    
    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distance) {
        this.distancia = distance;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    
    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public Queue<Edge<E>> getEdges() {
        return edges;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vertex<E> other = (Vertex<E>) obj;
        return Objects.equals(this.data, other.data);   
    }

    @Override
    public int hashCode() {
        return 59 * 3 + Objects.hashCode(this.data);
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append(data);
        return sb.toString();
    }
    
}
