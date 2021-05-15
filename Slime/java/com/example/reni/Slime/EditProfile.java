package com.example.reni.Slime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reni.Slime.db.UsersDB;
import com.example.reni.Slime.model.User;

public class EditProfile extends AppCompatActivity {
    EditText editEditProfileEmail, editEditProfileName, editEditProfilePhone;
    Button btnSave;
    ImageView btnBack;
    TextView txtEmailError, txtNameError, txtPhoneError;

    RadioGroup rgGender;
    RadioButton rbGenderMale, rbGenderFemale;

    UsersDB usersDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editEditProfileEmail = findViewById(R.id.editEditProfileEmail);
        editEditProfileName = findViewById(R.id.editEditProfileName);
        editEditProfilePhone = findViewById(R.id.editEditProfilePhone);

        btnSave = findViewById(R.id.btnEditProfileSave);
        btnBack = findViewById(R.id.btnEditProfileBack);

        txtEmailError = findViewById(R.id.txtEditProfileEmailError);
        txtNameError = findViewById(R.id.txtEditProfileNameError);
        txtPhoneError = findViewById(R.id.txtEditProfilePhoneError);

        rgGender = findViewById(R.id.radioGroupGenderEditProfile);
        rbGenderFemale = findViewById(R.id.radioButtonFemaleEditProfile);
        rbGenderMale = findViewById(R.id.radioButtonMaleEditProfile);

        editEditProfileName.setText(UsersDB.activeUser.name);
        editEditProfilePhone.setText(UsersDB.activeUser.phone);
        editEditProfileEmail.setText(UsersDB.activeUser.email);

        if(UsersDB.activeUser.gender.equals("Female")){
            rbGenderFemale.setChecked(true);
        }else if(UsersDB.activeUser.gender.equals("Male")){
            rbGenderMale.setChecked(true);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, name, phone;
                int flag = 0;

                email = editEditProfileEmail.getText().toString();
                name = editEditProfileName.getText().toString();
                phone = editEditProfilePhone.getText().toString();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

//              Email
                if (email.isEmpty()) {
                    txtEmailError.setText("Email is required!");
                } else if (!email.matches(emailPattern)) {
                    txtEmailError.setText("Invalid email address!");
                } else {
                    txtEmailError.setText("");
                    flag++;
                }

//              Name
                if (name.isEmpty()) {
                    txtNameError.setText("Name is required!");
                } else if(name.length() <= 4){
                    txtNameError.setText("Name must be more than 4 letters!");
                } else {
                    txtNameError.setText("");
                    flag++;
                }

//              Phone
                if (phone.isEmpty()) {
                    txtPhoneError.setText("Phone is required!");
                } else if(phone.length() > 13){
                    txtPhoneError.setText(("Phone length mustn't exceed 13!"));
                } else {
                    txtPhoneError.setText("");
                    flag++;
                }

                if(flag == 3){
                    User user = new User();
                    user.ID = UsersDB.activeUser.ID;
                    user.email = email;
                    user.password = UsersDB.activeUser.password;
                    user.phone = phone;
                    user.name = name;

                    if(rgGender.getCheckedRadioButtonId() == -1){
                        user.gender = "";
                    }else{
                        int selectedGender = rgGender.getCheckedRadioButtonId();
                        RadioButton rbSelectedGender = (RadioButton) findViewById(selectedGender);
                        String gender = rbSelectedGender.getText().toString();

                        user.gender = gender;
                    }

                    usersDB = new UsersDB(EditProfile.this);
                    usersDB.updateUser(user);

                    UsersDB.activeUser = user;

                    Toast.makeText(EditProfile.this, "Your profile have successfully updated!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(EditProfile.this, UserProfile.class);
                    startActivity(intent);
                    finish();

                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfile.this, UserProfile.class);
                startActivity(intent);
                finish();
            }
        });

    }
}