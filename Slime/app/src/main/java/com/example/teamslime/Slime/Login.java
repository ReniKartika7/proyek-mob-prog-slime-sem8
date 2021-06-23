package com.example.teamslime.Slime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.teamslime.Slime.db.AddressDB;
import com.example.teamslime.Slime.db.UsersDB;
import com.example.teamslime.Slime.model.User;
import com.example.teamslime.Slime.util.PreferenceUtils;

public class Login extends AppCompatActivity {

    EditText editLoginEmail, editLoginPassword;
    Button btnLogin;
    TextView txtDontHaveAccount, txtLoginEmailError, txtLoginPasswordError;
    CheckBox checkSaveLogin;

    //pop up
    public static boolean POP_UP_HAS_BEEN_SHOWED = false;

    UsersDB usersDB;
    AddressDB addressDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // load user
        usersDB = new UsersDB(Login.this);
        usersDB.loadUser(Login.this);

        // edit text dkk
        editLoginEmail = findViewById(R.id.editLoginEmail);
        editLoginPassword = findViewById(R.id.editLoginPassword);

        txtDontHaveAccount = findViewById(R.id.txtDontHaveAccount);
        txtLoginEmailError = findViewById(R.id.txtLoginEmailError);
        txtLoginPasswordError = findViewById(R.id.txtLoginPasswordError);

        checkSaveLogin = findViewById(R.id.checkSaveLogin);

        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;
                int flag = 0;
                email = editLoginEmail.getText().toString();
                password = editLoginPassword.getText().toString();

                // Email
                if (email.isEmpty()) {
                    txtLoginEmailError.setText("Email is required!");
                } else {
                    txtLoginEmailError.setText("");
                    flag++;
                }

                // Password
                if (password.isEmpty()) {
                    txtLoginPasswordError.setText("Password is required!");
                } else {
                    txtLoginPasswordError.setText("");
                    flag++;
                }


                User foundUser = null;
                // Ada ga di DB
                if(flag == 2){

                    for (User user : usersDB.vUser){
                        if(user.email.equals(email) && user.password.equals(password)){
                            flag++;
                            foundUser = user;
                            break;
                        }
                    }

                    if(foundUser == null){
                        txtLoginPasswordError.setText("Incorect email and password !");
                    }else{
                        usersDB.activeUser = foundUser;

                        if(checkSaveLogin.isChecked()){
                            PreferenceUtils.saveLogin(foundUser.ID, Login.this);
                        }

                        Intent intent = new Intent(Login.this, HomePage.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

        txtDontHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
            }
        });
    }
}