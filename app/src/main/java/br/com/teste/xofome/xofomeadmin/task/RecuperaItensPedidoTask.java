package br.com.teste.xofome.xofomeadmin.task;

import android.content.ClipData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.com.teste.xofome.xofomeadmin.constantes.HTTP;
import br.com.teste.xofome.xofomeadmin.model.ItemPedido;
import br.com.teste.xofome.xofomeadmin.model.Pedido;

/**
 * Created by marcosf on 01/12/2016.
 */

public class RecuperaItensPedidoTask extends AsyncTask<Pedido, Void, Void> {

    private Context context;
    private List<ItemPedido> itens = new ArrayList<ItemPedido>();
    private static final String TAG = "RecuperarItensTask";
    private Pedido pedido;
    private Integer status;
    public RecuperaItensPedidoTask(Context context, List<ItemPedido> itens){
        this.context = context;
        this.itens = itens;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(Pedido... pedidos) {
        Pedido pedido = pedidos[0];
        retorneItensPedido(pedido);
        if(status.equals(200)){
            Log.e(TAG, "Itens retornados com sucesso");
        }


        return null;
    }

    public void retorneItensPedido(Pedido pedido1) {
        HttpURLConnection urlConnection = null;
        BufferedReader in = null;

        try {
            Log.w(TAG, "Entrei no ReturnItensPedido");
            URL url = new URL(HTTP.REQUEST_FIND_ITENS_BY_PEDIDO + pedido1.getIdPedido());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(false);
            urlConnection.setRequestMethod("GET");

            in = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));

            String response = "";

            String inputLine;

            while ((inputLine = in.readLine()) != null)
                response += inputLine;


            Log.e(TAG, "Resposta >>>>>> " + response);
            Gson gson = new Gson();
            itens = (List<ItemPedido>) gson.fromJson(response,new TypeToken<List<ItemPedido>>(){}.getType());
            status = urlConnection.getResponseCode();

        } catch (MalformedURLException ex) {
            Log.e(TAG, ex.getMessage());
            ex.printStackTrace();
            Log.e(TAG, ex.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
            Log.e(TAG, ex.getMessage());
            Log.e(TAG, ex.toString());
        } finally {

            urlConnection.disconnect();
            try {
                in.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                Log.e(TAG, ex.getMessage());
                Log.e(TAG, ex.toString());
            }

        }

    }
}
