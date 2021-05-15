package com.example.reni.Slime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.reni.Slime.adapter.SnackCategoryAdapter;
import com.example.reni.Slime.adapter.SliderAdapter;
import com.example.reni.Slime.adapter.SnackItemAdapter;
import com.example.reni.Slime.db.SnackCategoryDB;
import com.example.reni.Slime.db.SnacksDB;
import com.example.reni.Slime.model.Snack;
import com.example.reni.Slime.model.SnackCategory;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;


public class HomePage extends AppCompatActivity {

    ImageView imageUserProfile, imageShoppingCart;

//    pop up
    Dialog popUpDialog;

//    banner
    SliderView sliderView;
    int[] images = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3};
    SliderAdapter sliderAdapter;

    RecyclerView rvSnackCategory, rvSnackItem;
    SnackCategoryAdapter snackCategoryAdapter;
    public static SnackItemAdapter snackItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        imageUserProfile = findViewById(R.id.imageUserProfile);
        imageShoppingCart = findViewById(R.id.imageShoppingCart);

        for(Snack snack : SnacksDB.currentSnackItem){
            Log.i("SNACK", snack.name + ", " + snack.price + ", " + snack.categoryID);
        }

        for(SnackCategory snackCategory : SnackCategoryDB.vSnackCategory){
            Log.i("SNACK", snackCategory.name + ", " + snackCategory.ID);
        }

//        pop up
        if(!Login.POP_UP_HAS_BEEN_SHOWED){
            popUpDialog = new Dialog(this);
            TextView txtPopUpClose;

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


//        Klik user profile
        imageUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, UserProfile.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(HomePage.this)
                .setMessage("Are you sure want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        HomePage.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}