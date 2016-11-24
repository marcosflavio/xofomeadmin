package br.com.teste.xofome.xofomeadmin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.teste.xofome.xofomeadmin.R;
import br.com.teste.xofome.xofomeadmin.model.ItemPedido;

/**
 * Created by marcosf on 23/11/2016.
 */

public class ItemPedidoAdapter extends RecyclerView.Adapter<ItemPedidoAdapter.MyViewHolder>{

    private final List<ItemPedido> itemPedidos;
    private final Context context;
    private final ItemPedidoAdapter.ItemPedidoOnClickListener onClickListener;

    public interface ItemPedidoOnClickListener {
        public void onClickItemPedido(View view, int idx);
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView produtoNome;
        public TextView quantidadeItem;
        private View view;

        public MyViewHolder(View v) {
            super(v);
            this.view = v;
            produtoNome = (TextView) view.findViewById(R.id.textViewProdutoNome);
            quantidadeItem = (TextView) view.findViewById(R.id.textViewQuantidadeItemPedido);
        }

        public View getView() {
            return view;
        }
    }


    public ItemPedidoAdapter(Context context, List<ItemPedido> itemPedidos, ItemPedidoAdapter.ItemPedidoOnClickListener onClickListener) {
        this.context = context;
        this.itemPedidos = itemPedidos;
        this.onClickListener = onClickListener;
    }


    @Override
    public ItemPedidoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final ItemPedido itemPedido = itemPedidos.get(position);

        //Atualiza os valores nas views
        holder.quantidadeItem.setText(String.valueOf(itemPedido.getQuantidade()));
        holder.produtoNome.setText(itemPedido.getNomeProduto());

        //Click
        if (onClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    onClickListener.onClickItemPedido(holder.getView(), position);

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return itemPedidos.size();
    }

}
