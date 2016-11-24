package br.com.teste.xofome.xofomeadmin.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by marcosf on 23/11/2016.
 */

public class AdicionaProdutoService extends IntentService{
    private static final String TAG = "serviceAdd";

    public AdicionaProdutoService() {
        super("AdicionaProdutoService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Receber um produto
        //Transformá-lo em Json (sua img tbm)
        // salvar local
        //Criar conexão com webService
        //passar o produto pro webService
    }
}
