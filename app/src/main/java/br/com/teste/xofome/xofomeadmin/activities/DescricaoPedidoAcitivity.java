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

import java.util.List;

import br.com.teste.xofome.xofomeadmin.R;
import br.com.teste.xofome.xofomeadmin.adapters.ItemPedidoAdapter;
import br.com.teste.xofome.xofomeadmin.constantes.Codes;
import br.com.teste.xofome.xofomeadmin.constantes.Keys;
import br.com.teste.xofome.xofomeadmin.model.ItemPedido;
import br.com.teste.xofome.xofomeadmin.service.ItemPedidoService;

public class DescricaoPedidoAcitivity extends AppCompatActivity {

    private RecyclerView rv;
    private ItemPedidoAdapter adapter = null;
    private static List<ItemPedido> itemPedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descricao_pedido_acitivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDesc);
        setSupportActionBar(toolbar);

        rv = (RecyclerView) findViewById(R.id.recyclerViewDescPedido);
        rv.setHasFixedSize(false);
        bancoStub();
        // modificar esse service, retornar apenas os itens de um determinado pedido
        //na verdade, está certo, depois deve ser feita uma filtragem dos itens que tem
        //um determinado pedido id, igual o do id que foi clicado
        //receber o id desse pedido via bundle
        itemPedidos = ItemPedidoService.findAll(this.getApplicationContext());
        adapter = new ItemPedidoAdapter(this.getApplicationContext(),itemPedidos, onClickItemPedido());

        rv.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
    }

    public void bancoStub(){

        ItemPedido p = new ItemPedido();
        p.setIdPedido(2);
        p.setNomeProduto("Pastel");
        p.setQuantidade(5);

        ItemPedido p1 = new ItemPedido();
        p1.setIdPedido(1);
        p1.setNomeProduto("Carne");
        p1.setQuantidade(5);

        ItemPedido p2 = new ItemPedido();
        p2.setIdPedido(3);
        p2.setNomeProduto("Feijão");
        p2.setQuantidade(6);

        ItemPedido p3 = new ItemPedido();
        p3.setIdPedido(2);
        p3.setNomeProduto("Arroz");
        p3.setQuantidade(5);

        ItemPedidoService.save(p,getApplicationContext());
        ItemPedidoService.save(p1,getApplicationContext());
        ItemPedidoService.save(p2,getApplicationContext());
        ItemPedidoService.save(p3,getApplicationContext());
    }

    private ItemPedidoAdapter.ItemPedidoOnClickListener onClickItemPedido() {

        return new ItemPedidoAdapter.ItemPedidoOnClickListener() {

            @Override
            public void onClickItemPedido(View view, int idx) {

                ItemPedido itemPedido = itemPedidos.get(idx);
                Toast.makeText(getApplicationContext(), "ItemPedido "
                        + itemPedido.getNomeProduto(), Toast.LENGTH_SHORT).show();
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
            //volta pra main activity
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
