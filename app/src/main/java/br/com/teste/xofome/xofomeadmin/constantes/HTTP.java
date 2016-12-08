package br.com.teste.xofome.xofomeadmin.constantes;

/**
 * Created by marcosf on 01/12/2016.
 */

public interface HTTP {
    public static final String URL = "http://192.168.1.17:8060";
    public static final String SAVE_PRODUTO = URL+"/produtos";
    public static final String RETURN_LIST = URL+"/pedidos";
    public static final String FIND_ONE = URL+"/pedidos/";
    public static final String REQUEST_FIND_ITENS_BY_PEDIDO = URL + "/pedidos/itensp/";
    public static final String REQUEST_UPDATE_STATUS = URL + "/pedidos/status/";
}
