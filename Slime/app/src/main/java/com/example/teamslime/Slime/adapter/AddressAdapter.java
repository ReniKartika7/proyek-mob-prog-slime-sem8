package com.example.teamslime.Slime.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamslime.Slime.EditAddress;
import com.example.teamslime.Slime.R;
import com.example.teamslime.Slime.db.AddressDB;
import com.example.teamslime.Slime.model.Address;

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

        String province = address.province + ", " + address.district + ", " + address.subDistrict + ", " + address.postalCode;

        province = province.toUpperCase();

        holder.textAddressItemFullName.setText(address.fullName);
        holder.textAddressItemPhoneNumber.setText(address.phoneNumber);
        holder.textAddressItemDetail.setText(address.detail);
        holder.textAddressItemProvince.setText(province);
    }

    @Override
    public int getItemCount() {
        return AddressDB.currentAddress.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textAddressItemFullName, textAddressItemPhoneNumber, textAddressItemDetail, textAddressItemProvince;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textAddressItemFullName = itemView.findViewById(R.id.textAddressItemFullName);
            textAddressItemPhoneNumber = itemView.findViewById(R.id.textAddressItemPhoneNumber);
            textAddressItemDetail = itemView.findViewById(R.id.textAddressItemDetail);
            textAddressItemProvince = itemView.findViewById(R.id.textAddressItemProvince);

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
