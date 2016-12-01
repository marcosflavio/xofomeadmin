package br.com.teste.xofome.xofomeadmin.model;


/**
 * Created by marcosf on 27/11/2016.
 */
public class Usuario {

    private String email;

    public Usuario (){
    }

    public Usuario (String email){
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
