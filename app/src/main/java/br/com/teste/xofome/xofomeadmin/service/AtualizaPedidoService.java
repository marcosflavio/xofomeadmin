package br.com.teste.xofome.xofomeadmin.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by marcosf on 19/11/2016.
 */

public class AtualizaPedidoService extends IntentService {

    private static final String TAG = "serviceUp";
    public AtualizaPedidoService() {
        super("AtualizaPedidoService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //Recebe um produto
        //Atualiza ele no banco local
        // Transforma em Json
        // Cria conexão com o WebService
        // Envia o Json atualizado para o WebService de Atualizar Status
    }
}
