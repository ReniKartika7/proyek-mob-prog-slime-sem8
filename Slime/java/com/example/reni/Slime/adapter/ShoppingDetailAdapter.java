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
import com.example.reni.Slime.db.SnacksDB;
import com.example.reni.Slime.db.TransactionDetailDB;
import com.example.reni.Slime.model.Snack;
import com.example.reni.Slime.model.TransactionDetail;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ShoppingDetailAdapter extends RecyclerView.Adapter<ShoppingDetailAdapter.ViewHolder> {
    Context ctx;
    SnacksDB snacksDB;

    public ShoppingDetailAdapter(Context ctx){
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
        TransactionDetail transactionDetail = TransactionDetailDB.specificTransactionDetail.get(position);
        snacksDB = new SnacksDB(ctx);
        Snack snack = snacksDB.loadSpecificSnack(transactionDetail.snackID);

        String url = snack.coverUrl;

        Picasso.get().load(url).resize(200,200).into(holder.imageRecyclerDetailSummary, new Callback() {
            @Override
            public void onSuccess() {
                //progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {

            }
        });

        holder.textRecyclerDetailSummaryName.setText(snack.name);
        holder.textRecyclerDetailSummaryPrice.setText("Rp" + snack.price);
        holder.textRecyclerDetailSummaryQty.setText(transactionDetail.quantity + " Pcs");
    }

    @Override
    public int getItemCount() {
        return TransactionDetailDB.specificTransactionDetail.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageRecyclerDetailSummary;
        TextView textRecyclerDetailSummaryName, textRecyclerDetailSummaryQty, textRecyclerDetailSummaryPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageRecyclerDetailSummary = itemView.findViewById(R.id.imageRecyclerOrderSummary);
            textRecyclerDetailSummaryName = itemView.findViewById(R.id.textRecyclerOrderSummaryName);
            textRecyclerDetailSummaryPrice = itemView.findViewById(R.id.textRecyclerOrderSummaryPrice);
            textRecyclerDetailSummaryQty = itemView.findViewById(R.id.textRecyclerOrderSummaryQty);
        }
    }
}
