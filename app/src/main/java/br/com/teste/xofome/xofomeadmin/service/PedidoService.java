package br.com.teste.xofome.xofomeadmin.service;

import android.content.Context;

import java.util.List;

import br.com.teste.xofome.xofomeadmin.dao.PedidoDAO;
import br.com.teste.xofome.xofomeadmin.model.Pedido;

/**
 * Created by marcosf on 23/11/2016.
 */

public class PedidoService {

    public static void save (Pedido pedido, Context context){
        PedidoDAO dao = new PedidoDAO(context);
        dao.save(pedido);
    }

    public static void update (Pedido pedido, Context context){
        PedidoDAO dao = new PedidoDAO(context);
        dao.update(pedido);
    }

    public static void delete (Pedido pedido, Context context){
        PedidoDAO dao = new PedidoDAO(context);
        dao.delete(pedido);
    }

    public static Pedido find (int id, Context context){
        PedidoDAO dao = new PedidoDAO(context);
        return dao.findById(id);
    }

    public static List<Pedido> findAllByStatus(String status, Context context){
        PedidoDAO dao = new PedidoDAO(context);
        return dao.findAllByStatus(status);
    }

    public static List<Pedido> findAll( Context context){
        PedidoDAO dao = new PedidoDAO(context);
        return dao.findAll();
    }


}
