package com.example.teamslime.Slime.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamslime.Slime.R;
import com.example.teamslime.Slime.SnackItemBuy;
import com.example.teamslime.Slime.db.SnacksDB;
import com.example.teamslime.Slime.model.Snack;
import com.squareup.picasso.Picasso;

public class SnackItemAdapter extends RecyclerView.Adapter<SnackItemAdapter.ViewHolder> {

    Context ctx;
    ProgressBar progressBar;

    public SnackItemAdapter(Context ctx){
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.snack_item_view, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Snack snack = SnacksDB.currentSnackItem.get(position);

        String url = snack.coverUrl;

        String name = snack.name;
        if(name.length() > 12){
            name = name.substring(0, 12) + "...";
        }

        Picasso.get().load(url).placeholder(R.drawable.loading_progressbar).error(R.drawable.ic_error).resize(200,200).into(holder.imageSnackItem);



        holder.textSnackItemName.setText(name);
        holder.textSnackItemPrice.setText("Rp" + snack.price);
        holder.textSnackItemStock.setText("Stock : " + snack.stock);
    }

    @Override
    public int getItemCount() {
        return SnacksDB.currentSnackItem.size();
        //return 5;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageSnackItem;
        TextView textSnackItemName, textSnackItemPrice, textSnackItemStock;
        LinearLayout linearLayoutSnackItem;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageSnackItem = itemView.findViewById(R.id.imageSnackItem);
            textSnackItemName = itemView.findViewById(R.id.textSnackItemName);
            textSnackItemPrice = itemView.findViewById(R.id.textSnackItemPrice);
            textSnackItemStock = itemView.findViewById(R.id.textSnackItemStock);
            linearLayoutSnackItem = itemView.findViewById(R.id.linearLayoutSnackItem);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            Intent intent = new Intent(ctx, SnackItemBuy.class);
            intent.putExtra("snackPosition", position);
            ctx.startActivity(intent);
        }
    }

}
