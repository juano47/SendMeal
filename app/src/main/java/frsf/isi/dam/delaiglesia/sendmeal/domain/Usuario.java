package frsf.isi.dam.delaiglesia.sendmeal.domain;

import java.util.Objects;

public class Usuario {
    private Integer id;
    private String nombre;
    private String mail;
    private String clave;
    private Boolean notificarMail;
    private Double credito;
    private CuentaBancaria cuentaBancaria;
    private TarjetaCredito tarjetaCredito;
    private TipoCuenta tipoCuenta;

    public Usuario(Integer id, String nombre, String mail, String clave, Boolean notificarMail, Double credito, CuentaBancaria cuentaBancaria, TarjetaCredito tarjetaCredito, TipoCuenta tipoCuenta) {
        this.id = id;
        this.nombre = nombre;
        this.mail = mail;
        this.clave = clave;
        this.notificarMail = notificarMail;
        this.credito = credito;
        this.cuentaBancaria = cuentaBancaria;
        this.tarjetaCredito = tarjetaCredito;
        this.tipoCuenta = tipoCuenta;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Boolean getNotificarMail() {
        return notificarMail;
    }

    public void setNotificarMail(Boolean notificarMail) {
        this.notificarMail = notificarMail;
    }

    public Double getCredito() {
        return credito;
    }

    public void setCredito(Double credito) {
        this.credito = credito;
    }

    public CuentaBancaria getCuentaBancaria() {
        return cuentaBancaria;
    }

    public void setCuentaBancaria(CuentaBancaria cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }

    public TarjetaCredito getTarjetaCredito() {
        return tarjetaCredito;
    }

    public void setTarjetaCredito(TarjetaCredito tarjetaCredito) {
        this.tarjetaCredito = tarjetaCredito;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) &&
                Objects.equals(nombre, usuario.nombre) &&
                Objects.equals(mail, usuario.mail) &&
                Objects.equals(clave, usuario.clave) &&
                Objects.equals(notificarMail, usuario.notificarMail) &&
                Objects.equals(credito, usuario.credito) &&
                Objects.equals(cuentaBancaria, usuario.cuentaBancaria) &&
                Objects.equals(tarjetaCredito, usuario.tarjetaCredito) &&
                tipoCuenta == usuario.tipoCuenta;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, mail, clave, notificarMail, credito, cuentaBancaria, tarjetaCredito, tipoCuenta);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", mail='" + mail + '\'' +
                ", clave='" + clave + '\'' +
                ", notificarMail=" + notificarMail +
                ", credito=" + credito +
                ", cuentaBancaria=" + cuentaBancaria +
                ", tarjetaCredito=" + tarjetaCredito +
                ", tipoCuenta=" + tipoCuenta +
                '}';
    }
}
