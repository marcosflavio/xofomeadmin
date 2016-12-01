package br.com.teste.xofome.xofomeadmin.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import br.com.teste.xofome.xofomeadmin.R;
import br.com.teste.xofome.xofomeadmin.adapters.ItemPedidoAdapter;
import br.com.teste.xofome.xofomeadmin.constantes.Codes;
import br.com.teste.xofome.xofomeadmin.constantes.Keys;
import br.com.teste.xofome.xofomeadmin.model.ItemPedido;
import br.com.teste.xofome.xofomeadmin.model.Pedido;
import br.com.teste.xofome.xofomeadmin.service.ItemPedidoService;
import br.com.teste.xofome.xofomeadmin.task.RecuperarPedidoTask;

public class DescricaoPedidoAcitivity extends AppCompatActivity {

    private RecyclerView rv;
    private ItemPedidoAdapter adapter = null;
    private static List<ItemPedido> itemPedidos;
    private TextView valorRealPedido;
    private TextView valorASerPagoRealPedido;
    private TextView descEndereco;
    private EditText statusPedido;
    private Pedido pedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descricao_pedido_acitivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDesc);
        setSupportActionBar(toolbar);

        //recebo o id de um pedido
        //recupero ele e jogo pra um pedido em memoria
        //Localizo os componentes e seto o valor de acordo com o do pedido recebido

        Intent intent = getIntent();
        int idPedido = (Integer) getIntent().getExtras().get(Keys.REQUEST_DETALHES);
        pedido = new Pedido();
        pedido.setIdPedido(idPedido);

        //Chamo minha asynctask para recuperar o pedido.
        //RecuperarPedidoTask recuperarPedidoTask = new RecuperarPedidoTask(getApplicationContext());
        //recuperarPedidoTask.execute(pedido);

         new Thread(new Runnable() {
            @Override
            public void run() {
                pedido = RecuperarPedidoTask.retornePedido(pedido);

            }
        }).start();

        Log.e("RecuperarPedidoTask", pedido.getStatus());

        valorRealPedido = (TextView) findViewById(R.id.textViewValorRealPedido);
        valorASerPagoRealPedido = (TextView) findViewById(R.id.textViewValorASerPagoRealPedido);
        descEndereco = (TextView) findViewById(R.id.textViewDescEndereco);

        //Assino os valores aos campos
        valorRealPedido.setText(String.valueOf(pedido.getValorTotalPedido()));
        valorASerPagoRealPedido.setText(String.valueOf(pedido.getValorASerPago()));
        if(pedido.getEndereco() == null)
            pedido.setEndereco("Não informado");
        descEndereco.setText(pedido.getEndereco());

        rv = (RecyclerView) findViewById(R.id.recyclerViewDescPedido);
        rv.setHasFixedSize(false);

        itemPedidos = ItemPedidoService.findAll(this.getApplicationContext());
        adapter = new ItemPedidoAdapter(this.getApplicationContext(),itemPedidos, onClickItemPedido());

        rv.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private ItemPedidoAdapter.ItemPedidoOnClickListener onClickItemPedido() {

        return new ItemPedidoAdapter.ItemPedidoOnClickListener() {

            @Override
            public void onClickItemPedido(View view, int idx) {

            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.desc_confirm) {
            //starta o service de setStatus
            return true;
        }else if (id == R.id.desc_cancel){
            Intent intent = new Intent(this, MainActivity.class);
            startActivityForResult(intent, Codes.REQUEST_BACK);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_desc, menu);
        return true;
    }
}
