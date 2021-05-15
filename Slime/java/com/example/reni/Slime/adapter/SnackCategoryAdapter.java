package com.example.reni.Slime.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reni.Slime.HomePage;
import com.example.reni.Slime.R;
import com.example.reni.Slime.db.SnackCategoryDB;
import com.example.reni.Slime.db.SnacksDB;
import com.example.reni.Slime.model.Snack;
import com.example.reni.Slime.model.SnackCategory;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class SnackCategoryAdapter extends RecyclerView.Adapter<SnackCategoryAdapter.ViewHolder> {

    Context ctx;
    int col_index;
    ProgressBar progressBar;

    public SnackCategoryAdapter(Context ctx){
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.snack_category_view, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final SnackCategory snackCategory = SnackCategoryDB.vSnackCategory.get(position);

        String url = snackCategory.coverUrl;

        Picasso.get().load(url).resize(200,200).into(holder.ivSnackCategory, new Callback() {
            @Override
            public void onSuccess() {
                //progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {

            }
        });

        holder.txtSnackCategoryTitle.setText(snackCategory.name);

        holder.linearLayoutSnackCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                col_index = position;
                SnackCategoryDB.activeSnackCategory = snackCategory;

                SnacksDB.currentSnackItem.clear();
                if(snackCategory.ID == 1){
                    for(Snack snack : SnacksDB.vSnack){
                        SnacksDB.currentSnackItem.add(snack);
                    }
                }else{
                    for(Snack snack : SnacksDB.vSnack){
                        if(snack.categoryID == snackCategory.ID){
                            SnacksDB.currentSnackItem.add(snack);
                        }
                    }
                }

                //        RV Snack Item
                HomePage.snackItemAdapter.notifyDataSetChanged();
                notifyDataSetChanged();
            }
        });

        if(col_index == position){
            holder.linearLayoutSnackCategory.setBackgroundResource(R.drawable.snack_category_selected_background);
        }else{
            holder.linearLayoutSnackCategory.setBackgroundResource(R.drawable.snack_category_view_background);
        }
    }

    @Override
    public int getItemCount() {
        return SnackCategoryDB.vSnackCategory.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivSnackCategory;
        TextView txtSnackCategoryTitle;
        LinearLayout linearLayoutSnackCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivSnackCategory = itemView.findViewById(R.id.imageSnackCategory);
            txtSnackCategoryTitle = itemView.findViewById(R.id.txtSnackCategoryTitle);
            linearLayoutSnackCategory = itemView.findViewById(R.id.linearLayoutSnackCategory);
        }
    }
}
