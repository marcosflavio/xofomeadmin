package br.com.teste.xofome.xofomeadmin.model;

/**
 * Created by Caio on 30/10/2016.
 */

public class ItemPedido {
    private int idItemPedido;
    private String nomeProduto;
    private int quantidade;
    private double valor;
    private Pedido pedido;
    private Produto produto;

    public ItemPedido(int idItemPedido, Pedido pedido, Produto produto, String nomeProduto, int quantidade, double valor) {
        this.idItemPedido = idItemPedido;
        this.pedido = pedido;
        this.produto = produto;
        this.nomeProduto = nomeProduto;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public ItemPedido(){

    }
    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getIdItemPedido() {
        return idItemPedido;
    }

    public void setIdItemPedido(int idItemPedido) {
        this.idItemPedido = idItemPedido;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemPedido that = (ItemPedido) o;

        if (getIdItemPedido() != that.getIdItemPedido()) return false;
        if (getPedido() != that.getPedido()) return false;
        if (getProduto() != that.getProduto()) return false;
        if (getQuantidade() != that.getQuantidade()) return false;
        return getNomeProduto().equals(that.getNomeProduto());

    }

    @Override
    public int hashCode() {
        int result = getIdItemPedido();
        result = 31 * result + getPedido().hashCode();
        result = 31 * result + getProduto().hashCode();
        result = 31 * result + getNomeProduto().hashCode();
        result = 31 * result + getQuantidade();
        return result;
    }
}