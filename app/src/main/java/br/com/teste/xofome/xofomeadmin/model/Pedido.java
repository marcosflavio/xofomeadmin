package br.com.teste.xofome.xofomeadmin.model;

import java.util.ArrayList;
import java.util.List;

import br.com.teste.xofome.xofomeadmin.model.Usuario;

/**
 * Created by marcosf on 02/11/2016.
 */

public class Pedido {

    private int idPedido;
    private String status = "Inativo";
    private double valorTotalPedido;
    private String latitude;
    private String longitude;
    private double valorASerPago;
    private Usuario usuario;

    public Pedido() {
        this.idPedido++;
        this.valorASerPago = 0.0d;
    }

    public double getValorASerPago() {
        return valorASerPago;
    }

    public void setValorASerPago(double valorASerPago) {
        this.valorASerPago = valorASerPago;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public double getValorTotalPedido() {
        return valorTotalPedido;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setValorTotalPedido(double valorTotalPedido) {
        this.valorTotalPedido = valorTotalPedido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pedido)) return false;

        Pedido pedido = (Pedido) o;

        return idPedido == pedido.idPedido;
    }

    @Override
    public int hashCode() {
        return idPedido;
    }
}