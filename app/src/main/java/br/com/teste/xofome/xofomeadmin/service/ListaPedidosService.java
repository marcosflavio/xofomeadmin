package br.com.teste.xofome.xofomeadmin.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by marcosf on 19/11/2016.
 */

public class ListaPedidosService extends IntentService{

    private static final String TAG = "serviceList";
    public ListaPedidosService() {
        super("ListaPedidosService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //Cria conexão com o WebService
        // Recebe a lista de pedidos em Json
        // Transforma a lista pra object e salva no banco
        // envia um broadCast pra chamar onResume da Main activity
        // Salva no banco local os novos pedidos
    }
}
