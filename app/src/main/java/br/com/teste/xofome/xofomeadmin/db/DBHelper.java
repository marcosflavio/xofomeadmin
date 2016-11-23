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

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "sql";
    public static final String NOME_BANCO = "xofome.admin.sqlite";
    private static final int VERSAO_BANCO = 1;

    public DBHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }


    private static final String CREATE_TABLE_PRODUTO = "create table if not exists produto " +
            "(id_produto integer primary key autoincrement" +
            " , nome_produto text,descricao text,preco float, tipo integer);";

    private static final String CREATE_TABLE_ITEM_PEDIDO = "create table if not exists item_pedido(idItemPedido" +
            " integer primary key autoincrement, idPedido integer, idProduto integer, nomeProduto text, " +
            "quantidade int, valor dpuble, foreign key(idPedido) references Pedido(idPedido)," +
            " foreign key(idProduto) references Produto(idProduto));";

    private static final String CREATE_TABLE_PEDIDO = "create table if not exists pedido" +
            "(idPedido integer primary key autoincrement, status text, valorTotalPedido double" +
            " endereco text,valorASerPago double)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PRODUTO);
        db.execSQL(CREATE_TABLE_ITEM_PEDIDO);
        db.execSQL(CREATE_TABLE_PEDIDO);
        Log.d(TAG, "Banco criado com sucesso!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Caso a gnt update a versao do banco
    }

}
