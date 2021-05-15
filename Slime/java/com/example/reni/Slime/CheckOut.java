package com.example.reni.Slime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reni.Slime.adapter.OrderAdapter;
import com.example.reni.Slime.db.AddressDB;
import com.example.reni.Slime.db.CartDB;
import com.example.reni.Slime.db.SnacksDB;
import com.example.reni.Slime.db.TransactionDetailDB;
import com.example.reni.Slime.db.TransactionHeaderDB;
import com.example.reni.Slime.db.UsersDB;
import com.example.reni.Slime.model.Address;
import com.example.reni.Slime.model.Cart;
import com.example.reni.Slime.model.Snack;
import com.example.reni.Slime.model.TransactionDetail;
import com.example.reni.Slime.model.TransactionHeader;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckOut extends AppCompatActivity {

    ImageView btnCheckOutBack;
    TextView textCheckOutAddress, textCheckOutSubTotal, textCheckOutShoppingCost, textCheckOutTotal;
    RecyclerView recyclerViewCheckOut;
    Button btnBuyNow;

    OrderAdapter orderAdapter;

    SnacksDB snacksDB;
    CartDB cartDB;
    AddressDB addressDB;
    TransactionHeaderDB transactionHeaderDB;
    TransactionDetailDB transactionDetailDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        snacksDB = new SnacksDB(CheckOut.this);
        cartDB = new CartDB(CheckOut.this);

        btnCheckOutBack = findViewById(R.id.btnCheckOutBack);
        textCheckOutAddress = findViewById(R.id.textCheckOutAddress);
        textCheckOutSubTotal = findViewById(R.id.textCheckOutSubTotal);
        textCheckOutShoppingCost = findViewById(R.id.textCheckOutShoppingCost);
        textCheckOutTotal = findViewById(R.id.textCheckOutTotal);
        recyclerViewCheckOut = findViewById(R.id.recyclerViewCheckOut);
        btnBuyNow = findViewById(R.id.btnBuyNow);

        //address
        addressDB = new AddressDB(CheckOut.this);
        Intent intent = getIntent();

        final int addressID = intent.getIntExtra("addressID", 0);

        if (addressID == 0) {
            textCheckOutAddress.setText("Choose Your Address in here");
        } else {
            Address address = addressDB.loadSpecificAddress(addressID);

            String province = address.province + ", " + address.district + ", " + address.subDistrict + ", " + address.postalCode;
            province = province.toUpperCase();

            textCheckOutAddress.setText(address.fullName + "\n" + address.phoneNumber + "\n" +
                    address.detail + "\n" + province);
        }

        // RV
        LinearLayoutManager llManagerOrderSummary = new LinearLayoutManager(CheckOut.this, LinearLayoutManager.VERTICAL, false);
        recyclerViewCheckOut.setLayoutManager(llManagerOrderSummary);

        orderAdapter = new OrderAdapter(CheckOut.this);
        recyclerViewCheckOut.setAdapter(orderAdapter);

        // subtotal dkk
        int subTotal, total;
        subTotal = 0;

        Snack snack;
        if (CartDB.IS_CHECKED_BUY_NOW == false) {
            for (Cart cart : CartDB.currentCart) {
                snack = snacksDB.loadSpecificSnack(cart.snackID);
                int price = snack.price;
                subTotal = subTotal + price * cart.quantity;
            }
        } else {
            Cart cart = CartDB.vBuy;
            snack = snacksDB.loadSpecificSnack(cart.snackID);

            subTotal = snack.price * cart.quantity;
        }

        textCheckOutSubTotal.setText("Rp" + subTotal);

        if (subTotal >= 120000) {
            textCheckOutShoppingCost.setText("Free");
            total = subTotal;
        } else {
            total = subTotal + 10000;
            textCheckOutShoppingCost.setText("Rp10000");
        }
        textCheckOutTotal.setText("Rp" + total);

        btnCheckOutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CartDB.IS_CHECKED_BUY_NOW == false) {
                    Intent intent = new Intent(CheckOut.this, CartView.class);
                    startActivity(intent);
                    finish();
                } else {
                    CartDB.IS_CHECKED_BUY_NOW = false;
                    CartDB.vBuy = null;
                    Intent intent = new Intent(CheckOut.this, SnackItemBuy.class);
                    intent.putExtra("snackPosition", SnackItemBuy.position);
                    startActivity(intent);
                    finish();
                }
            }
        });

        textCheckOutAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckOut.this, ChooseAddress.class);
                startActivity(intent);
            }
        });

        btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addressID != 0) {
                    new AlertDialog.Builder(CheckOut.this)
                            .setMessage("Are you sure want to buy now?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                    Date date = new Date();

                                    TransactionHeader transactionHeader = new TransactionHeader();
                                    transactionHeaderDB = new TransactionHeaderDB(CheckOut.this);

                                    transactionHeader.userID = UsersDB.activeUser.ID;
                                    transactionHeader.date = simpleDateFormat.format(date);
                                    transactionHeader.addressID = addressID;

                                    transactionHeaderDB.insertTransactionHeader(transactionHeader);
                                    transactionHeaderDB.loadTransactionHeader(CheckOut.this);
                                    transactionHeaderDB.loadCurrentTransactionHeader(CheckOut.this, UsersDB.activeUser.ID);

                                    int currentID = transactionHeaderDB.getLastTransactionID(CheckOut.this);

                                    TransactionDetail transactionDetail = new TransactionDetail();
                                    transactionDetailDB = new TransactionDetailDB(CheckOut.this);

                                    if (CartDB.IS_CHECKED_BUY_NOW == false) {
                                        for (Cart cart : CartDB.currentCart) {
                                            Snack snack1 = snacksDB.loadSpecificSnack(cart.snackID);
                                            snack1.stock = snack1.stock - cart.quantity;

                                            snacksDB.updateSnack(snack1);
                                            snacksDB.loadSnack(CheckOut.this);

                                            for (Cart carts : CartDB.vCart) {
                                                if (carts.snackID == cart.snackID) {
                                                    Snack snack2 = snacksDB.loadSpecificSnack(carts.snackID);

                                                    if (snack2.stock == 0) {
                                                        cartDB.deleteSpecificCartMyCart(carts.ID);
                                                    } else if (snack2.stock < carts.quantity) {
                                                        carts.quantity = snack2.stock;

                                                        cartDB.updateCartMyCart(CheckOut.this, carts);
                                                    }
                                                }
                                            }

                                            transactionDetail.transactionID = currentID;
                                            transactionDetail.snackID = cart.snackID;
                                            transactionDetail.quantity = cart.quantity;

                                            transactionDetailDB.insertTransactionDetail(transactionDetail);
                                        }

                                        // delete currentCart
                                        cartDB.deleteSpecificCart(UsersDB.activeUser.ID);

                                    } else {
                                        Snack snack1 = snacksDB.loadSpecificSnack(CartDB.vBuy.snackID);
                                        snack1.stock = snack1.stock - CartDB.vBuy.quantity;

                                        snacksDB.updateSnack(snack1);
                                        snacksDB.loadSnack(CheckOut.this);

                                        for (Cart carts : CartDB.vCart) {
                                            if (carts.snackID == CartDB.vBuy.snackID) {
                                                Snack snack2 = snacksDB.loadSpecificSnack(carts.snackID);

                                                if (snack2.stock == 0) {
                                                    cartDB.deleteSpecificCartMyCart(carts.ID);
                                                } else if (snack2.stock < carts.quantity) {
                                                    carts.quantity = snack2.stock;

                                                    cartDB.updateCartMyCart(CheckOut.this, carts);
                                                }
                                            }
                                        }

                                        transactionDetail.transactionID = currentID;
                                        transactionDetail.snackID = CartDB.vBuy.snackID;
                                        transactionDetail.quantity = CartDB.vBuy.quantity;

                                        transactionDetailDB.insertTransactionDetail(transactionDetail);
                                    }


                                    cartDB.loadCurrentCart(CheckOut.this, UsersDB.activeUser.ID);
                                    cartDB.loadCart(CheckOut.this);

                                    transactionDetailDB.loadCurrentTransactionDetail(CheckOut.this, UsersDB.activeUser.ID);
                                    transactionDetailDB.loadTransactionDetail(CheckOut.this);

                                    CartDB.IS_CHECKED_BUY_NOW = false;
                                    CartDB.vBuy = null;

                                    Toast.makeText(CheckOut.this, "Thankyou for your purchase  !", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(CheckOut.this, HomePage.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                } else {
                    Toast.makeText(CheckOut.this, "Please choose your shipping address !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (CartDB.IS_CHECKED_BUY_NOW == false) {
            Intent intent = new Intent(CheckOut.this, CartView.class);
            startActivity(intent);
            finish();
        } else {
            CartDB.IS_CHECKED_BUY_NOW = false;
            CartDB.vBuy = null;
            Intent intent = new Intent(CheckOut.this, SnackItemBuy.class);
            intent.putExtra("snackPosition", SnackItemBuy.position);
            startActivity(intent);
            finish();
        }
    }
}