package com.example.teamslime.Slime.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamslime.Slime.R;
import com.example.teamslime.Slime.db.AddressDB;
import com.example.teamslime.Slime.model.Address;


public class ChooseAddressAdapter extends RecyclerView.Adapter<ChooseAddressAdapter.ViewHolder> {
    Context ctx;
    public static RadioButton lastChecked = null;
    public static int lastCheckedPosition = 0;

    public ChooseAddressAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.choose_address_item_view, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Address address = AddressDB.currentAddress.get(position);

        String province = address.province + ", " + address.district + ", " + address.subDistrict + ", " + address.postalCode;

        province = province.toUpperCase();

        holder.textChooseAddressItemFullName.setText(address.fullName);
        holder.textChooseAddressItemPhoneNumber.setText(address.phoneNumber);
        holder.textChooseAddressItemDetail.setText(address.detail);
        holder.textChooseAddressItemProvince.setText(province);

        boolean isChecked = holder.radioButtonChoosedAddres.isChecked();

        holder.radioButtonChoosedAddres.setTag(new Integer(position));

        if (position == 0 && isChecked) {
            lastChecked = holder.radioButtonChoosedAddres;
            lastCheckedPosition = 0;
        }

        holder.radioButtonChoosedAddres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton radioButton = (RadioButton) view;
                int clickedPos = ((Integer) radioButton.getTag()).intValue();

                if (radioButton.isChecked()) {
                    if (lastChecked != null && lastChecked != radioButton) {
                        lastChecked.setChecked(false);
                    }

                    lastChecked = radioButton;
                    lastCheckedPosition = clickedPos;
                } else {
                    lastChecked = null;
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return AddressDB.currentAddress.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textChooseAddressItemFullName, textChooseAddressItemPhoneNumber, textChooseAddressItemDetail, textChooseAddressItemProvince;
        RadioButton radioButtonChoosedAddres;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textChooseAddressItemFullName = itemView.findViewById(R.id.textChooseAddressItemFullName);
            textChooseAddressItemPhoneNumber = itemView.findViewById(R.id.textChooseAddressItemPhoneNumber);
            textChooseAddressItemDetail = itemView.findViewById(R.id.textChooseAddressItemDetail);
            textChooseAddressItemProvince = itemView.findViewById(R.id.textChooseAddressItemProvince);
            radioButtonChoosedAddres = itemView.findViewById(R.id.radioButtonChoosedAddres);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
