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
import br.com.teste.xofome.xofomeadmin.model.Produto;

/**
 * Created by marcosf on 22/11/2016.
 */

public class ItemPedidoDAO {

    private Context context;
    private String table_name = "item_pedido";
    private String[] colunas  = new String[]{"idItemPedido","idPedido","idProduto","nomeProduto","quantidade","valor"};
    private static final String TAG = "sql";

    public ItemPedidoDAO(Context context){
        this.context = context;
    }

    public void save(ItemPedido itemPedido) {

        Integer id = itemPedido.getIdItemPedido();
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        try {
            Log.w("pedidoTASK", "Entrei no dao do item");

            ContentValues values = new ContentValues();
            //Encontro o meu pedido, pego o seu id e seto no values
            PedidoDAO pedidoDAO = new PedidoDAO(context);
            Pedido pedido = pedidoDAO.find(itemPedido.getPedido().getIdPedido());

            Log.w("pedidoTASK", "PEDIDO ::" + pedido.getIdPedido());
            //Encontro o meu produto, pego o seu id e seto no values
            ProdutoDAO produtoDAO = new ProdutoDAO(context);
            Produto produto = produtoDAO.find(itemPedido.getProduto().getIdProduto());
            Log.w("pedidoTASK", "PRODUTO ::" + produto.getIdProduto());

            values.put("idItemPedido", itemPedido.getIdItemPedido());
            values.put("idPedido", pedido.getIdPedido());
            values.put("idProduto", produto.getIdProduto());
            values.put("nomeProduto", produto.getNomeProduto());
            values.put("quantidade", itemPedido.getQuantidade());
            values.put("valor", itemPedido.getValor());
            //insiro o item
            db.insert(table_name, "", values);
        } finally {
            Log.d(TAG, "Item" + itemPedido.getNomeProduto() + " adicionado ao banco!");
            db.close();
        }

    }

    public void update(ItemPedido itemPedido){

        int id = itemPedido.getIdItemPedido();
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            //Encontro o meu pedido, pego o seu id e seto no values
            PedidoDAO pedidoDAO = new PedidoDAO(context);
            Pedido pedido = pedidoDAO.find(itemPedido.getPedido().getIdPedido());

            //Encontro o meu produto, pego o seu id e seto no values
            ProdutoDAO produtoDAO = new ProdutoDAO(context);
            Produto produto = produtoDAO.find(itemPedido.getProduto().getIdProduto());

            values.put("idPedido", pedido.getIdPedido());
            values.put("idProduto", produto.getIdProduto());
            values.put("nomeProduto", produto.getNomeProduto());
            values.put("quantidade", itemPedido.getQuantidade());
            values.put("valor", itemPedido.getValor());
            //Atualiza o item
            db.update(table_name,values,"idItemPedido = " + id, null);
        }finally {
            Log.d(TAG, "Item" + itemPedido.getIdItemPedido() + " atualizado no banco!");
            db.close();
        }
    }

    public void delete(ItemPedido itemPedido) {
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();

        try {
            db.delete(table_name, "idItemPedido", new String[]{String.valueOf(itemPedido.getIdItemPedido())});
            Log.i(TAG, "Deletou o item" + itemPedido.getIdItemPedido());
        } finally {
            db.close();
        }
    }

    public ItemPedido find(int id) {
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();

        try {
            Cursor c = db.query(table_name, null, "idItemPedido = '" + id + "'", null, null, null, null);
            return toItemPedido(c);
        } finally {
            db.close();
        }
    }

    //listar todos os itens
    public List<ItemPedido> findAll() {

        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();

        try {
            Cursor c = db.query(table_name, null, null, null, null, null, null);
            return toList(c);
        } finally {
            db.close();
        }
    }

    public List<ItemPedido> findAllByPedido(int idPedido){
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();

        try {
            Cursor c = db.query(table_name, null, "idPedido = '" + idPedido + "'", null, null, null, null);
            return toList(c);
        } finally {
            db.close();
        }

    }

    //retorna um determinado item
    public ItemPedido findById(int id) {

        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        try {
            Cursor c = db.query(table_name, null, "idItemPedido = '" + id + "'", null, null, null, null);
            if (c.moveToFirst()) {
                ItemPedido itemPedido = new ItemPedido();

                int idPedido = (c.getInt(c.getColumnIndex("idPedido")));
                int idProduto = (c.getInt(c.getColumnIndex("idProduto")));

                PedidoDAO pedidoDAO = new PedidoDAO(context);
                Pedido pedido = pedidoDAO.find(idPedido);

                ProdutoDAO produtoDAO = new ProdutoDAO(context);
                Produto produto = produtoDAO.find(idProduto);

                itemPedido.setProduto(produto);
                itemPedido.setPedido(pedido);
                itemPedido.setIdItemPedido(c.getInt(c.getColumnIndex("idItemPedido")));
                itemPedido.setNomeProduto(produto.getNomeProduto());
                itemPedido.setQuantidade(c.getInt(c.getColumnIndex("quantidade")));
                itemPedido.setValor(c.getDouble(c.getColumnIndex("valor")));

                return itemPedido;
            }

        } finally {
            db.close();
        }
        return null;
    }

    private List<ItemPedido> toList(Cursor c) {
        List<ItemPedido> itens = new ArrayList<ItemPedido>();

        if (c.moveToFirst()) {
            do {
                ItemPedido itemPedido = new ItemPedido();
                itens.add(itemPedido);

                int idPedido = (c.getInt(c.getColumnIndex("idPedido")));
                int idProduto = (c.getInt(c.getColumnIndex("idProduto")));

                PedidoDAO pedidoDAO = new PedidoDAO(context);
                Pedido pedido = pedidoDAO.find(idPedido);

                ProdutoDAO produtoDAO = new ProdutoDAO(context);
                Produto produto = produtoDAO.find(idProduto);

                itemPedido.setIdItemPedido(c.getInt(c.getColumnIndex("idItemPedido")));
                itemPedido.setPedido(pedido);
                itemPedido.setProduto(produto);
                itemPedido.setNomeProduto(produto.getNomeProduto());
                itemPedido.setQuantidade(c.getInt(c.getColumnIndex("quantidade")));
                itemPedido.setValor(c.getDouble(c.getColumnIndex("valor")));

            } while (c.moveToNext());
        }

        return itens;
    }

    private ItemPedido toItemPedido(Cursor c) {
        ItemPedido itemPedido = new ItemPedido();

        if (c.moveToFirst()) {
            Log.w("moveToFirst", "true");

            int idPedido = (c.getInt(c.getColumnIndex("idPedido")));
            int idProduto = (c.getInt(c.getColumnIndex("idProduto")));

            PedidoDAO pedidoDAO = new PedidoDAO(context);
            Pedido pedido = pedidoDAO.find(idPedido);

            ProdutoDAO produtoDAO = new ProdutoDAO(context);
            Produto produto = produtoDAO.find(idProduto);

            itemPedido.setIdItemPedido(c.getInt(c.getColumnIndex("idItemPedido")));
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);
            itemPedido.setNomeProduto(produto.getNomeProduto());
            itemPedido.setQuantidade(c.getInt(c.getColumnIndex("quantidade")));
            itemPedido.setValor(c.getDouble(c.getColumnIndex("valor")));

            return itemPedido;
        } else {
            Log.w("moveToFirst", "false");
            return null;
        }
    }
}
