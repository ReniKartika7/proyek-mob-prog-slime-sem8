package com.example.reni.Slime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.reni.Slime.db.UsersDB;

public class UserProfile extends AppCompatActivity {

    LinearLayout layoutEditProfile;
    TextView textUserName;
    ImageView imageUserProfile, btnSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        layoutEditProfile = findViewById(R.id.layoutEditProfile);
        textUserName = findViewById(R.id.textUserName);
        imageUserProfile = findViewById(R.id.imageUserProfile);
        btnSetting = findViewById(R.id.buttonUserSetting);

        if(UsersDB.activeUser.gender.equals("Female")){
            imageUserProfile.setImageResource(R.drawable.female);
        }else if(UsersDB.activeUser.gender.equals("Male")){
            imageUserProfile.setImageResource(R.drawable.male);
        }

        textUserName.setText(UsersDB.activeUser.name);

        layoutEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfile.this, EditProfile.class);
                startActivity(intent);
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfile.this, Setting.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UserProfile.this, HomePage.class);
        startActivity(intent);
        finish();
    }
}