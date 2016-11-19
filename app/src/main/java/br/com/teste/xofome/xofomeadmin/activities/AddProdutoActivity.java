package br.com.teste.xofome.xofomeadmin.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import br.com.teste.xofome.xofomeadmin.R;
import br.com.teste.xofome.xofomeadmin.constantes.Codes;

public class AddProdutoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_produto);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.confirmar_produto) {

            //pego os dados dos campos e crio o produto
            //notifico a criação com sucesso
            //starto um service para adicionar o produto no servidor com json
            return true;

        }else if (id == R.id.cancelar_produto){
            Intent intent = new Intent(this, MainActivity.class);
            startActivityForResult(intent, Codes.REQUEST_MAIN);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_produto_menu, menu);
        return true;
    }

}
