package frsf.isi.dam.delaiglesia.sendmeal.domain;

import java.util.Objects;

public class TarjetaCredito {
    private Integer id;
    private String numero;
    private Integer digitoVerificacion;
    private String vencimiento;

    public TarjetaCredito(Integer id, String numero, Integer digitoVerificacion, String vencimiento) {
        this.id = id;
        this.numero = numero;
        this.digitoVerificacion = digitoVerificacion;
        this.vencimiento = vencimiento;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Integer getDigitoVerificacion() {
        return digitoVerificacion;
    }

    public void setDigitoVerificacion(Integer digitoVerificacion) {
        this.digitoVerificacion = digitoVerificacion;
    }

    public String getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(String vencimiento) {
        this.vencimiento = vencimiento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TarjetaCredito that = (TarjetaCredito) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(numero, that.numero) &&
                Objects.equals(digitoVerificacion, that.digitoVerificacion) &&
                Objects.equals(vencimiento, that.vencimiento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numero, digitoVerificacion, vencimiento);
    }



    @Override
    public String toString() {
        return "TarjetaCredito{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", digitoVerificacion=" + digitoVerificacion +
                ", vencimiento='" + vencimiento + '\'' +
                '}';
    }
}
