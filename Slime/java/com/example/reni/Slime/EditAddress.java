package com.example.reni.Slime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reni.Slime.db.AddressDB;
import com.example.reni.Slime.db.UsersDB;
import com.example.reni.Slime.model.Address;

public class EditAddress extends AppCompatActivity {

    ImageView btnEditAddressBack;
    EditText editEAFullName, editEAPhoneNumber, editEADetail, editEAProvince, editEADistrict, editEASubDistrict, editEAPostalCode;
    TextView txtEAFullNameError, txtEAPhoneNumberError, txtEADetailError, txtEAProvinceError, txtEADistrictError, txtEASubDistrictError, txtEAPostalCodeError;
    Button btnSaveAddress;

    int position;

    AddressDB addressDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);

        btnEditAddressBack = findViewById(R.id.btnEditAddressBack);
        editEAFullName = findViewById(R.id.editEAFullName);
        txtEAFullNameError = findViewById(R.id.txtEAFullNameError);
        editEAPhoneNumber = findViewById(R.id.editEAPhoneNumber);
        txtEAPhoneNumberError = findViewById(R.id.txtEAPhoneNumberError);
        editEADetail = findViewById(R.id.editEADetail);
        txtEADetailError = findViewById(R.id.txtEADetailError);
        editEAProvince = findViewById(R.id.editEAProvince);
        txtEAProvinceError = findViewById(R.id.txtEAProvinceError);
        editEADistrict = findViewById(R.id.editEADistrict);
        txtEADistrictError = findViewById(R.id.txtEADistrictError);
        editEASubDistrict = findViewById(R.id.editEASubDistrict);
        txtEASubDistrictError = findViewById(R.id.txtEASubDistrictError);
        editEAPostalCode = findViewById(R.id.editEAPostalCode);
        txtEAPostalCodeError = findViewById(R.id.txtEAPostalCodeError);
        btnSaveAddress = findViewById(R.id.btnSaveAddress);

        Intent intent = getIntent();

        position = intent.getIntExtra("addressPosition", -1);

        final Address addressPosition = AddressDB.currentAddress.get(position);

        editEAFullName.setText(addressPosition.fullName);
        editEAPhoneNumber.setText(addressPosition.phoneNumber);
        editEADetail.setText(addressPosition.detail);
        editEAProvince.setText(addressPosition.province);
        editEADistrict.setText(addressPosition.district);
        editEASubDistrict.setText(addressPosition.subDistrict);
        editEAPostalCode.setText(addressPosition.postalCode);

        btnEditAddressBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditAddress.this, UserAddress.class);
                startActivity(intent);
                finish();
            }
        });

        btnSaveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName, phoneNumber, detail, province, district, subDistrict, postalCode;
                int flag = 0;

                fullName = editEAFullName.getText().toString();
                phoneNumber = editEAPhoneNumber.getText().toString();
                detail = editEADetail.getText().toString();
                province = editEAProvince.getText().toString();
                district = editEADistrict.getText().toString();
                subDistrict = editEASubDistrict.getText().toString();
                postalCode = editEAPostalCode.getText().toString();

                // full name
                if (fullName.isEmpty()) {
                    txtEAFullNameError.setText("Full name is required!");
                } else if (fullName.length() <= 4) {
                    txtEAFullNameError.setText("Full name must be more than 4 letters!");
                } else {
                    txtEAFullNameError.setText("");
                    flag++;
                }

                // phone number
                if (phoneNumber.isEmpty()) {
                    txtEAPhoneNumberError.setText("Phone number is required!");
                } else if(phoneNumber.length() > 13){
                    txtEAPhoneNumberError.setText(("Phone number length mustn't exceed 13!"));
                } else {
                    txtEAPhoneNumberError.setText("");
                    flag++;
                }

                // detail
                if (detail.isEmpty()) {
                    txtEADetailError.setText("Address detail is required!");
                } else if (detail.length() <= 10) {
                    txtEADetailError.setText("Address detail must be more than 10 letters!");
                } else {
                    txtEADetailError.setText("");
                    flag++;
                }

                // province
                if (province.isEmpty()) {
                    txtEAProvinceError.setText("Province is required!");
                } else if (province.length() <= 2) {
                    txtEAProvinceError.setText("Province too short!");
                } else {
                    txtEAProvinceError.setText("");
                    flag++;
                }

                // district
                if (district.isEmpty()) {
                    txtEADistrictError.setText("District is required!");
                } else if (district.length() <= 2) {
                    txtEADistrictError.setText("District too short!");
                } else {
                    txtEADistrictError.setText("");
                    flag++;
                }

                // sub district
                if (subDistrict.isEmpty()) {
                    txtEASubDistrictError.setText("Sub district is required!");
                } else if (subDistrict.length() <= 2) {
                    txtEASubDistrictError.setText("Sub district too short!");
                } else {
                    txtEASubDistrictError.setText("");
                    flag++;
                }

                // postal code
                if (postalCode.isEmpty()) {
                    txtEAPostalCodeError.setText("Postal code is required!");
                } else if (postalCode.length() <= 2) {
                    txtEAPostalCodeError.setText("Postal code too short!");
                } else {
                    txtEAPostalCodeError.setText("");
                    flag++;
                }

                if(flag == 7){
                    Address address = new Address();
                    address.AddressID = addressPosition.AddressID;
                    address.userID = UsersDB.activeUser.ID;
                    address.fullName = fullName;
                    address.phoneNumber = phoneNumber;
                    address.detail = detail;
                    address.province = province;
                    address.district = district;
                    address.subDistrict = subDistrict;
                    address.postalCode = postalCode;

                    addressDB = new AddressDB(EditAddress.this);
                    addressDB.updateAddress(address);
                    addressDB.loadAddress(EditAddress.this);
                    addressDB.loadCurrentAddress(EditAddress.this, UsersDB.activeUser.ID);

                    Toast.makeText(EditAddress.this, "Your address have successfully updated!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(EditAddress.this, UserAddress.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}