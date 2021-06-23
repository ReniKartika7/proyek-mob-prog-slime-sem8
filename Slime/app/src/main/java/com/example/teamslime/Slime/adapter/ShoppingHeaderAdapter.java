package com.example.teamslime.Slime.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamslime.Slime.MyShoppingDetail;
import com.example.teamslime.Slime.R;
import com.example.teamslime.Slime.db.SnacksDB;
import com.example.teamslime.Slime.db.TransactionDetailDB;
import com.example.teamslime.Slime.db.TransactionHeaderDB;
import com.example.teamslime.Slime.model.Snack;
import com.example.teamslime.Slime.model.TransactionDetail;
import com.example.teamslime.Slime.model.TransactionHeader;

public class ShoppingHeaderAdapter extends RecyclerView.Adapter<ShoppingHeaderAdapter.ViewHolder> {
    Context ctx;
    SnacksDB snacksDB;
    TransactionDetailDB transactionDetailDB;

    public ShoppingHeaderAdapter(Context ctx){
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.my_shopping_list_header_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final TransactionHeader transactionHeader = TransactionHeaderDB.currentTransactionHeader.get(position);

        int subTotal = 0;
        int totalPrice;
        int qty = 0;
        boolean isFreeShipping = false;

        snacksDB = new SnacksDB(ctx);

        for(TransactionDetail transactionDetail : TransactionDetailDB.currentTransactionDetail){
            if(transactionDetail.transactionID == transactionHeader.ID){
                Snack snack = snacksDB.loadSpecificSnack(transactionDetail.snackID);

                subTotal = subTotal + (snack.price * transactionDetail.quantity);
                qty++;
            }
        }

        if(subTotal < 120000){
            totalPrice = subTotal + 10000;
        }else{
            totalPrice = subTotal;
            isFreeShipping = true;
        }

        holder.textShoppingHeaderDateTime.setText(transactionHeader.date + "");
        holder.textShoppingHeaderTotalPrice.setText("Total Price : Rp" + totalPrice);
        holder.textShoppingHeaderItemsPurchased.setText(qty + " items purchased");

        final boolean finalIsFreeShipping = isFreeShipping;
        final int finalSubTotal = subTotal;
        final int finalQty = qty;
        holder.textShoppingHeaderViewDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transactionDetailDB = new TransactionDetailDB(ctx);

                transactionDetailDB.loadSpecificTransactionDetail(ctx, transactionHeader.ID);

                Intent intent = new Intent(ctx, MyShoppingDetail.class);
                intent.putExtra("subTotal", finalSubTotal);
                intent.putExtra("qty", finalQty);
                intent.putExtra("isFreeShipping", finalIsFreeShipping);
                intent.putExtra("transactionID", transactionHeader.ID);
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return TransactionHeaderDB.currentTransactionHeader.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textShoppingHeaderDateTime, textShoppingHeaderTotalPrice, textShoppingHeaderItemsPurchased, textShoppingHeaderViewDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textShoppingHeaderDateTime = itemView.findViewById(R.id.textShoppingHeaderDateTime);
            textShoppingHeaderTotalPrice = itemView.findViewById(R.id.textShoppingHeaderTotalPrice);
            textShoppingHeaderItemsPurchased = itemView.findViewById(R.id.textShoppingHeaderItemsPurchased);
            textShoppingHeaderViewDetail = itemView.findViewById(R.id.textShoppingHeaderViewDetail);
        }

        @Override
        public void onClick(View view) {
        }
    }
}
