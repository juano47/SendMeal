package frsf.isi.dam.delaiglesia.sendmeal.domain;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class Pedido implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private Date fecha;
    private Integer estado;
    private Double lat;
    private Double lng;
    @Ignore
    private List<ItemPedido> items;

    public Pedido(Integer id, Date fecha, Integer estado, Double lat, Double lng, List<ItemPedido> items) {
        this.id = id;
        this.fecha = fecha;
        this.estado = estado;
        this.lat = lat;
        this.lng = lng;
        this.items = items;
    }

    public Pedido() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public List<ItemPedido> getItems() {
        return items;
    }

    public void setItems(List<ItemPedido> items) {
        this.items = items;
    }
}
