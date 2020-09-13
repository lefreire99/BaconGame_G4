/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.util;

import java.util.Objects;

/**
 *
 * @author eduardo
 */
public class Edge <E>{
    private int peso;
    private Vertex<E> vo;
    private Vertex<E> vd;
    private String pelicula;

    public Edge(int peso, Vertex<E> vo, Vertex<E> vd) {
        this.peso = peso;
        this.vo = vo;
        this.vd = vd;
    }
    
    public Edge(int peso, Vertex<E> vo, Vertex<E> vd,String pelicula) {
        this.peso = peso;
        this.vo = vo;
        this.vd = vd;
        this.pelicula=pelicula;
    }

    public String getPelicula() {
        return pelicula;
    }

    public void setPelicula(String pelicula) {
        this.pelicula = pelicula;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public Vertex<E> getVOrigen() {
        return vo;
    }

    public void setVOrigen(Vertex<E> vo) {
        this.vo = vo;
    }

    public Vertex<E> getVDestino() {
        return vd;
    }

    public void setVDestino(Vertex<E> vd) {
        this.vd = vd;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
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
        final Edge<E> other = (Edge<E>) obj;
        return Objects.equals(this.vo, other.vo) && Objects.equals(this.vd, other.vd);
    }
    
    
    
}
