package br.com.teste.xofome.xofomeadmin.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import br.com.teste.xofome.xofomeadmin.constantes.HTTP;
import br.com.teste.xofome.xofomeadmin.model.Pedido;

/**
 * Created by marcosf on 01/12/2016.
 */

public class RecuperarPedidoTask {
    //private Context context;
    private static final String TAG = "RecuperarPedidoTask";


    public static Pedido retornePedido (Pedido pedido) {
        HttpURLConnection urlConnection = null;
        BufferedReader in = null;

        try {
            Log.w(TAG, "Entrei no returnPedido");

            Log.w(TAG, HTTP.FIND_ONE + pedido.getIdPedido());

            URL url = new URL(HTTP.FIND_ONE + pedido.getIdPedido());

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

            pedido = gson.fromJson(response, Pedido.class);
         //   return pedido;

        } catch (MalformedURLException ex) {
            Log.e(TAG, ex.getMessage());
            Log.e(TAG, ex.toString());
        } catch (IOException ex) {
            Log.e(TAG, ex.getMessage());
            Log.e(TAG, ex.toString());
        } finally {

            urlConnection.disconnect();
            try {
                in.close();
            } catch (IOException ex) {
                Log.e(TAG, ex.getMessage());
                Log.e(TAG, ex.toString());
            }

        }
        return pedido;
    }


}
