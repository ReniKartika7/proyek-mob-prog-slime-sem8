package com.example.reni.Slime.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reni.Slime.R;
import com.example.reni.Slime.db.CartDB;
import com.example.reni.Slime.db.SnacksDB;
import com.example.reni.Slime.model.Cart;
import com.example.reni.Slime.model.Snack;
import com.squareup.picasso.Callback;
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
        Picasso.get().load(url).resize(200,200).into(holder.imageRecyclerOrderSummary, new Callback() {
            @Override
            public void onSuccess() {
                //progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
            }
        });

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
