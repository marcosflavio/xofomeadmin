package br.com.teste.xofome.xofomeadmin.service;

import android.content.Context;
import java.io.IOException;
import java.util.List;
import br.com.teste.xofome.xofomeadmin.dao.ProdutoDAO;
import br.com.teste.xofome.xofomeadmin.model.Produto;

/**
 * Created by marcosf on 19/11/2016.
 */

public class ProdutoService {

    public static Produto getProdutoById(Context context, int id) {
        ProdutoDAO dao = new ProdutoDAO(context);
        Produto produto = dao.find(id);
        if(produto!=null){
            return produto;
        }
        return null;
    }

    public static List<Produto> getProdutos(Context context, int tipo) throws IOException {
        ProdutoDAO dao = new ProdutoDAO(context);
            List<Produto> produtos = dao.findAllTipo(tipo);
            return produtos;
    }

    public static Produto getProduto(Context context, int id) throws IOException {
        ProdutoDAO dao = new ProdutoDAO(context);
            Produto produto = dao.findById(id);
            return produto;
    }

    public static void save(Context context, Produto produto) {
        ProdutoDAO dao = new ProdutoDAO(context);
            dao.save(produto);
    }

    public static void delete(Context context, Produto produto) {
        ProdutoDAO dao = new ProdutoDAO(context);
            dao.delete(produto);
    }

    public static Produto formarProduto(int tipo, float preco, String nome, String desc, byte [] imagem) {
        Produto produto = new Produto();
        produto.setTipo(tipo);
        produto.setPreco(preco);
        produto.setNomeProduto(nome);
        produto.setDescricao(desc);
        produto.setImagem(imagem);
        return produto;
    }

    public static void setListProdutos(List<Produto> produtos, Context context) {
        for (int i = 0; i < produtos.size(); i++) {
            save(context, produtos.get(i));
        }
    }
}