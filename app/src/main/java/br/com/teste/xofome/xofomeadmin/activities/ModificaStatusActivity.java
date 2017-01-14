package br.com.teste.xofome.xofomeadmin.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import br.com.teste.xofome.xofomeadmin.R;
import br.com.teste.xofome.xofomeadmin.constantes.Codes;
import br.com.teste.xofome.xofomeadmin.constantes.Keys;
import br.com.teste.xofome.xofomeadmin.task.ModificaStatusTask;

public class ModificaStatusActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private int idPedido;
    private String statusPedido;
    private TextView idModifica;
    //private EditText status;
    private Spinner spinnerStatus;
    private String statusString = "Iniciado";
    private static final String[] status = {"Recebido", "Em espera", "Preparando","Pronto","Em entrega","Finalizado"};

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
        //status = (EditText) findViewById(R.id.textViewStatusModifica);
        spinnerStatus = (Spinner) findViewById(R.id.spinnerStatus);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ModificaStatusActivity.this,
                android.R.layout.simple_spinner_item,status);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);
        spinnerStatus.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                statusString = status[0];
                break;
            case 1:
                statusString = status[1];
                break;
            case 2:
                statusString = status[2];
                break;
            case 3:
                statusString = status[3];
                break;
            case 4:
                statusString = status[4];
                break;
            case 5:
                statusString = status[5];
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.back_confirmar) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivityForResult(intent, Codes.REQUEST_BACK);
            return true;
        }else if (id == R.id.modifica_confirmar){

            //starta um service pra modificar o stauts

            ModificaStatusTask modificaStatusTask = new ModificaStatusTask(statusString);
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
