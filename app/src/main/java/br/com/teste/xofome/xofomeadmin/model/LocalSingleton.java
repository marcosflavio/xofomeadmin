package br.com.teste.xofome.xofomeadmin.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcosf on 07/12/2016.
 */

public class LocalSingleton {


    private static LocalSingleton instancia;
    private String latitude;
    private String longitude;

    private LocalSingleton(){
    }

    public static LocalSingleton getInstancia(){
        if(instancia == null){
            instancia = new LocalSingleton();
        }
        return instancia;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
