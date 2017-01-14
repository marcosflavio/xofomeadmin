package br.com.teste.xofome.xofomeadmin.service;

import android.content.Context;

import br.com.teste.xofome.xofomeadmin.dao.UsuarioDAO;
import br.com.teste.xofome.xofomeadmin.model.Usuario;

/**
 * Created by marcosf on 01/12/2016.
 */
public class UsuarioService {

    private Context context;
    public UsuarioService(Context context){
        this.context = context;
    }

    public void save(Usuario usuario){
        UsuarioDAO dao = new UsuarioDAO(context);
        dao.save(usuario);
    }

    public Usuario getUsuario(){
        UsuarioDAO dao = new UsuarioDAO(context);
        return dao.find();
    }

    public Long getUser (){
        UsuarioDAO dao = new UsuarioDAO(context);
        return dao.getTaskCount();
    }
}