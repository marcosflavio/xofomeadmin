package br.com.teste.xofome.xofomeadmin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import br.com.teste.xofome.xofomeadmin.R;
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
        private View view;

        public MyViewHolder(View v) {
            super(v);
            this.view = v;
            pedidoLay = (TextView) view.findViewById(R.id.textViewPedidoLay);
            pedidoNumLay = (TextView) view.findViewById(R.id.textViewPedidoNumLay);
            statusPedidoLay = (TextView) view.findViewById(R.id.textViewStatusPedidoLay);
            statusPedidoDin = (TextView) view.findViewById(R.id.textViewStatusPedidoDin);
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
        holder.statusPedidoDin.setText(p.getEndereco());
        holder.pedidoNumLay.setText(String.valueOf(p.getValorASerPago()));
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

