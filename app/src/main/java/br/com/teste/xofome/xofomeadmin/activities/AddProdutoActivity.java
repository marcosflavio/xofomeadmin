package br.com.teste.xofome.xofomeadmin.activities;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import br.com.teste.xofome.xofomeadmin.R;
import br.com.teste.xofome.xofomeadmin.constantes.Codes;
import br.com.teste.xofome.xofomeadmin.model.Produto;
import br.com.teste.xofome.xofomeadmin.service.ProdutoService;

public class AddProdutoActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    private RadioButton comidas, bebidas;
    private int tipo;
    private LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_produto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarAdd);
        setSupportActionBar(toolbar);
        linearLayout = (LinearLayout) findViewById(R.id.activity_add_produto);
    }

    public void listenerOnRadioButton(){

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        comidas = (RadioButton) findViewById(R.id.radioButtonProdutoComida);
        bebidas = (RadioButton) findViewById(R.id.radioButtonProdutoBebida);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if(i == R.id.radioButtonProdutoComida){
                    tipo = 0;
                }else if(i == R.id.radioButtonProdutoBebida){
                    tipo = 1;
                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.confirmar_produto) {

            EditText nomeProduto = (EditText) findViewById(R.id.editTextNomeProduto);

            EditText precoProduto = (EditText) findViewById(R.id.editTextPrecoProduto);
            EditText descProduto = (EditText) findViewById(R.id.editTextDescricaoProduto);
            float preco = Float.valueOf(precoProduto.getText().toString());
            String nome = nomeProduto.getText().toString();
            String desc = descProduto.getText().toString();

            Produto produto = ProdutoService.formarProduto(tipo, preco, nome, desc);
            ProdutoService.save(getApplicationContext(), produto);

            Snackbar snackbar = Snackbar
                    .make(linearLayout, "Produto " + produto.getNomeProduto() +
                            " criado com sucesso!", Snackbar.LENGTH_LONG);
            snackbar.show();
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
