package com.example.teamslime.Slime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.teamslime.Slime.adapter.SnackCategoryAdapter;
import com.example.teamslime.Slime.adapter.SliderAdapter;
import com.example.teamslime.Slime.adapter.SnackItemAdapter;
import com.example.teamslime.Slime.db.AddressDB;
import com.example.teamslime.Slime.db.CartDB;
import com.example.teamslime.Slime.db.SnackCategoryDB;
import com.example.teamslime.Slime.db.SnacksDB;
import com.example.teamslime.Slime.db.TransactionDetailDB;
import com.example.teamslime.Slime.db.TransactionHeaderDB;
import com.example.teamslime.Slime.db.UsersDB;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;


public class HomePage extends AppCompatActivity {

    ImageView imageUserProfile, imageShoppingCart;
    EditText editSearchSnack;

    //    pop up
    Dialog popUpDialog;

    //    banner
    SliderView sliderView;
    int[] images = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3};
    SliderAdapter sliderAdapter;

    RecyclerView rvSnackCategory, rvSnackItem;
    SnackCategoryAdapter snackCategoryAdapter;
    public static SnackItemAdapter snackItemAdapter;

    AddressDB addressDB;
    SnackCategoryDB snackCategoryDB;
    SnacksDB snacksDB;
    CartDB cartDB;
    TransactionHeaderDB transactionHeaderDB;
    TransactionDetailDB transactionDetailDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        imageUserProfile = findViewById(R.id.imageUserProfile);
        imageShoppingCart = findViewById(R.id.imageShoppingCart);
        editSearchSnack = findViewById(R.id.editSearchSnack);


        snackCategoryDB = new SnackCategoryDB(HomePage.this);
        snackCategoryDB.loadSnackCategory(HomePage.this);
        snacksDB = new SnacksDB(HomePage.this);
        snacksDB.loadSnack(HomePage.this);

        //load address
        addressDB = new AddressDB(HomePage.this);
        addressDB.loadAddress(HomePage.this);
        addressDB.loadCurrentAddress(HomePage.this, UsersDB.activeUser.ID);

        // load cart
        cartDB = new CartDB(HomePage.this);
        cartDB.loadCart(HomePage.this);
        cartDB.loadCurrentCart(HomePage.this, UsersDB.activeUser.ID);

        //load trans head
        transactionHeaderDB = new TransactionHeaderDB(HomePage.this);
        transactionHeaderDB.loadTransactionHeader(HomePage.this);
        transactionHeaderDB.loadCurrentTransactionHeader(HomePage.this, UsersDB.activeUser.ID);

        //load trans detail
        transactionDetailDB = new TransactionDetailDB(HomePage.this);
        transactionDetailDB.loadTransactionDetail(HomePage.this);
        transactionDetailDB.loadCurrentTransactionDetail(HomePage.this, UsersDB.activeUser.ID);

//        pop up
        if (!Login.POP_UP_HAS_BEEN_SHOWED) {
            popUpDialog = new Dialog(this);

            popUpDialog.setContentView(R.layout.custom_pop_up);
            popUpDialog.show();
        }
        Login.POP_UP_HAS_BEEN_SHOWED = true;

//        Slider
        sliderView = findViewById(R.id.sliderView);

        sliderAdapter = new SliderAdapter(images);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.startAutoCycle();

//        RV Snack Category
        rvSnackCategory = findViewById(R.id.recyclerViewSnackCategory);

        LinearLayoutManager llManagerSnackCategory = new LinearLayoutManager(HomePage.this, LinearLayoutManager.HORIZONTAL, false);
        rvSnackCategory.setLayoutManager(llManagerSnackCategory);

        snackCategoryAdapter = new SnackCategoryAdapter(HomePage.this);
        rvSnackCategory.setAdapter(snackCategoryAdapter);

//        RV Snack Item
        rvSnackItem = findViewById(R.id.recyclerViewSnack);
        LinearLayoutManager llManagerSnackItem = new LinearLayoutManager(HomePage.this, LinearLayoutManager.VERTICAL, false);
        rvSnackItem.setLayoutManager(llManagerSnackItem);

        snackItemAdapter = new SnackItemAdapter(HomePage.this);
        rvSnackItem.setAdapter(snackItemAdapter);


        // Klik user profile
        imageUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, UserProfile.class);
                startActivity(intent);
            }
        });

        // Klik Shopping Cart
        imageShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, CartView.class);
                startActivity(intent);
            }
        });

        editSearchSnack.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction() == 1){
                    hideSoftKeyboard(view);

                    String keyword = editSearchSnack.getText().toString();

                    snacksDB.loadSearchSnack(keyword);

                    snackItemAdapter.notifyDataSetChanged();

                    return true;
                }

                return false;
            }
        });
    }

    public void hideSoftKeyboard(View view){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);

        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(HomePage.this)
                .setMessage("Are you sure want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}