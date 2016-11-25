package br.com.teste.xofome.xofomeadmin.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.Image;
import android.net.Uri;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import br.com.teste.xofome.xofomeadmin.R;
import br.com.teste.xofome.xofomeadmin.constantes.Codes;
import br.com.teste.xofome.xofomeadmin.model.Produto;
import br.com.teste.xofome.xofomeadmin.service.ProdutoService;
import br.com.teste.xofome.xofomeadmin.util.HandleBitmap;
import br.com.teste.xofome.xofomeadmin.util.ImageUtil;

public class AddProdutoActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    private RadioButton comidas, bebidas;
    private int tipo;
    private LinearLayout linearLayout;
    private ImageView imageView;
    private Bitmap bitmap;
    private Bitmap backup;

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_produto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarAdd);
        setSupportActionBar(toolbar);
        linearLayout = (LinearLayout) findViewById(R.id.activity_add_produto);
        imageView = (ImageView) findViewById(R.id.imageViewTeste);
        ImageButton b = (ImageButton) findViewById(R.id.imageButton);

        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, 0);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        HandleBitmap handleBitmap = new HandleBitmap(getApplicationContext());
        Bitmap bit = null;

        if( data != null && resultCode == RESULT_OK){
            Bundle bundle = data.getExtras();
            if(bundle != null){
                //Recupera o Bitmap retornado pela camera
                this.bitmap = (Bitmap) bundle.get("data");

                Uri uri = getImageUri(getApplicationContext(), bitmap);
                try {
                    bit = handleBitmap.handleSamplingAndRotationBitmap(getApplicationContext(),uri);
                    ExifInterface ei = new ExifInterface(uri.toString());
                    int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_UNDEFINED);

                    switch(orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            rotateImage(bit, 90);
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            rotateImage(bit, 180);
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            rotateImage(bit, 270);
                            break;
                        case ExifInterface.ORIENTATION_NORMAL:
                        default:
                            break;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                //Atualiza a imagem na tela
                imageView.setImageBitmap(bitmap);
            }
        }
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

            byte [] image = ImageUtil.getBytes(bitmap);

            Produto produto = ProdutoService.formarProduto(tipo, preco, nome, desc, image);

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

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

}
