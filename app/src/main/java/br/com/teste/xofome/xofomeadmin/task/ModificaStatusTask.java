package br.com.teste.xofome.xofomeadmin.task;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import br.com.teste.xofome.xofomeadmin.activities.ModificaStatusActivity;
import br.com.teste.xofome.xofomeadmin.constantes.HTTP;

/**
 * Created by marcosf on 01/12/2016.
 */

public class ModificaStatusTask extends AsyncTask<Integer, Void, Void> {

    private String status;

    public ModificaStatusTask(String status) {
        this.status = status;
    }

    @Override
    protected Void doInBackground(Integer... integers) {

        int idPedido = integers[0];

        HttpURLConnection urlConnection = null;
        BufferedReader in = null;

        try {

            URL url = new URL(HTTP.REQUEST_UPDATE_STATUS + idPedido + "/"+ status);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(false);
            urlConnection.setRequestMethod("GET");

            in = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));

            String response = "";

            String inputLine;

            while ((inputLine = in.readLine()) != null)
                response += inputLine;

        } catch (MalformedURLException ex) {
           // Log.e(TAG, ex.getMessage());
            //Log.e(TAG, ex.toString());
            Log.e("modificastatus", "Url mal formada");
            ex.printStackTrace();
        } catch (IOException ex) {
            Log.e("modificastatus", ex.getMessage());
            Log.e("modificastatus", ex.toString());
            Log.e("modificastatus", "Sem internet");
            ex.printStackTrace();
        } finally {

            urlConnection.disconnect();
            try {
                in.close();
            } catch (IOException ex) {
                Log.e("modificastatus", "Erro na conexao");
                Log.e("modificastatus", ex.getMessage());
                Log.e("modificastatus", ex.toString());
                ex.printStackTrace();
            }

        }
        return  null;
    }

}


