package br.com.teste.xofome.xofomeadmin.model;

/**
 * Created by marcosf on 19/11/2016.
 */

public class Produto {
    private int idProduto;
    private String nomeProduto;
    private Float preco;
    private String descricao;
    private int tipo;
    //private byte [] imagem;
    private String imagem;

    public Produto(String nome, Float preco, String descricao, int tipo, String imagem) {
        this.nomeProduto = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.tipo = tipo;
        this.imagem = imagem;
    }

    public Produto() {
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public Float getPreco() {
        return preco;
    }

    public void setPreco(Float preco) {
        this.preco = preco;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getImagem() {
        return imagem;
    }
    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}