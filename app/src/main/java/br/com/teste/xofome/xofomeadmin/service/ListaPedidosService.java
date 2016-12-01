package br.com.teste.xofome.xofomeadmin.service;

import android.app.IntentService;
import android.content.Intent;
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
import br.com.teste.xofome.xofomeadmin.model.Pedido;
import br.com.teste.xofome.xofomeadmin.model.PedidoSingleton;

/**
 * Created by marcosf on 19/11/2016.
 */

public class ListaPedidosService extends IntentService{

    private static final String TAG = "serviceList";
    private Integer status;
    public ListaPedidosService() {
        super("ListaPedidosService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        receberPedidos();
        if(status.equals(200)) {

            sendBroadcast(new Intent("List_pedido_complete"));
            stopSelf();
        }else {
            sendBroadcast(new Intent("List_pedido_fail"));
            stopSelf();
        }
    }


        public void receberPedidos() {
            HttpURLConnection urlConnection = null;
            BufferedReader in = null;

            try {
                PedidoSingleton pedidoSingleton = PedidoSingleton.getInstancia();
                List<Pedido> pedidos = new ArrayList<Pedido>();
                URL url = new URL(HTTP.RETURN_LIST);
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

                pedidos = (List<Pedido>) gson.fromJson(response,new TypeToken<List<Pedido>>(){}.getType());
                status = urlConnection.getResponseCode();
                pedidoSingleton.setList(pedidos);

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

        }
    }

