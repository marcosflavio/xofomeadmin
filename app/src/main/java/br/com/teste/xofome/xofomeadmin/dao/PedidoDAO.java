package br.com.teste.xofome.xofomeadmin.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.teste.xofome.xofomeadmin.db.DBHelper;
import br.com.teste.xofome.xofomeadmin.model.ItemPedido;
import br.com.teste.xofome.xofomeadmin.model.Pedido;
import br.com.teste.xofome.xofomeadmin.model.Usuario;

/**
 * Created by marcosf on 22/11/2016.
 */

public class PedidoDAO {

    private Context context;
    private String table_name = "pedido";
    private String[] colunas  = new String[]{"idPedido","valorASerPago","status","endereco","valorTotalPedido"};
    private static final String TAG = "sql";

    public PedidoDAO (Context context){
        this.context = context;
    }


    public void save(Pedido pedido) {

        Integer id = pedido.getIdPedido();



        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        try {

            UsuarioDAO usuarioDAO = new UsuarioDAO(context);
            Usuario user = usuarioDAO.find();

            ContentValues values = new ContentValues();

            values.put("idPedido",pedido.getIdPedido());
            values.put("email",user.getEmail());
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
            UsuarioDAO usuarioDAO = new UsuarioDAO(context);
            Usuario user = usuarioDAO.find();

            ContentValues values = new ContentValues();
            values.put("status", pedido.getStatus());
            values.put("email",user.getEmail());
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

    //Listar todos os pedidos de um dado usuário
    public List<Pedido> findAllByUser(String email){
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();

        try {
            Cursor c = db.query(table_name, null, "email = '" + email + "'", null, null, null, null);
            return toList(c);
        } finally {
            db.close();
        }
    }

    //listar todos os pedidos
    public List<Pedido> findAll() {

        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();

        try {
            //Cursor c = db.query("pedido", null, null, null, null, null, null);
            Cursor cursor = db.rawQuery( "SELECT * FROM " + table_name,null);

            return toList(cursor);
        } finally {
            db.close();
        }
    }

    //retorna um determinado pedido
    public Pedido findById(int id) {

        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        try {
            Cursor c = db.query(table_name, null, "idPedido = '" + id + "'", null, null, null, null);
            if (c.moveToFirst()) {
                Pedido pedido = new Pedido();
                pedido.setIdPedido(c.getInt(c.getColumnIndex("idPedido")));

                String email = (c.getString(c.getColumnIndex("email")));
                UsuarioDAO usuarioDAO = new UsuarioDAO(context);
                Usuario usuario = usuarioDAO.find();

//                ItemPedidoDAO itemPedidoDAO = new ItemPedidoDAO(context);
//                List<ItemPedido> itens= itemPedidoDAO.findAllByPedido(pedido.getIdPedido());

                pedido.setUsuario(usuario);
//                pedido.setItensPedido(itens);
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

                String email = (c.getString(c.getColumnIndex("email")));
                UsuarioDAO usuarioDAO = new UsuarioDAO(context);
                Usuario usuario = usuarioDAO.find();

//                ItemPedidoDAO itemPedidoDAO = new ItemPedidoDAO(context);
//                List<ItemPedido> itens= itemPedidoDAO.findAllByPedido(pedido.getIdPedido());

                pedido.setUsuario(usuario);
//                pedido.setItensPedido(itens);
                pedido.setValorTotalPedido(c.getDouble(c.getColumnIndex("valorTotalPedido")));
                pedido.setStatus(c.getString(c.getColumnIndex("status")));
                pedido.setEndereco(c.getString(c.getColumnIndex("endereco")));
                pedido.setValorASerPago(c.getDouble(c.getColumnIndex("valorASerPago")));

            } while (c.moveToNext());
        }

        return pedidos;
    }

    private Pedido toPedido(Cursor c) {

        Pedido pedido = new Pedido();

        if (c.moveToFirst()) {
            Log.w("moveToFirst", "true");

            pedido.setIdPedido(c.getInt(c.getColumnIndex("idPedido")));

            //String email = (c.getString(c.getColumnIndex("email")));
            UsuarioDAO usuarioDAO = new UsuarioDAO(context);
            Usuario usuario = usuarioDAO.find();
            pedido.setValorTotalPedido(c.getDouble(c.getColumnIndex("valorTotalPedido")));
            pedido.setStatus(c.getString(c.getColumnIndex("status")));
            pedido.setEndereco(c.getString(c.getColumnIndex("endereco")));
            pedido.setUsuario(usuario);
            pedido.setValorASerPago(c.getDouble(c.getColumnIndex("valorASerPago")));

            return pedido;
        } else {
            Log.w("moveToFirst", "false");
            return null;
        }
    }

}
