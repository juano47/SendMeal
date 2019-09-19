package frsf.isi.dam.delaiglesia.sendmeal.domain;

import java.io.Serializable;

public class Plato implements Serializable {
    private Integer id;
    private String titulo;
    private String descripcion;
    private Double precio;
    private Integer calorias;

    public Plato(Integer id, String titulo, String descripcion, Double precio, Integer calorias) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.calorias = calorias;
    }

    public Plato() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getCalorias() {
        return calorias;
    }

    public void setCalorias(Integer calorias) {
        this.calorias = calorias;
    }
}
