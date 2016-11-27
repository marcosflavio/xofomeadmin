package br.com.teste.xofome.xofomeadmin.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import java.net.*;
import java.io.*;
import com.google.gson.Gson;
import br.com.teste.xofome.xofomeadmin.model.Produto;

/**
 * Created by marcosf on 23/11/2016.
 */

public class AdicionaProdutoService extends AsyncTask<Produto, Integer, Integer> {
    private static final String TAG = "serviceAdd";
    private Context context;

    public AdicionaProdutoService(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(context,"Salvando produto..", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostExecute(Integer integer) {
       if(integer.equals(200)){
           Toast.makeText(context, "Produto inserido com sucesso!", Toast.LENGTH_SHORT).show();
       }else{
           Toast.makeText(context, "Erro, verifique a conexão de sua internet!", Toast.LENGTH_SHORT).show();
       }
    }

    @Override
    protected Integer doInBackground(Produto... produtos) {
        Integer status = 0;
        HttpURLConnection request = null;
        try {
            Log.w("DoIn", "Olha aqui>>>> " + produtos.length);
            Produto p = produtos[0];
            Log.w("DoIn", "PRODUTOOOO>>>> " + p.getNomeProduto());
            //A URL que enviaremos o request
            URL reqUrl = new URL("http://169.254.188.79:8060/produtos");
            request = (HttpURLConnection) (reqUrl.openConnection());
            Gson gson = new Gson();
            String post = gson.toJson(p);
            Log.w("DoIn", "JSON>>>> " + post);
            request.setDoOutput(true);
            request.setDoInput (true);
            //Adiciona o tamanho do conteudo do dados do post
            request.addRequestProperty("Content-Length", Integer.toString(post.length()));
            //Adiciona o tipo de conteudo do request
            request.setRequestMethod("POST");
            request.addRequestProperty("Content-Type", "application/json");
           // request.setRequestProperty("Accept", "application/json");
            request.connect();
            //Aqui é escrito os nossos dados de request
            DataOutputStream writer = new DataOutputStream(request.getOutputStream());
            //OutputStreamWriter writer = new OutputStreamWriter(request.getOutputStream());
            writer.writeBytes(post);
            //writer.writeBytes(post);
            writer.flush();
            writer.close();
            status = request.getResponseCode();
            Log.w("status", "A   " + status);

        } catch (MalformedURLException e) {
            Log.w("ErroNet", "Erro de MalFormed salvarPedido");
            e.printStackTrace();
        } catch (IOException e) {
           // Log.w("ErroNet", "Erro de IO salvarPedido");
            e.printStackTrace();
        }
        request.disconnect();
        return status; //considerar o status que volta do findProduct
    }
}

