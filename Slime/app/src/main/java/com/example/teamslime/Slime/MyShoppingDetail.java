package com.example.teamslime.Slime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teamslime.Slime.adapter.ShoppingDetailAdapter;
import com.example.teamslime.Slime.db.AddressDB;
import com.example.teamslime.Slime.db.TransactionDetailDB;
import com.example.teamslime.Slime.db.TransactionHeaderDB;
import com.example.teamslime.Slime.model.Address;
import com.example.teamslime.Slime.model.TransactionHeader;

public class MyShoppingDetail extends AppCompatActivity {

    RecyclerView recyclerMySHoppingListDetail;
    ShoppingDetailAdapter shoppingDetailAdapter;

    TransactionHeaderDB transactionHeaderDB;
    AddressDB addressDB;

    TextView textShoppingDetailDate, textShoppingDetailSubTotal, textShoppingDetailShippingCost, textShoppingDetailTotal, textShoppingDetailQty;
    TextView textShoppingDetailFullName, textShoppingDetailPhoneNumber, textShoppingDetailAddressDetail, textShoppingDetailProvince;
    ImageView btnMyShoppingDetailBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shopping_detail);

        Intent intent = getIntent();

        int transactionID = intent.getIntExtra("transactionID", 0);
        boolean isFreeShipping = intent.getBooleanExtra("isFreeShipping", false);
        int subTotal = intent.getIntExtra("subTotal", 0);
        int qty = intent.getIntExtra("qty", 0);

        transactionHeaderDB = new TransactionHeaderDB(MyShoppingDetail.this);
        TransactionHeader transactionHeader = transactionHeaderDB.loadSpecificTransactionHeader(transactionID);

        addressDB = new AddressDB(MyShoppingDetail.this);
        Address address = addressDB.loadSpecificAddress(transactionHeader.addressID);

        btnMyShoppingDetailBack = findViewById(R.id.btnMyShoppingDetailBack);
        textShoppingDetailDate = findViewById(R.id.textShoppingDetailDate);
        textShoppingDetailSubTotal = findViewById(R.id.textShoppingDetailSubTotal);
        textShoppingDetailShippingCost = findViewById(R.id.textShoppingDetailShippingCost);
        textShoppingDetailTotal = findViewById(R.id.textShoppingDetailTotal);
        textShoppingDetailQty = findViewById(R.id.textShoppingDetailQty);
        textShoppingDetailFullName = findViewById(R.id.textShoppingDetailFullName);
        textShoppingDetailPhoneNumber = findViewById(R.id.textShoppingDetailPhoneNumber);
        textShoppingDetailAddressDetail = findViewById(R.id.textShoppingDetailAddressDetail);
        textShoppingDetailProvince = findViewById(R.id.textShoppingDetailProvince);
        recyclerMySHoppingListDetail = findViewById(R.id.recyclerMySHoppingListDetail);

        textShoppingDetailDate.setText("Date Purchased : " + transactionHeader.date);
        textShoppingDetailSubTotal.setText("Rp" + subTotal);
        if(isFreeShipping){
            textShoppingDetailShippingCost.setText("Free");
            textShoppingDetailTotal.setText("Rp" + subTotal);
        }else{
            int total = subTotal + 10000;
            textShoppingDetailShippingCost.setText("Rp10000");
            textShoppingDetailTotal.setText("Rp" + total);
        }
        textShoppingDetailQty.setText(qty + " items purchased");

        textShoppingDetailFullName.setText(address.fullName);
        textShoppingDetailPhoneNumber.setText(address.phoneNumber);
        textShoppingDetailAddressDetail.setText(address.detail);

        String province = address.province + ", " + address.district + ", " + address.subDistrict + ", " + address.postalCode;
        province = province.toUpperCase();
        textShoppingDetailProvince.setText(province);

        //RV
        LinearLayoutManager llManagerShoppingDetail = new LinearLayoutManager(MyShoppingDetail.this, LinearLayoutManager.VERTICAL, false);
        recyclerMySHoppingListDetail.setLayoutManager(llManagerShoppingDetail);

        shoppingDetailAdapter = new ShoppingDetailAdapter(MyShoppingDetail.this);
        recyclerMySHoppingListDetail.setAdapter(shoppingDetailAdapter);

        btnMyShoppingDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransactionDetailDB.specificTransactionDetail.clear();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        TransactionDetailDB.specificTransactionDetail.clear();
        super.onBackPressed();
    }
}