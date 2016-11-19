package br.com.teste.xofome.xofomeadmin.service;

import android.content.Context;

import java.io.IOException;
import java.util.List;

import br.com.teste.xofome.xofomeadmin.db.ProdutoDB;
import br.com.teste.xofome.xofomeadmin.model.Produto;

/**
 * Created by marcosf on 19/11/2016.
 */

public class ProdutoService {

    public static Produto getProdutoById(Context context, int id) {
        ProdutoDB dao = new ProdutoDB(context);

        try {
            Produto produto = dao.find(id);

            return produto;
        } finally {
            dao.close();
        }
    }

    public static List<Produto> getProdutos(Context context, int tipo) throws IOException {
        ProdutoDB dao = new ProdutoDB(context);
        try {
            List<Produto> produtos = dao.findAllTipo(tipo);
            return produtos;
        } finally {
            dao.close();
        }
    }

    public static Produto getProduto(Context context, int id) throws IOException {
        ProdutoDB dao = new ProdutoDB(context);
        try {
            Produto produto = dao.findById(id);
            return produto;
        } finally {
            dao.close();
        }
    }

    public static void save(Context context, Produto produto) {
        ProdutoDB dao = new ProdutoDB(context);

        try {
            dao.save(produto);
        } finally {
            dao.close();
        }
    }

    public static void delete(Context context, Produto produto) {
        ProdutoDB dao = new ProdutoDB(context);
        try {
            dao.delete(produto);
        } finally {
            dao.close();
        }
    }

    public static Produto formarProduto(int tipo, float preco, String nome, String desc) {
        Produto produto = new Produto();

        produto.setTipo(tipo);
        produto.setPreco(preco);
        produto.setNomeProduto(nome);
        produto.setDescricao(desc);

        return produto;
    }

    public static void setListProdutos(List<Produto> produtos, Context context) {

        for (int i = 0; i < produtos.size(); i++) {
            save(context, produtos.get(i));
        }

    }
}