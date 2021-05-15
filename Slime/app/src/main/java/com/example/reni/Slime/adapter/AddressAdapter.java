package com.example.reni.Slime.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reni.Slime.EditAddress;
import com.example.reni.Slime.R;
import com.example.reni.Slime.db.AddressDB;
import com.example.reni.Slime.model.Address;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    Context ctx;

    public AddressAdapter(Context ctx){
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.address_item_view, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Address address = AddressDB.currentAddress.get(position);

        holder.textAddressItemFullName.setText(address.fullName);
        holder.textAddressItemPhoneNumber.setText(address.phoneNumber);
        holder.textAddressItemDetail.setText(address.detail);
    }

    @Override
    public int getItemCount() {
        return AddressDB.currentAddress.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textAddressItemFullName, textAddressItemPhoneNumber, textAddressItemDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Log.i("ADAPTER", "masuk1");
            textAddressItemFullName = itemView.findViewById(R.id.textAddressItemFullName);
            textAddressItemPhoneNumber = itemView.findViewById(R.id.textAddressItemPhoneNumber);
            textAddressItemDetail = itemView.findViewById(R.id.textAddressItemDetail);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            Intent intent = new Intent(ctx, EditAddress.class);
            intent.putExtra("addressPosition", position);
            ctx.startActivity(intent);

        }
    }

}
