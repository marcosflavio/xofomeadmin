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
    private String endereco;
    private double valorASerPago = 0.0;
    private Usuario usuario;

    public Pedido() {
        // this.itensPedido = new ArrayList<ItemPedido>();
        idPedido++;
    }

//    public List<ItemPedido> getItensPedido() {
//        return itensPedido;
//    }
//
//    public void setItensPedido(List<ItemPedido> itensPedido) {
//        this.itensPedido = itensPedido;
//    }

    public double getValorASerPago() {
        return valorASerPago;
    }

    public void setValorASerPago(double valorASerPago) {
        this.valorASerPago = valorASerPago;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
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

        double valorTotal =0;

//        for(ItemPedido i: itensPedido){
//            valorTotal += i.getValor();
//        }
//        valorTotalPedido = valorTotal;
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
