package com.example.teamslime.Slime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teamslime.Slime.db.SnackCategoryDB;
import com.example.teamslime.Slime.db.SnacksDB;
import com.example.teamslime.Slime.db.UsersDB;
import com.example.teamslime.Slime.util.APIManagerSnack;
import com.example.teamslime.Slime.util.APIManagerSnackCategory;
import com.example.teamslime.Slime.util.PreferenceUtils;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 2500;

    Animation topAnimation, bottomAnimation;
    TextView Title, Subtitle;
    ImageView Logo;
    SnackCategoryDB snackCategoryDB;
    SnacksDB snacksDB;
    UsersDB usersDB;

    APIManagerSnackCategory apiManagerSnackCategory;
    APIManagerSnack apiManagerSnack;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String SNACK_CATEGORY = "snackCategory";
    public static final String SNACK = "snack";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bottom_animation);

        Title = findViewById(R.id.txtTitle);
        Subtitle = findViewById(R.id.txtSubtitle);
        Logo = findViewById(R.id.imgLogo);

        Title.setAnimation(topAnimation);
        Subtitle.setAnimation(topAnimation);
        Logo.setAnimation(bottomAnimation);

        //SP
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SnackCategoryDB.SNACK_CATEGORY_HAS_INSERTED = sharedPreferences.getBoolean(SNACK_CATEGORY, false);
        SnacksDB.SNACK_HAS_INSERTED = sharedPreferences.getBoolean(SNACK, false);

        // load user
        usersDB = new UsersDB(MainActivity.this);
        usersDB.loadUser(MainActivity.this);

        // load snack category
        apiManagerSnackCategory = new APIManagerSnackCategory(MainActivity.this);

        snackCategoryDB = new SnackCategoryDB(MainActivity.this);

        if(SnackCategoryDB.SNACK_CATEGORY_HAS_INSERTED == false){
            APIManagerSnackCategory.viewSnackCategory(MainActivity.this);
        }
        SnackCategoryDB.SNACK_CATEGORY_HAS_INSERTED = true;

        //load snack
        apiManagerSnack = new APIManagerSnack(MainActivity.this);
        snacksDB = new SnacksDB(MainActivity.this);

        if(SnacksDB.SNACK_HAS_INSERTED == false){
            APIManagerSnack.viewSnackItem(MainActivity.this);
        }
        SnacksDB.SNACK_HAS_INSERTED = true;

        //SP
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SNACK_CATEGORY, true);
        editor.putBoolean(SNACK, true);
        editor.commit();



        if(PreferenceUtils.getLoginUser(MainActivity.this) != 0){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    UsersDB usersDB = new UsersDB(MainActivity.this);

                    usersDB.loadCurrentUser(PreferenceUtils.getLoginUser(MainActivity.this));

                    Intent intent = new Intent(MainActivity.this, HomePage.class);
                    startActivity(intent);
                    finish();
                }
            }, SPLASH_SCREEN);
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, AppsIntroduction.class);
                    startActivity(intent);
                    finish();
                }
            }, SPLASH_SCREEN);
        }
    }
}