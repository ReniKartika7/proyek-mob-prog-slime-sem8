package com.example.reni.Slime;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.reni.Slime.db.UsersDB;
import com.example.reni.Slime.util.PreferenceUtils;

public class Setting extends AppCompatActivity {

    ImageView btnSettingBack;
    LinearLayout buttonSettingChangePassword,  buttonSettingMyProfile, buttonSettingMyAddress, buttonLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        btnSettingBack = findViewById(R.id.btnSettingBack);
        buttonSettingChangePassword = findViewById(R.id.buttonSettingChangePassword);
        buttonSettingMyProfile = findViewById(R.id.buttonSettingMyProfile);
        buttonSettingMyAddress = findViewById(R.id.buttonSettingMyAddress);
        buttonLogOut = findViewById(R.id.buttonLogOut);

        btnSettingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Setting.this, UserProfile.class);
                startActivity(intent);
                finish();
            }
        });

        buttonSettingMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Setting.this, EditProfile.class);
                startActivity(intent);
            }
        });

        buttonSettingMyAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Setting.this, UserAddress.class);
                startActivity(intent);
            }
        });

        buttonSettingChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Setting.this, ChangePassword.class);
                startActivity(intent);
            }
        });

        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(Setting.this)
                        .setMessage("Are you sure want to log out?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                PreferenceUtils.saveLogin(0, Setting.this);
                                UsersDB.activeUser = null;

                                Intent intent = new Intent(Setting.this, Login.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

    }
}