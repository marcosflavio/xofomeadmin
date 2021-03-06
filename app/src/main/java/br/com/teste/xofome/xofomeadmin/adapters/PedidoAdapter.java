package br.com.teste.xofome.xofomeadmin.adapters;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import br.com.teste.xofome.xofomeadmin.R;
import br.com.teste.xofome.xofomeadmin.activities.ComoChegarActivity;
import br.com.teste.xofome.xofomeadmin.activities.ModificaStatusActivity;
import br.com.teste.xofome.xofomeadmin.constantes.Keys;
import br.com.teste.xofome.xofomeadmin.model.Pedido;

/**
 * Created by marcosf on 23/11/2016.
 */

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.MyViewHolder>{

    private final List<Pedido> pedidos;
    private final Context context;
    private final PedidoAdapter.PedidoOnClickListener onClickListener;

    public interface PedidoOnClickListener {
        public void onClickPedido(View view, int idx);
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView pedidoLay;
        public TextView pedidoNumLay;
        public TextView statusPedidoLay;
        public TextView statusPedidoDin;


        public Button btnRota;

        public TextView enderecoLay;
        public TextView enderecoDin;

        public TextView valorTotalLay;
        public TextView valorTotalDin;

        public TextView valorPagoLay;
        public TextView valorPagoDin;

        private View view;

        public MyViewHolder(View v) {
            super(v);
            this.view = v;
            pedidoLay = (TextView) view.findViewById(R.id.textViewPedidoLay);
            pedidoNumLay = (TextView) view.findViewById(R.id.textViewPedidoNumLay);
            statusPedidoLay = (TextView) view.findViewById(R.id.textViewStatusPedidoLay);
            statusPedidoDin = (TextView) view.findViewById(R.id.textViewStatusPedidoDin);
            enderecoLay = (TextView) view.findViewById(R.id.textViewLayEndereco);
            enderecoDin = (TextView) view.findViewById(R.id.textViewEnderecoDin);
            valorTotalLay = (TextView) view.findViewById(R.id.textViewValorTotalLay);
            valorTotalDin = (TextView) view.findViewById(R.id.textViewValorTotalDin);
            valorPagoLay = (TextView) view.findViewById(R.id.textViewValorPagoLay);
            valorPagoDin = (TextView) view.findViewById(R.id.textViewValorPagoDin);
            btnRota = (Button) view.findViewById(R.id.buttonRota);
        }

        public View getView() {
            return view;
        }
    }


    public PedidoAdapter(Context context, List<Pedido> pedidos, PedidoAdapter.PedidoOnClickListener onClickListener) {
        this.context = context;
        this.pedidos = pedidos;
        this.onClickListener = onClickListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PedidoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pedido, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final Pedido p = pedidos.get(position);
        //Atualiza os valores nas views
        holder.statusPedidoDin.setText(p.getStatus());
        holder.pedidoNumLay.setText(String.valueOf(p.getIdPedido()));
        holder.valorTotalDin.setText(String.valueOf(p.getValorTotalPedido()));
        holder.valorPagoDin.setText(String.valueOf(p.getValorASerPago()));

        holder.btnRota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ComoChegarActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Double latitude =   Double.valueOf(p.getLatitude());
                Double longitude =  Double.valueOf(p.getLongitude());

                LatLng latLng = new LatLng(latitude, longitude);

                i.putExtra(Keys.REQUEST_ROTA,latLng);
                context.startActivity(i);
            }
        });
        //Click
        if (onClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    onClickListener.onClickPedido(holder.getView(), position);

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }
}

