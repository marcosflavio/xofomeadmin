package br.com.teste.xofome.xofomeadmin.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.teste.xofome.xofomeadmin.model.ItemPedido;
import br.com.teste.xofome.xofomeadmin.model.Pedido;

/**
 * Created by marcosf on 22/11/2016.
 */

public class PedidoDAO {

    private Context context;
    private String table_name = "pedido";
    private String[] colunas  = new String[]{"idPedido","status","valorTotalPedido","endereco","valorASerPago"};
    private static final String TAG = "sql";

    public PedidoDAO (Context context){
        this.context = context;
    }


    public void save(Pedido pedido) {

        Integer id = pedido.getIdPedido();
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("status", pedido.getStatus());
            values.put("valorTotalPedido", pedido.getValorTotalPedido());
            values.put("endereco", pedido.getEndereco());
            values.put("valorASerPago", pedido.getValorASerPago());
            //insiro o pedido
            db.insert(table_name, "", values);
        } finally {
            Log.d(TAG, "Pedido" + pedido.getStatus() + " adicionado ao banco!");
            db.close();
        }

    }

    public void update(Pedido pedido){

        int id = pedido.getIdPedido();
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("status", pedido.getStatus());
            values.put("valorTotalPedido", pedido.getValorTotalPedido());
            values.put("endereco", pedido.getEndereco());
            values.put("valorASerPago", pedido.getValorASerPago());
            //Atualiza o pedido
            db.update(table_name,values,"idPedido = " + id, null);
        }finally {
            Log.d(TAG, "Pedido" + pedido.getStatus() + " atualizado no banco!");
            db.close();
        }
    }

    public void delete(Pedido pedido) {
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();

        try {
            db.delete(table_name, "idPedido", new String[]{String.valueOf(pedido.getIdPedido())});
            Log.i(TAG, "Deletou o pedido" + pedido.getStatus());
        } finally {
            db.close();
        }
    }

    public Pedido find(int id) {
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();

        try {
            Cursor c = db.query(table_name, null, "idPedido = '" + id + "'", null, null, null, null);
            return toPedido(c);
        } finally {
            db.close();
        }
    }

    //listar todos os pedidos com um determinado status
    public List<Pedido> findAllByStatus(String status) {

        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();

        try {
            Cursor c = db.query(table_name, null, "status = '" + status + "'", null, null, null, null);
            return toList(c);
        } finally {
            db.close();
        }
    }

    //listar todos os pedidos
    public List<Pedido> findAll() {

        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();

        try {
            Cursor c = db.query(table_name, null, null, null, null, null, null);
            return toList(c);
        } finally {
            db.close();
        }
    }

    //retorna um determinado pedido
    public Pedido findById(int id) {

        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        try {
            Cursor c = db.query(table_name, null, "id_produto = '" + id + "'", null, null, null, null);
            if (c.moveToFirst()) {
                Pedido pedido = new Pedido();
                pedido.setIdPedido(c.getInt(c.getColumnIndex("idPedido")));
                pedido.setValorTotalPedido(c.getDouble(c.getColumnIndex("valorTotalPedido")));
                pedido.setStatus(c.getString(c.getColumnIndex("status")));
                pedido.setEndereco(c.getString(c.getColumnIndex("endereco")));
                pedido.setValorASerPago(c.getDouble(c.getColumnIndex("valorTotalASerPago")));
                return pedido;
            }

        } finally {
            db.close();
        }
        return null;
    }

    private List<Pedido> toList(Cursor c) {
        List<Pedido> pedidos = new ArrayList<Pedido>();

        if (c.moveToFirst()) {
            do {
               Pedido pedido = new Pedido();
                pedidos.add(pedido);
                pedido.setIdPedido(c.getInt(c.getColumnIndex("idPedido")));
                pedido.setValorTotalPedido(c.getDouble(c.getColumnIndex("valorTotalPedido")));
                pedido.setStatus(c.getString(c.getColumnIndex("status")));
                pedido.setEndereco(c.getString(c.getColumnIndex("endereco")));
                pedido.setValorASerPago(c.getDouble(c.getColumnIndex("valorTotalASerPago")));

            } while (c.moveToNext());
        }

        return pedidos;
    }

    private Pedido toPedido(Cursor c) {
        Pedido pedido = new Pedido();

        if (c.moveToFirst()) {
            Log.w("moveToFirst", "true");

            pedido.setIdPedido(c.getInt(c.getColumnIndex("idPedido")));
            pedido.setValorTotalPedido(c.getDouble(c.getColumnIndex("valorTotalPedido")));
            pedido.setStatus(c.getString(c.getColumnIndex("status")));
            pedido.setEndereco(c.getString(c.getColumnIndex("endereco")));
            pedido.setValorASerPago(c.getDouble(c.getColumnIndex("valorTotalASerPago")));

            return pedido;
        } else {
            Log.w("moveToFirst", "false");
            return null;
        }
    }
//
//    private void setItensPedidoDAO( int id ){
//
//        Pedido pedido = findById(id);
//    }

    private List<ItemPedido> getItensPedidoDAO( int id ){

        List<ItemPedido> itens = new ArrayList<ItemPedido>();
        Pedido pedido = findById( id );
        itens = pedido.getItensPedido();

        if(itens.size()!= 0){

            return itens;
        }
        return null;
    }
}
