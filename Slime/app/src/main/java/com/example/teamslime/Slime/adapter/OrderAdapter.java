package com.example.teamslime.Slime.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamslime.Slime.R;
import com.example.teamslime.Slime.db.CartDB;
import com.example.teamslime.Slime.db.SnacksDB;
import com.example.teamslime.Slime.model.Cart;
import com.example.teamslime.Slime.model.Snack;
import com.squareup.picasso.Picasso;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    Context ctx;
    SnacksDB snacksDB;
    CartDB cartDB;

    public OrderAdapter(Context ctx){
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.order_summary_view, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cart cart;
        if(CartDB.IS_CHECKED_BUY_NOW == false){
            cart = CartDB.currentCart.get(position);
        }else{
            cart = CartDB.vBuy;
        }

        snacksDB = new SnacksDB(ctx);
        Snack snack = snacksDB.loadSpecificSnack(cart.snackID);

        String url = snack.coverUrl;
        Picasso.get().load(url).placeholder(R.drawable.loading_progressbar).error(R.drawable.ic_error).resize(200,200).into(holder.imageRecyclerOrderSummary);


        holder.textRecyclerOrderSummaryName.setText(snack.name);
        holder.textRecyclerOrderSummaryPrice.setText("Rp" + snack.price);
        holder.textRecyclerOrderSummaryQty.setText(cart.quantity + " Pcs");
    }

    @Override
    public int getItemCount() {
        if(CartDB.IS_CHECKED_BUY_NOW == false){
            return CartDB.currentCart.size();
        }else{
            return 1;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageRecyclerOrderSummary;
        TextView textRecyclerOrderSummaryName, textRecyclerOrderSummaryQty, textRecyclerOrderSummaryPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageRecyclerOrderSummary = itemView.findViewById(R.id.imageRecyclerOrderSummary);
            textRecyclerOrderSummaryName = itemView.findViewById(R.id.textRecyclerOrderSummaryName);
            textRecyclerOrderSummaryQty = itemView.findViewById(R.id.textRecyclerOrderSummaryQty);
            textRecyclerOrderSummaryPrice = itemView.findViewById(R.id.textRecyclerOrderSummaryPrice);
        }
    }
}
