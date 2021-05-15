package com.example.reni.Slime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.reni.Slime.adapter.AddressAdapter;
import com.example.reni.Slime.db.AddressDB;

public class UserAddress extends AppCompatActivity {

    AddressDB addressDB;
    RecyclerView rvAddressItem;
    AddressAdapter addressAdapter;

    ImageView btnMyAddressBack;
    TextView textAddNewAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_address);

        btnMyAddressBack = findViewById(R.id.btnMyAddressBack);
        textAddNewAddress = findViewById(R.id.textAddNewAddress);

        //load address
        addressDB = new AddressDB(UserAddress.this);
        addressDB.loadAddress(UserAddress.this);

        //RV
        rvAddressItem = findViewById(R.id.recyclerViewAddress);
        LinearLayoutManager llManagerAddressItem = new LinearLayoutManager(UserAddress.this, LinearLayoutManager.VERTICAL, false);
        rvAddressItem.setLayoutManager(llManagerAddressItem);

        addressAdapter = new AddressAdapter(UserAddress.this);
        rvAddressItem.setAdapter(addressAdapter);

        btnMyAddressBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserAddress.this, Setting.class);
                startActivity(intent);
                finish();
            }
        });

        textAddNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserAddress.this, AddNewAddress.class);
                startActivity(intent);
            }
        });

    }
}