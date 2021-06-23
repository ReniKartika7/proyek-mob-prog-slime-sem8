package com.example.teamslime.Slime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.teamslime.Slime.db.UsersDB;
import com.example.teamslime.Slime.model.User;

public class SignUp extends AppCompatActivity {

    EditText editSignUpEmail, editSignUpName, editSignUpPhone, editSignUpPassword, editSignUpConfirmPassword;
    Button btnNext;
    TextView txtHaveAccount, txtEmailError, txtNameError, txtPhoneError, txtPasswordError, txtConfirmPasswordError, txtAgreeToSError;
    CheckBox checkAgreeToS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editSignUpEmail = findViewById(R.id.editSignUpEmail);
        editSignUpName = findViewById(R.id.editSignUpName);
        editSignUpPhone = findViewById(R.id.editSignUpPhone);
        editSignUpPassword = findViewById(R.id.editSignUpPassword);
        editSignUpConfirmPassword = findViewById(R.id.editSignUpConfirmPassword);

        txtHaveAccount = findViewById(R.id.txtHaveAccount);
        txtEmailError = findViewById(R.id.txtSignUpEmailError);
        txtNameError = findViewById(R.id.txtSignUpNameError);
        txtPhoneError = findViewById(R.id.txtSignUpPhoneError);
        txtPasswordError = findViewById(R.id.txtSignUpPasswordError);
        txtConfirmPasswordError = findViewById(R.id.txtSignUpConfirmPasswordError);
        txtAgreeToSError = findViewById(R.id.txtSignUpAgreeToSError);

        checkAgreeToS = findViewById(R.id.checkToS);

        btnNext = findViewById(R.id.btnSignUpNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, name, phone, password, confirmPassword;
                int flag = 0;

                email = editSignUpEmail.getText().toString();
                name = editSignUpName.getText().toString();
                phone = editSignUpPhone.getText().toString();
                password = editSignUpPassword.getText().toString();
                confirmPassword = editSignUpConfirmPassword.getText().toString();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String passwordPattern = "^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+)$";

                int hasRegistered = 0;
                for (User user : UsersDB.vUser) {
                    if (user.email.equals(email)) {
                        hasRegistered = 1;
                        break;
                    }
                }

//                Email
                if (email.isEmpty()) {
                    txtEmailError.setText("Email is required!");
                } else if (!email.matches(emailPattern)) {
                    txtEmailError.setText("Invalid email address!");
                } else if (hasRegistered == 1) {
                    txtEmailError.setText("Email has been registered!");
                } else {
                    txtEmailError.setText("");
                    flag++;
                }

//                Name
                if (name.isEmpty()) {
                    txtNameError.setText("Name is required!");
                } else if (name.length() <= 4) {
                    txtNameError.setText("Name must be more than 4 letters!");
                } else {
                    txtNameError.setText("");
                    flag++;
                }

//                Phone
                if (phone.isEmpty()) {
                    txtPhoneError.setText("Phone is required!");
                } else if (phone.length() > 13) {
                    txtPhoneError.setText(("Phone length mustn't exceed 13!"));
                } else {
                    txtPhoneError.setText("");
                    flag++;
                }

//                Password
                if (password.isEmpty()) {
                    txtPasswordError.setText("Password is required!");
                } else if (password.length() < 9) {
                    txtPasswordError.setText("Password too short!");
                } else if (!password.matches(passwordPattern)) {
                    txtPasswordError.setText("Password must consist of at least one character and one number!");
                } else {
                    txtPasswordError.setText("");
                    flag++;
                }

//                Confirm Password
                if (!confirmPassword.equals(password)) {
                    txtConfirmPasswordError.setText("Password and Confirm Password doesn't match!");
                } else {
                    txtConfirmPasswordError.setText("");
                    flag++;
                }

//                ToS
                if (!checkAgreeToS.isChecked()) {
                    txtAgreeToSError.setText("You have to agree to our Privacy Policy and ToS!");
                } else {
                    txtAgreeToSError.setText("");
                    flag++;
                }

                if (flag == 6) {
                    Intent intent = new Intent(SignUp.this, ChooseGender.class);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    intent.putExtra("phone", phone);
                    intent.putExtra("name", name);

                    startActivity(intent);
                }

            }
        });

        txtHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, Login.class);
                finish();
            }
        });

    }
}