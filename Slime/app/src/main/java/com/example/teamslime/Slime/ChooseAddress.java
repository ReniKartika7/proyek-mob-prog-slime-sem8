package com.example.teamslime.Slime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teamslime.Slime.adapter.ChooseAddressAdapter;
import com.example.teamslime.Slime.db.AddressDB;

public class ChooseAddress extends AppCompatActivity {

    ImageView btnChooseAddressBack;
    RecyclerView recyclerViewChooseAddress;
    TextView textChooseAddressAddNew;
    Button btnChooseAddress;

    public static ChooseAddressAdapter chooseAddressAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_address);

        btnChooseAddressBack = findViewById(R.id.btnChooseAddressBack);
        recyclerViewChooseAddress = findViewById(R.id.recyclerViewChooseAddress);
        textChooseAddressAddNew = findViewById(R.id.textChooseAddressAddNew);
        btnChooseAddress = findViewById(R.id.btnChooseAddress);

        //RV
        LinearLayoutManager llManagerChooseAddressItem = new LinearLayoutManager(ChooseAddress.this, LinearLayoutManager.VERTICAL, false);
        recyclerViewChooseAddress.setLayoutManager(llManagerChooseAddressItem);

        chooseAddressAdapter = new ChooseAddressAdapter(ChooseAddress.this);
        recyclerViewChooseAddress.setAdapter(chooseAddressAdapter);

        btnChooseAddressBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseAddress.this, CheckOut.class);
                startActivity(intent);
                finish();
            }
        });

        btnChooseAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ChooseAddressAdapter.lastChecked != null){
                    int addressID = AddressDB.currentAddress.get(ChooseAddressAdapter.lastCheckedPosition).AddressID;
                    ChooseAddressAdapter.lastChecked = null;
                    Intent intent = new Intent(ChooseAddress.this, CheckOut.class);
                    intent.putExtra("addressID", addressID);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(ChooseAddress.this, "Please choose your shipping address !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textChooseAddressAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddressDB.IS_FROM_CHOOSE_ADDRESS = true;

                Intent intent = new Intent(ChooseAddress.this, AddNewAddress.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ChooseAddress.this, CheckOut.class);
        startActivity(intent);
        finish();
    }
}