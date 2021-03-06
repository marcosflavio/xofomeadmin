package br.com.teste.xofome.xofomeadmin.activities;
import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

import br.com.teste.xofome.xofomeadmin.R;
import br.com.teste.xofome.xofomeadmin.adapters.PedidoAdapter;
import br.com.teste.xofome.xofomeadmin.constantes.Codes;
import br.com.teste.xofome.xofomeadmin.constantes.Keys;
import br.com.teste.xofome.xofomeadmin.model.LocalSingleton;
import br.com.teste.xofome.xofomeadmin.model.Pedido;
import br.com.teste.xofome.xofomeadmin.model.PedidoSingleton;
import br.com.teste.xofome.xofomeadmin.service.ListaPedidosService;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private RecyclerView rv;
    private PedidoAdapter adapter = null;
    private static List<Pedido> pedidos = new ArrayList<Pedido>();
    protected SwipeRefreshLayout swipeRefreshLayout;
    private GoogleApiClient mGoogleApiClient;
    private String latitude;
    private String longitude;
    LocalSingleton localSingleton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        registerReceiver(receiverListarPedido,new IntentFilter("List_pedido_complete"));
        registerReceiver(receiverListarPedidoFail, new IntentFilter("Update_status_fail"));
        PedidoSingleton pedidoSingleton = PedidoSingleton.getInstancia();
        localSingleton = LocalSingleton.getInstancia();
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeListarPedidos);
        swipeRefreshLayout.setOnRefreshListener(OnRefreshListener());
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_1,R.color.refresh_progress_2,
                R.color.refresh_progress_3);

        rv = (RecyclerView) findViewById(R.id.recyclerViewPedidos);
        rv.setHasFixedSize(true);

        pedidos = pedidoSingleton.getList();
        adapter = new PedidoAdapter(this.getApplicationContext(),pedidos, onClickPedido());

        rv.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        callConnection();
    }


    private synchronized void callConnection() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    // LISTENER
    @Override
    public void onConnected(Bundle bundle) {
        Log.i("LOGGG", "onConnected(" + bundle + ")");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location l = LocationServices
                .FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        if(l != null){
            this.latitude = String.valueOf(l.getLatitude());
            this.longitude = String.valueOf(l.getLongitude());

            localSingleton.setLatitude(latitude);
            localSingleton.setLongitude(longitude);

            Log.i("LOGGG", "latitude: "+l.getLatitude());
            Log.i("LOGGG", "longitude: "+l.getLongitude());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("LOGGG", "onConnectionSuspended(" + i + ")");
    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("LOGGG", "onConnectionFailed("+connectionResult+")");
    }

    private SwipeRefreshLayout.OnRefreshListener OnRefreshListener(){
        return  new SwipeRefreshLayout.OnRefreshListener(){

            @Override
            public void onRefresh() {
                startService(new Intent(getApplicationContext(),ListaPedidosService.class));
            }
        };
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
    protected void onResume() {
        PedidoSingleton pedidoSingleton = PedidoSingleton.getInstancia();
        swipeRefreshLayout.setRefreshing(false);
        pedidos = pedidoSingleton.getList();
        adapter = new PedidoAdapter(this.getApplicationContext(),pedidos, onClickPedido());
        rv.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);
        super.onResume();
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
                Intent i = new Intent(getApplicationContext(), ModificaStatusActivity.class);
                i.putExtra(Keys.REQUEST_DETALHES,p.getIdPedido());
                startActivity(i);
            }
        };
    }

    private BroadcastReceiver receiverListarPedido = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            onResume();
        }
    };

    private BroadcastReceiver receiverListarPedidoFail = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("service","Entrei no BroadcastReceiver listarFail");
            Toast.makeText(getApplicationContext(), "Falha ao verificar pedidos, favor verificar conexão com a internet.",
                    Toast.LENGTH_SHORT).show();
        }
    };

}
