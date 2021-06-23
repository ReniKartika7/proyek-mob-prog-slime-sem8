package com.example.teamslime.Slime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teamslime.Slime.db.UsersDB;
import com.example.teamslime.Slime.model.User;

public class ChooseGender extends AppCompatActivity {

    Button btnChooseGenderSignUp;
    TextView txtChooseGenderSkip;

    RadioGroup rgGender;
    RadioButton rbGenderMale, rbGenderFemale;

    UsersDB usersDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_gender);

        // ambil intentnya
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String password = intent.getStringExtra("password");
        String phone = intent.getStringExtra("phone");
        String name = intent.getStringExtra("name");

        final User user ;
        user = new User();
        user.email = email;
        user.password = password;
        user.phone = phone;
        user.name = name;

        btnChooseGenderSignUp = findViewById(R.id.btnChooseGenderSignUp);
        txtChooseGenderSkip = findViewById(R.id.txtChooseGenderSkip);

        rgGender = findViewById(R.id.radioGroupGender);
        rbGenderFemale = findViewById(R.id.radioButtonFemale);
        rbGenderMale = findViewById(R.id.radioButtonMale);

        usersDB = new UsersDB(ChooseGender.this);

        btnChooseGenderSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rgGender.getCheckedRadioButtonId() == -1){
                    user.gender = "";
                }else{
                    int selectedGender = rgGender.getCheckedRadioButtonId();
                    RadioButton rbSelectedGender = (RadioButton) findViewById(selectedGender);
                    String gender = rbSelectedGender.getText().toString();

                    user.gender = gender;
                }

                usersDB.insertUser(user);

                Toast.makeText(ChooseGender.this, "Thanks for your registration !", Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(ChooseGender.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        txtChooseGenderSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.gender = "";

                usersDB.insertUser(user);

                Toast.makeText(ChooseGender.this, "Thanks for your registration !", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ChooseGender.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}