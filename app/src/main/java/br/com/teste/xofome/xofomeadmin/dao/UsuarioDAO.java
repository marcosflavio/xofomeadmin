package br.com.teste.xofome.xofomeadmin.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import br.com.teste.xofome.xofomeadmin.db.DBHelper;
import br.com.teste.xofome.xofomeadmin.model.Usuario;

/**
 * Created by marcosf on 01/12/2016.
 */

public class UsuarioDAO {
    private Context context;
    private String table_name = "usuario";
    private static final String TAG = "sql";

    public UsuarioDAO (Context context){
        this.context = context;
    }

    public void save(Usuario usuario){

        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("email", usuario.getEmail());
            //insiro o usuario
            db.insert(table_name, "", values);
        } finally {
            Log.d(TAG, "Usuario " + usuario.getEmail() + " adicionado ao banco!");
            db.close();
        }

    }

    public long getTaskCount() {
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        return DatabaseUtils.queryNumEntries(db,table_name);
    }

    public Usuario find() {
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();

        try {
            Cursor c = db.query(table_name, null, null, null, null, null, null);
            return toUsuario(c);
        } finally {
            db.close();
        }
    }

    private Usuario toUsuario(Cursor c) {
        Usuario user = new Usuario();

        if (c.moveToFirst()) {
            Log.w("moveToFirst", "true");
            user.setEmail(c.getString(c.getColumnIndex("email")));
            return user;
        } else {
            Log.w("moveToFirst", "false");
            return null;
        }
    }

}
