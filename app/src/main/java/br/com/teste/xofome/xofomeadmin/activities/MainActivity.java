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
import br.com.teste.xofome.xofomeadmin.model.ItemPedido;
import br.com.teste.xofome.xofomeadmin.model.Pedido;
import br.com.teste.xofome.xofomeadmin.model.Produto;
import br.com.teste.xofome.xofomeadmin.service.PedidoService;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private PedidoAdapter adapter = null;
    private static List<Pedido> pedidos;

    //Listar somente os pedidos diferentes de "Entregue"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        rv = (RecyclerView) findViewById(R.id.recyclerViewPedidos);
        rv.setHasFixedSize(true);
        //pedidos = PedidoService.findAll(this.getApplicationContext());
        bancoStub();
        adapter = new PedidoAdapter(this.getApplicationContext(),pedidos, onClickPedido());

        rv.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
    }


    public void bancoStub(){

//        Produto produto = new Produto();
//        produto.setIdProduto(1);
//        produto.setNomeProduto("Feijão");
//        produto.setPreco(5.99f);
//        produto.setTipo(0);
//
//
//        Produto produto2 = new Produto();
//        produto.setIdProduto(2);
//        produto.setNomeProduto("Pastel");
//        produto.setPreco(5.99f);
//        produto.setTipo(0);
//
//
//        Produto produto3 = new Produto();
//        produto.setIdProduto(3);
//        produto.setNomeProduto("Refrigerante");
//        produto.setPreco(5.99f);
//        produto.setTipo(1);
//
//        ItemPedido itemPedido = new ItemPedido();
//        itemPedido.setValor(50f);
//        itemPedido.setQuantidade(5);
//        itemPedido.setNomeProduto(produto.getNomeProduto());
//        itemPedido.setIdPedido(1);
//
        pedidos = new ArrayList<Pedido>();
        Pedido pedido = new Pedido();
        pedido.setIdPedido(1);
        pedido.setStatus("Em preparo");

        Pedido pedido1 = new Pedido();
        pedido1.setIdPedido(2);
        pedido1.setStatus("Em entrega");

        Pedido pedido2 = new Pedido();
        pedido2.setIdPedido(3);
        pedido2.setStatus("Finalizado");

        Pedido pedido3 = new Pedido();
        pedido3.setIdPedido(4);
        pedido3.setStatus("Recebido");

        pedidos.add(pedido);
        pedidos.add(pedido2);
        pedidos.add(pedido3);
        pedidos.add(pedido1);
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
                            + p.getIdPedido(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), DescricaoPedidoAcitivity.class);
                    i.putExtra(Keys.REQUEST_DETALHES,p.getIdPedido());
                    startActivity(i);
            }
        };
    }


}
