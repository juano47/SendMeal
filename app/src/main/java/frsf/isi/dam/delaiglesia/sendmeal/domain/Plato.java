package frsf.isi.dam.delaiglesia.sendmeal.domain;

import android.widget.ImageView;

import java.io.Serializable;

public class Plato implements Serializable {
    private Integer id;
    private String titulo;
    private String descripcion;
    private Double precio;
    private Integer calorias;
    private boolean enOferta;
    private String foto;

    public Plato(Integer id, String titulo, String descripcion, Double precio, Integer calorias, boolean enOferta, String foto) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.calorias = calorias;
        this.enOferta = enOferta;
        this.foto = foto;
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

    public boolean getEnOferta(){return enOferta;}

    public void setEnOferta(boolean enOferta) {this.enOferta = enOferta;}

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFoto() {
        return  foto;
    }
}
