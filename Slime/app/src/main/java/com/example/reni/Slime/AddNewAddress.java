package com.example.reni.Slime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.reni.Slime.model.Address;

public class AddNewAddress extends AppCompatActivity {

    ImageView btnAddAddressBack;

    EditText editAddressFullName, editAddressPhoneNumber, editAddress;

    Button btnAddAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_address);

        btnAddAddressBack = findViewById(R.id.btnAddAddressBack);
        editAddressFullName = findViewById(R.id.editAddressFullName);
        editAddressPhoneNumber = findViewById(R.id.editAddressPhoneNumber);
        editAddress = findViewById(R.id.editAddress);
        btnAddAddress = findViewById(R.id.btnAddAddress);

        btnAddAddressBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddNewAddress.this, UserAddress.class);
                startActivity(intent);
                finish();
            }
        });

        btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddNewAddress.this, EditAddress.class);
                startActivity(intent);
            }
        });

    }
}