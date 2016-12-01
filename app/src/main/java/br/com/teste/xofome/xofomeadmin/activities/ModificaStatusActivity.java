package br.com.teste.xofome.xofomeadmin.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import br.com.teste.xofome.xofomeadmin.R;
import br.com.teste.xofome.xofomeadmin.constantes.Codes;
import br.com.teste.xofome.xofomeadmin.constantes.Keys;
import br.com.teste.xofome.xofomeadmin.task.ModificaStatusTask;

public class ModificaStatusActivity extends AppCompatActivity {
    private int idPedido;
    private String statusPedido;
    private TextView idModifica;
    private EditText status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_status);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarModifica);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        idPedido = (Integer) getIntent().getExtras().get(Keys.REQUEST_DETALHES);
        idModifica = (TextView) findViewById(R.id.textViewIdPedidoModifica);
        idModifica.setText(String.valueOf(idPedido));
        status = (EditText) findViewById(R.id.textViewStatusModifica);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.back_confirmar) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivityForResult(intent, Codes.REQUEST_BACK);
            return true;
        }else if (id == R.id.modifica_confirmar){

            statusPedido = status.getText().toString();
            //starta um service pra modificar o stauts

            ModificaStatusTask modificaStatusTask = new ModificaStatusTask(statusPedido);
            modificaStatusTask.execute(idPedido);

            Intent intent = new Intent(this, MainActivity.class);
            startActivityForResult(intent, Codes.REQUEST_BACK);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_modifica_status, menu);
        return true;
    }
}
