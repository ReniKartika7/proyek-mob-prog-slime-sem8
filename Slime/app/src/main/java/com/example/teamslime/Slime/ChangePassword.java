package com.example.teamslime.Slime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teamslime.Slime.db.UsersDB;
import com.example.teamslime.Slime.model.User;

public class ChangePassword extends AppCompatActivity {

    ImageView btnChangePasswordBack;
    TextView txtChangeCurrentPasswordError, txtChangeConfirmNewPasswordError, txtChangeNewPasswordError;
    EditText editChangeCurrentPassword, editChangeConfirmNewPassword, editChangeNewPassword;
    Button buttonChangePassword;

    UsersDB usersDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        btnChangePasswordBack = findViewById(R.id.btnChangePasswordBack);
        txtChangeCurrentPasswordError = findViewById(R.id.txtChangeCurrentPasswordError);
        txtChangeConfirmNewPasswordError = findViewById(R.id.txtChangeConfirmNewPasswordError);
        txtChangeNewPasswordError = findViewById(R.id.txtChangeNewPasswordError);
        editChangeCurrentPassword = findViewById(R.id.editChangeCurrentPassword);
        editChangeNewPassword = findViewById(R.id.editChangeNewPassword);
        editChangeConfirmNewPassword = findViewById(R.id.editChangeConfirmNewPassword);
        buttonChangePassword = findViewById(R.id.buttonChangePassword);

        btnChangePasswordBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangePassword.this, Setting.class);
                startActivity(intent);
                finish();
            }
        });

        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentPassword, newPassword, confirmNewPassword;

                int flag = 0;
                currentPassword = editChangeCurrentPassword.getText().toString();
                newPassword = editChangeNewPassword.getText().toString();
                confirmNewPassword = editChangeConfirmNewPassword.getText().toString();

                String passwordPattern = "^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+)$";

                // Current Password
                if (currentPassword.isEmpty()) {
                    txtChangeCurrentPasswordError.setText("Current Password is required!");
                } else if(!currentPassword.equals(UsersDB.activeUser.password)){
                    txtChangeCurrentPasswordError.setText("Wrong Current Password!");
                }else {
                    txtChangeCurrentPasswordError.setText("");
                    flag++;
                }

                // New Password
                if (newPassword.isEmpty()) {
                    txtChangeNewPasswordError.setText("New Password is required!");
                } else if(newPassword.length() < 9){
                    txtChangeNewPasswordError.setText("New Password too short!");
                } else if(!newPassword.matches(passwordPattern)){
                    txtChangeNewPasswordError.setText("New Password must consist of at least one character and one number!");
                } else {
                    txtChangeNewPasswordError.setText("");
                    flag++;
                }

                // Confirm New Password
                if (!confirmNewPassword.equals(newPassword)) {
                    txtChangeConfirmNewPasswordError.setText("New Password and Confirm Password doesn't match!");
                } else {
                    txtChangeConfirmNewPasswordError.setText("");
                    flag++;
                }

                if(flag == 3){
                    User user = new User();
                    user.ID = UsersDB.activeUser.ID;
                    user.email = UsersDB.activeUser.email;
                    user.password = newPassword;
                    user.phone = UsersDB.activeUser.phone;
                    user.name = UsersDB.activeUser.name;
                    user.gender = UsersDB.activeUser.gender;

                    usersDB = new UsersDB(ChangePassword.this);
                    usersDB.updateUser(user);

                    UsersDB.activeUser = user;

                    Toast.makeText(ChangePassword.this, "Your password have successfully updated!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ChangePassword.this, Setting.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}