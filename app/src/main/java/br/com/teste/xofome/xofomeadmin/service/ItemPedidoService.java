package br.com.teste.xofome.xofomeadmin.service;

import android.content.Context;

import java.util.List;

import br.com.teste.xofome.xofomeadmin.dao.ItemPedidoDAO;
import br.com.teste.xofome.xofomeadmin.model.ItemPedido;

/**
 * Created by marcosf on 23/11/2016.
 */

public class ItemPedidoService {

    public static void save (ItemPedido itemPedido, Context context){
        ItemPedidoDAO dao = new ItemPedidoDAO(context);
        dao.save(itemPedido);
    }

    public static void update (ItemPedido itemPedido, Context context){
        ItemPedidoDAO dao = new ItemPedidoDAO(context);
        dao.update(itemPedido);
    }

    public static void delete (ItemPedido itemPedido, Context context){
        ItemPedidoDAO dao = new ItemPedidoDAO(context);
        dao.delete(itemPedido);
    }

    public static ItemPedido find (int id, Context context){
        ItemPedidoDAO dao = new ItemPedidoDAO(context);
        return dao.findById(id);
    }

    public static List<ItemPedido> findAll( Context context){
        ItemPedidoDAO dao = new ItemPedidoDAO(context);
        return dao.findAll();
    }
}
