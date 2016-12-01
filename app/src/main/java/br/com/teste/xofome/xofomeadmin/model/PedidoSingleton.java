package br.com.teste.xofome.xofomeadmin.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcosf on 01/12/2016.
 */

public class PedidoSingleton {

    private static PedidoSingleton instancia;
    private List<Pedido> pedidos = new ArrayList<Pedido>();

    private PedidoSingleton(){
    }
    public static PedidoSingleton getInstancia(){
        if(instancia == null){
            instancia = new PedidoSingleton();
        }
        return instancia;
    }

    public void adicionarItem(Pedido pedido){
        pedidos.add(pedido);
    }

    public List<Pedido> getList(){
        return this.pedidos;
    }

    public void setList (List <Pedido> pedidos){
        this.pedidos = pedidos;
    }

    public void clear (){

        for(int i = 0; i < pedidos.size(); i++){
            pedidos.remove(i);
        }
        pedidos.clear();
        pedidos = new ArrayList<>();

    }
}
