package com.example.reni.Slime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.reni.Slime.db.SnacksDB;
import com.example.reni.Slime.model.Snack;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class SnackItemBuy extends AppCompatActivity {

    ImageView backIcon, imageSnackBuy;
    TextView textSnackBuyName, textSnackBuyPrice;
    LinearLayout buttonAddNow, buttonBuyNow;

    int position;

    SnacksDB snacksDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snack_item_buy);

        backIcon = findViewById(R.id.imageBackIcon);
        imageSnackBuy = findViewById(R.id.imageSnackBuy);
        textSnackBuyName = findViewById(R.id.textSnackBuyName);
        textSnackBuyPrice = findViewById(R.id.textSnackBuyPrice);
        buttonAddNow = findViewById(R.id.buttonAddNow);
        buttonBuyNow = findViewById(R.id.buttonBuyNow);

        Intent intent = getIntent();

        position = intent.getIntExtra("snackPosition", -1);

        Snack snack = SnacksDB.currentSnackItem.get(position);

        String url = snack.coverUrl;
        Picasso.get().load(url).resize(200,200).into(imageSnackBuy, new Callback() {
            @Override
            public void onSuccess() {
                //progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                Log.i("PICASSO", "OMO");

            }
        });

        textSnackBuyName.setText(snack.name);
        textSnackBuyPrice.setText("Rp" + snack.price);

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SnackItemBuy.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });

        buttonAddNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}