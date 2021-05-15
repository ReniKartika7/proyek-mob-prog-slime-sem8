package com.example.reni.Slime;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reni.Slime.db.CartDB;
import com.example.reni.Slime.db.SnacksDB;
import com.example.reni.Slime.db.UsersDB;
import com.example.reni.Slime.model.Cart;
import com.example.reni.Slime.model.Snack;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class SnackItemBuy extends AppCompatActivity {

    ImageView backIcon, imageSnackBuy;
    TextView textSnackBuyName, textSnackBuyPrice, textSnackBuDetail;
    LinearLayout buttonAddNow, buttonBuyNow;

    public static int position;

    //    pop up
    Dialog popUpDialog;

    CartDB cartDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snack_item_buy);
        cartDB = new CartDB(SnackItemBuy.this);

        backIcon = findViewById(R.id.imageBackIcon);
        imageSnackBuy = findViewById(R.id.imageSnackBuy);
        textSnackBuyName = findViewById(R.id.textSnackBuyName);
        textSnackBuyPrice = findViewById(R.id.textSnackBuyPrice);
        textSnackBuDetail = findViewById(R.id.textSnackBuDetail);
        buttonAddNow = findViewById(R.id.buttonAddNow);
        buttonBuyNow = findViewById(R.id.buttonBuyNow);

        Intent intent = getIntent();

        position = intent.getIntExtra("snackPosition", -1);

        final Snack snack = SnacksDB.currentSnackItem.get(position);

        final String url = snack.coverUrl;
        Picasso.get().load(url).resize(200,200).into(imageSnackBuy, new Callback() {
            @Override
            public void onSuccess() {
                //progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {

            }
        });

        textSnackBuyName.setText(snack.name);
        textSnackBuyPrice.setText("Rp" + snack.price);
        textSnackBuDetail.setText(snack.detail);

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
                popUpDialog = new Dialog(SnackItemBuy.this);

                popUpDialog.setContentView(R.layout.custom_add_cart_pop_up);
                Objects.requireNonNull(popUpDialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                Window window = popUpDialog.getWindow();
                WindowManager.LayoutParams params = window.getAttributes();
                params.gravity = Gravity.BOTTOM;
                window.setAttributes(params);


                ImageView imageAddCartPopUp;
                final TextView textAddCartPopUpPrice, textAddCartPopUpStock, numberPopUpAddCart;
                final Button decrementPopUpAddCart, incrementPopUpAddCart, btnAddToCart;

                imageAddCartPopUp = popUpDialog.findViewById(R.id.imageAddCartPopUp);
                textAddCartPopUpPrice = popUpDialog.findViewById(R.id.textAddCartPopUpPrice);
                textAddCartPopUpStock = popUpDialog.findViewById(R.id.textAddCartPopUpStock);
                decrementPopUpAddCart = popUpDialog.findViewById(R.id.decrementPopUpAddCart);
                incrementPopUpAddCart = popUpDialog.findViewById(R.id.incrementPopUpAddCart);
                numberPopUpAddCart = popUpDialog.findViewById(R.id.numberPopUpAddCart);
                btnAddToCart = popUpDialog.findViewById(R.id.btnAddToCart);

                Picasso.get().load(url).resize(200,200).into(imageAddCartPopUp, new Callback() {
                    @Override
                    public void onSuccess() {
                        //progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

                textAddCartPopUpPrice.setText("Rp" + snack.price);
                textAddCartPopUpStock.setText("Stock : " + snack.stock);

                int currentCartNumber = 0;
                int currentCartCapacity = 0;

                for(Cart carts : CartDB.currentCart){
                    if(carts.snackID == snack.ID){
                        currentCartNumber = carts.quantity;
                        break;
                    }
                }

                currentCartCapacity = snack.stock - currentCartNumber;

                popUpDialog.show();

                int number;
                number = Integer.parseInt(numberPopUpAddCart.getText().toString());
                if(number==0&& currentCartCapacity == 0){
                    decrementPopUpAddCart.setTextColor(getResources().getColor(R.color.white));
                    incrementPopUpAddCart.setTextColor(getResources().getColor(R.color.white));
                }


                final int finalCurrentCartCapacity = currentCartCapacity;
                final int finalCurrentCartCapacity1 = currentCartCapacity;
                decrementPopUpAddCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int number;
                        number = Integer.parseInt(numberPopUpAddCart.getText().toString());
                        if(number == 0 && finalCurrentCartCapacity1 == 0){
                            decrementPopUpAddCart.setTextColor(getResources().getColor(R.color.white));
                            incrementPopUpAddCart.setTextColor(getResources().getColor(R.color.white));
                        }else if(number == 0){
                            decrementPopUpAddCart.setTextColor(getResources().getColor(R.color.white));
                            incrementPopUpAddCart.setTextColor(getResources().getColor(R.color.black));
                        }else{
                            if(number == 1 && finalCurrentCartCapacity > 1){
                                decrementPopUpAddCart.setTextColor(getResources().getColor(R.color.white));
                                incrementPopUpAddCart.setTextColor(getResources().getColor(R.color.black));
                            }else{
                                decrementPopUpAddCart.setTextColor(getResources().getColor(R.color.black));
                                incrementPopUpAddCart.setTextColor(getResources().getColor(R.color.black));

                            }
                            number = number - 1;
                            numberPopUpAddCart.setText(number + "");
                        }
                    }
                });

                incrementPopUpAddCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int number;
                        number = Integer.parseInt(numberPopUpAddCart.getText().toString());
                        if(number==0 && finalCurrentCartCapacity1 == 0){
                            decrementPopUpAddCart.setTextColor(getResources().getColor(R.color.white));
                            incrementPopUpAddCart.setTextColor(getResources().getColor(R.color.white));
                        }else if(number==finalCurrentCartCapacity){
                            decrementPopUpAddCart.setTextColor(getResources().getColor(R.color.black));
                            incrementPopUpAddCart.setTextColor(getResources().getColor(R.color.white));
                        }else{
                            if(number == (finalCurrentCartCapacity - 1)){
                                decrementPopUpAddCart.setTextColor(getResources().getColor(R.color.black));
                                incrementPopUpAddCart.setTextColor(getResources().getColor(R.color.white));
                            }else{
                                decrementPopUpAddCart.setTextColor(getResources().getColor(R.color.black));
                                incrementPopUpAddCart.setTextColor(getResources().getColor(R.color.black));
                            }
                            number = number + 1;
                            numberPopUpAddCart.setText(number + "");
                        }
                    }
                });

                btnAddToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int number;
                        number = Integer.parseInt(numberPopUpAddCart.getText().toString());

                        if(number > 0){
                            Cart cart;

                            cart = new Cart();
                            cart.userID = UsersDB.activeUser.ID;
                            cart.snackID = snack.ID;
                            cart.quantity = number;

                            int flag = 0;

                            for(Cart carts : CartDB.currentCart){
                                if(carts.snackID == cart.snackID){
                                    cart.ID = carts.ID;
                                    cartDB.updateCart(SnackItemBuy.this, cart);
                                    flag = 1;
                                    break;
                                }
                            }

                            if(flag == 0){
                                cartDB.insertCart(cart);
                            }

                            cartDB.loadCart(SnackItemBuy.this);
                            cartDB.loadCurrentCart(SnackItemBuy.this, UsersDB.activeUser.ID);

                            Toast.makeText(SnackItemBuy.this, "Successfully added to cart !", Toast.LENGTH_SHORT).show();

                            popUpDialog.cancel();
                        }else{
                            Toast.makeText(SnackItemBuy.this, "You have to choose at least 1 pcs !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        buttonBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpDialog = new Dialog(SnackItemBuy.this);

                popUpDialog.setContentView(R.layout.custom_buy_now_pop_up);
                Objects.requireNonNull(popUpDialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                Window window = popUpDialog.getWindow();
                WindowManager.LayoutParams params = window.getAttributes();
                params.gravity = Gravity.BOTTOM;
                window.setAttributes(params);

                ImageView imageBuyNowPopUp;
                final TextView textBuyNowPopUpPrice, textBuyNowPopUpStock, numberPopUpBuyNow;
                final Button decrementPopUpBuyNow, incrementPopUpBuyNow, btnBuyNow;

                imageBuyNowPopUp = popUpDialog.findViewById(R.id.imageBuyNowPopUp);
                textBuyNowPopUpPrice = popUpDialog.findViewById(R.id.textBuyNowPopUpPrice);
                textBuyNowPopUpStock = popUpDialog.findViewById(R.id.textBuyNowPopUpStock);
                decrementPopUpBuyNow = popUpDialog.findViewById(R.id.decrementPopUpBuyNow);
                incrementPopUpBuyNow = popUpDialog.findViewById(R.id.incrementPopUpBuyNow);
                numberPopUpBuyNow = popUpDialog.findViewById(R.id.numberPopUpBuyNow);
                btnBuyNow = popUpDialog.findViewById(R.id.btnBuyNow);

                Picasso.get().load(url).resize(200,200).into(imageBuyNowPopUp, new Callback() {
                    @Override
                    public void onSuccess() {
                        //progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

                textBuyNowPopUpPrice.setText("Rp" + snack.price);
                textBuyNowPopUpStock.setText("Stock : " + snack.stock);


                popUpDialog.show();

                decrementPopUpBuyNow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int number;
                        number = Integer.parseInt(numberPopUpBuyNow.getText().toString());
                        if(snack.stock==1){
                            decrementPopUpBuyNow.setTextColor(getResources().getColor(R.color.white));
                            incrementPopUpBuyNow.setTextColor(getResources().getColor(R.color.white));
                        }else if(number==1){
                            decrementPopUpBuyNow.setTextColor(getResources().getColor(R.color.white));
                            incrementPopUpBuyNow.setTextColor(getResources().getColor(R.color.black));
                        }else{
                            if(number == 2 && snack.stock > 2){
                                decrementPopUpBuyNow.setTextColor(getResources().getColor(R.color.white));
                                incrementPopUpBuyNow.setTextColor(getResources().getColor(R.color.black));
                            }else{
                                decrementPopUpBuyNow.setTextColor(getResources().getColor(R.color.black));
                                incrementPopUpBuyNow.setTextColor(getResources().getColor(R.color.black));

                            }
                            number = number - 1;
                            numberPopUpBuyNow.setText(number + "");
                        }
                    }
                });

                incrementPopUpBuyNow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int number;
                        number = Integer.parseInt(numberPopUpBuyNow.getText().toString());
                        if(snack.stock==1){
                            decrementPopUpBuyNow.setTextColor(getResources().getColor(R.color.white));
                            incrementPopUpBuyNow.setTextColor(getResources().getColor(R.color.white));
                        }else if(number==snack.stock){
                            decrementPopUpBuyNow.setTextColor(getResources().getColor(R.color.black));
                            incrementPopUpBuyNow.setTextColor(getResources().getColor(R.color.white));
                        }else{
                            if(number == (snack.stock - 1)){
                                decrementPopUpBuyNow.setTextColor(getResources().getColor(R.color.black));
                                incrementPopUpBuyNow.setTextColor(getResources().getColor(R.color.white));
                            }else{
                                decrementPopUpBuyNow.setTextColor(getResources().getColor(R.color.black));
                                incrementPopUpBuyNow.setTextColor(getResources().getColor(R.color.black));
                            }
                            number = number + 1;
                            numberPopUpBuyNow.setText(number + "");
                        }
                    }
                });

                btnBuyNow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        new AlertDialog.Builder(SnackItemBuy.this)
                                .setMessage("Are you sure want to buy now?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        int number;
                                        number = Integer.parseInt(numberPopUpBuyNow.getText().toString());

                                        Cart cart;
                                        cart = new Cart();
                                        cart.userID = UsersDB.activeUser.ID;
                                        cart.snackID = snack.ID;
                                        cart.quantity = number;

                                        CartDB.vBuy = cart;
                                        CartDB.IS_CHECKED_BUY_NOW = true;

                                        Intent intent = new Intent(SnackItemBuy.this, CheckOut.class);
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton("No", null)
                                .show();
                    }
                });
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SnackItemBuy.this, HomePage.class);
        startActivity(intent);
        finish();
    }
}