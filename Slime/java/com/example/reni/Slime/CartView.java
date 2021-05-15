package com.example.reni.Slime;

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

import com.example.reni.Slime.adapter.CartAdapter;
import com.example.reni.Slime.db.CartDB;
import com.example.reni.Slime.db.SnacksDB;
import com.example.reni.Slime.model.Cart;
import com.example.reni.Slime.model.Snack;

public class CartView extends AppCompatActivity {

    ImageView btnMyCartBack;
    TextView textMyCartCountProduct, textMyCartPriceTotal;
    Button btnCheckOut;
    RecyclerView rvMyCart;

    CartDB cartDB;
    SnacksDB snacksDB;

    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_view);

        cartDB = new CartDB(CartView.this);
        snacksDB = new SnacksDB(CartView.this);

        btnMyCartBack = findViewById(R.id.btnMyCartBack);
        textMyCartCountProduct = findViewById(R.id.textMyCartCountProduct);
        textMyCartPriceTotal = findViewById(R.id.textMyCartPriceTotal);
        btnCheckOut = findViewById(R.id.btnCheckOut);
        rvMyCart = findViewById(R.id.recyclerMyCart);

        textMyCartCountProduct.setText(CartDB.currentCart.size() + " PRODUK TERPILIH");

        int totalPrice = 0;

        for(Cart cart : CartDB.currentCart){
            Snack snack;
            snack = snacksDB.loadSpecificSnack(cart.snackID);

            int price = snack.price;

            totalPrice = totalPrice + price * cart.quantity;
        }

        textMyCartPriceTotal.setText("Sub Total : Rp" + totalPrice);

        // RV
        LinearLayoutManager llManagerMyCart = new LinearLayoutManager(CartView.this, LinearLayoutManager.VERTICAL, false);
        rvMyCart.setLayoutManager(llManagerMyCart);

        cartAdapter = new CartAdapter(CartView.this, new CartAdapter.onSubTotalChangeListener() {
            @Override
            public void onSubTotalChange() {
                int totalPrice = 0;

                for(Cart cart : CartDB.currentCart){
                    Snack snack = new Snack();
                    snack = snacksDB.loadSpecificSnack(cart.snackID);

                    int price = snack.price;

                    totalPrice = totalPrice + price * cart.quantity;
                }

                textMyCartPriceTotal.setText("Sub Total : Rp" + totalPrice);
                textMyCartCountProduct.setText(CartDB.currentCart.size() + " PRODUK TERPILIH");
            }
        });
        rvMyCart.setAdapter(cartAdapter);

        btnMyCartBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartView.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });

        final int finalTotalPrice = totalPrice;
        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(finalTotalPrice == 0){
                    Toast.makeText(CartView.this, "Your Cart is Empty !", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(CartView.this, CheckOut.class);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CartView.this, HomePage.class);
        startActivity(intent);
        finish();
    }
}