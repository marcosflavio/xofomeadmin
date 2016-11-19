package br.com.teste.xofome.xofomeadmin.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.teste.xofome.xofomeadmin.model.Produto;

/**
 * Created by marcosf on 19/11/2016.
 */

public class ProdutoDB extends SQLiteOpenHelper {
    private static final String TAG = "sql";
    public static final String NOME_BANCO = "xofome.admin.sqlite";
    private static final int VERSAO_BANCO = 1;

    public ProdutoDB(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists produto (id_produto integer primary key autoincrement , nome_produto text," +
                "descricao text,preco float, tipo integer);");
        Log.d(TAG, "Tabela produto criada com sucesso!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Caso a gnt update a versao do banco
    }

    /*Métodos CRUD
    * */

    public void save(Produto produto) {
        //Pego o id para verificar se o msm já foi inserido ou nao no banco
        Integer id = produto.getIdProduto();

        SQLiteDatabase db = getWritableDatabase();
        try {

            ContentValues values = new ContentValues();
            values.put("nome_produto", produto.getNomeProduto());
            values.put("descricao", produto.getDescricao());
            values.put("preco", produto.getPreco());
            values.put("tipo", produto.getTipo());
            //insiro o produto
            db.insert("produto", "", values);
        } finally {
            Log.d(TAG, "Produto" + produto.getNomeProduto() + " adicionado ao banco!");
            db.close();
        }

    }

    public void delete(Produto produto) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            db.delete("produto", "id_produto", new String[]{String.valueOf(produto.getIdProduto())});
            Log.i(TAG, "Deletou o produto" + produto.getNomeProduto());
        } finally {
            db.close();
        }
    }

    public Produto find(int id) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            Cursor c = db.query("produto", null, "id = '" + id + "'", null, null, null, null);
            return toProduto(c);
        } finally {
            db.close();
        }
    }

    //listar todos os produtos de acordo com seu tipo
    public List<Produto> findAllTipo(int tipo) {

        SQLiteDatabase db = getWritableDatabase();

        try {
            Cursor c = db.query("produto", null, "tipo = '" + tipo + "'", null, null, null, null);
            return toList(c);
        } finally {
            db.close();
        }
    }

    //retorna um determinado produto
    public Produto findById(int id) {

        SQLiteDatabase db = getWritableDatabase();
        try {
            Cursor c = db.query("produto", null, "id_produto = '" + id + "'", null, null, null, null);
            if (c.moveToFirst()) {
                Produto produto = new Produto();
                produto.setIdProduto(c.getInt(c.getColumnIndex("id_produto")));
                produto.setDescricao(c.getString(c.getColumnIndex("descricao")));
                produto.setNomeProduto(c.getString(c.getColumnIndex("nome_produto")));
                produto.setPreco(c.getFloat(c.getColumnIndex("preco")));
                produto.setTipo(c.getInt(c.getColumnIndex("tipo")));
                return produto;
            }

        } finally {
            db.close();
        }
        return null;
    }

    private List<Produto> toList(Cursor c) {
        List<Produto> produtos = new ArrayList<Produto>();

        if (c.moveToFirst()) {
            do {
                Produto produto = new Produto();
                produtos.add(produto);
                produto.setIdProduto(c.getInt(c.getColumnIndex("id_produto")));
                produto.setDescricao(c.getString(c.getColumnIndex("descricao")));
                produto.setNomeProduto(c.getString(c.getColumnIndex("nome_produto")));
                produto.setPreco(c.getFloat(c.getColumnIndex("preco")));
                produto.setTipo(c.getInt(c.getColumnIndex("tipo")));

            } while (c.moveToNext());
        }

        return produtos;
    }

    private Produto toProduto(Cursor c) {
        Produto produto = new Produto();

        if (c.moveToFirst()) {
            Log.w("moveToFirst", "true");
            produto.setIdProduto(c.getInt(c.getColumnIndex("id_produto")));
            produto.setDescricao(c.getString(c.getColumnIndex("descricao")));
            produto.setNomeProduto(c.getString(c.getColumnIndex("nome_produto")));
            produto.setPreco(c.getFloat(c.getColumnIndex("preco")));
            produto.setTipo(c.getInt(c.getColumnIndex("tipo")));

            return produto;
        } else {
            Log.w("moveToFirst", "false");
            return null;
        }
    }
}
