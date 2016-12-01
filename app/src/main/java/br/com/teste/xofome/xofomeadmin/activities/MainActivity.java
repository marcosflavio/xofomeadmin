package br.com.teste.xofome.xofomeadmin.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.teste.xofome.xofomeadmin.R;
import br.com.teste.xofome.xofomeadmin.adapters.PedidoAdapter;
import br.com.teste.xofome.xofomeadmin.constantes.Codes;
import br.com.teste.xofome.xofomeadmin.constantes.Keys;
import br.com.teste.xofome.xofomeadmin.dao.PedidoDAO;
import br.com.teste.xofome.xofomeadmin.model.ItemPedido;
import br.com.teste.xofome.xofomeadmin.model.Pedido;
import br.com.teste.xofome.xofomeadmin.model.Produto;
import br.com.teste.xofome.xofomeadmin.service.PedidoService;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private PedidoAdapter adapter = null;
    private static List<Pedido> pedidos = new ArrayList<Pedido>();

    //Listar somente os pedidos diferentes de "Entregue"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        rv = (RecyclerView) findViewById(R.id.recyclerViewPedidos);
        rv.setHasFixedSize(true);
       // bancoStub();
        //pedidos = PedidoService.findAll(this.getApplicationContext());
        adapter = new PedidoAdapter(this.getApplicationContext(),pedidos, onClickPedido());

        rv.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
    }

    public void bancoStub(){
//
//        Pedido pedido = new Pedido();
//        pedido.setStatus("Em preparo");
//        pedido.setEndereco("rua vila velha");
//        pedido.setValorASerPago(50d);
//        Pedido pedido1 = new Pedido();
//        pedido1.setStatus("Em entrega");
//        pedido1.setValorASerPago(50d);
//        pedido1.setEndereco("rua vila velha");
//        Pedido pedido2 = new Pedido();
//        pedido2.setStatus("Finalizado");
//        pedido2.setValorASerPago(50d);
//        pedido2.setEndereco("rua vila velha");
//        Pedido pedido3 = new Pedido();
//        pedido3.setStatus("Recebido");
//        pedido3.setValorASerPago(50d);
//        pedido3.setEndereco("rua vila velha");
//
//        PedidoDAO dao = new PedidoDAO(getApplicationContext());
//        dao.save(pedido);
//        dao.save(pedido2);
//        dao.save(pedido3);
//        dao.save(pedido1);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_produto) {

            Intent intent = new Intent(this, AddProdutoActivity.class);
            startActivityForResult(intent, Codes.REQUEST_ADD_PRODUTO);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private PedidoAdapter.PedidoOnClickListener onClickPedido() {

        return new PedidoAdapter.PedidoOnClickListener() {

            @Override
            public void onClickPedido(View view, int idx) {
                    Pedido p = pedidos.get(idx);
                    Toast.makeText(getApplicationContext(), "Pedido "
                            + p.getValorASerPago(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), DescricaoPedidoAcitivity.class);
                    i.putExtra(Keys.REQUEST_DETALHES,p.getIdPedido());
                    startActivity(i);
            }
        };
    }


}
