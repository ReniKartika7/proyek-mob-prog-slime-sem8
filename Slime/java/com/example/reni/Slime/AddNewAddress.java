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

import com.example.reni.Slime.adapter.ChooseAddressAdapter;
import com.example.reni.Slime.db.AddressDB;
import com.example.reni.Slime.db.UsersDB;
import com.example.reni.Slime.model.Address;

public class AddNewAddress extends AppCompatActivity {

    ImageView btnAddAddressBack;

    EditText addAddressFullName, addAddressPhoneNumber, addAddressDetail, addAddressProvince, addAddressDistrict, addAddressSubDistrict, addAddressPostalCode;
    TextView txtAddressFullNameError, txtAddressPhoneNumberError, txtAddressDetailError, txtAddressProvinceError, txtAddressDistrictError, txtAddressSubDistrictError, txtAddressPostalCodeError;
    Button btnAddAddress;

    AddressDB addressDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_address);

        btnAddAddressBack = findViewById(R.id.btnAddAddressBack);
        addAddressFullName = findViewById(R.id.editAddressFullName);
        addAddressPhoneNumber = findViewById(R.id.editAddressPhoneNumber);
        addAddressDetail = findViewById(R.id.editAddressDetail);
        addAddressProvince = findViewById(R.id.editAddressProvince);
        addAddressDistrict = findViewById(R.id.editAddressDistrict);
        addAddressSubDistrict = findViewById(R.id.editAddressSubDistrict);
        addAddressPostalCode = findViewById(R.id.editAddressPostalCode);
        txtAddressFullNameError = findViewById(R.id.txtAddressFullNameError);
        txtAddressPhoneNumberError = findViewById(R.id.txtAddressPhoneNumberError);
        txtAddressDetailError = findViewById(R.id.txtAddressDetailError);
        txtAddressProvinceError = findViewById(R.id.txtAddressProvinceError);
        txtAddressDistrictError = findViewById(R.id.txtAddressDistrictError);
        txtAddressSubDistrictError = findViewById(R.id.txtAddressSubDistrictError);
        txtAddressPostalCodeError = findViewById(R.id.txtAddressPostalCodeError);

        btnAddAddress = findViewById(R.id.btnAddAddress);

        addressDB = new AddressDB(AddNewAddress.this);

        btnAddAddressBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName, phoneNumber, detail, province, district, subDistrict, postalCode;
                int flag = 0;

                fullName = addAddressFullName.getText().toString();
                phoneNumber = addAddressPhoneNumber.getText().toString();
                detail = addAddressDetail.getText().toString();
                province = addAddressProvince.getText().toString();
                district = addAddressDistrict.getText().toString();
                subDistrict = addAddressSubDistrict.getText().toString();
                postalCode = addAddressPostalCode.getText().toString();

                // full name
                if (fullName.isEmpty()) {
                    txtAddressFullNameError.setText("Full name is required!");
                } else if (fullName.length() <= 4) {
                    txtAddressFullNameError.setText("Full name must be more than 4 letters!");
                } else {
                    txtAddressFullNameError.setText("");
                    flag++;
                }

                // phone number
                if (phoneNumber.isEmpty()) {
                    txtAddressPhoneNumberError.setText("Phone number is required!");
                } else if(phoneNumber.length() > 13){
                    txtAddressPhoneNumberError.setText(("Phone number length mustn't exceed 13!"));
                } else {
                    txtAddressPhoneNumberError.setText("");
                    flag++;
                }

                // detail
                if (detail.isEmpty()) {
                    txtAddressDetailError.setText("Address detail is required!");
                } else if (detail.length() <= 10) {
                    txtAddressDetailError.setText("Address detail must be more than 10 letters!");
                } else {
                    txtAddressDetailError.setText("");
                    flag++;
                }

                // province
                if (province.isEmpty()) {
                    txtAddressProvinceError.setText("Province is required!");
                } else if (province.length() <= 2) {
                    txtAddressProvinceError.setText("Province too short!");
                } else {
                    txtAddressProvinceError.setText("");
                    flag++;
                }

                // district
                if (district.isEmpty()) {
                    txtAddressDistrictError.setText("District is required!");
                } else if (district.length() <= 2) {
                    txtAddressDistrictError.setText("District too short!");
                } else {
                    txtAddressDistrictError.setText("");
                    flag++;
                }

                // sub district
                if (subDistrict.isEmpty()) {
                    txtAddressSubDistrictError.setText("Sub district is required!");
                } else if (subDistrict.length() <= 2) {
                    txtAddressSubDistrictError.setText("Sub district too short!");
                } else {
                    txtAddressSubDistrictError.setText("");
                    flag++;
                }

                // postal code
                if (postalCode.isEmpty()) {
                    txtAddressPostalCodeError.setText("Postal code is required!");
                } else if (postalCode.length() <= 2) {
                    txtAddressPostalCodeError.setText("Postal code too short!");
                } else {
                    txtAddressPostalCodeError.setText("");
                    flag++;
                }

                if(flag == 7){
                    Address address = new Address();
                    address.userID = UsersDB.activeUser.ID;
                    address.fullName = fullName;
                    address.phoneNumber = phoneNumber;
                    address.detail = detail;
                    address.province = province;
                    address.district = district;
                    address.subDistrict = subDistrict;
                    address.postalCode = postalCode;

                    addressDB.insertAddress(address);
                    addressDB.loadCurrentAddress(AddNewAddress.this, UsersDB.activeUser.ID);
                    addressDB.loadAddress(AddNewAddress.this);

                    Toast.makeText(AddNewAddress.this, "Your address have successfully added!", Toast.LENGTH_SHORT).show();

                    if(AddressDB.IS_FROM_CHOOSE_ADDRESS == false){
                        Intent intent = new Intent(AddNewAddress.this, UserAddress.class);
                        startActivity(intent);
                        finish();
                    }else{
                        AddressDB.IS_FROM_CHOOSE_ADDRESS = false;
                        ChooseAddress.chooseAddressAdapter.notifyDataSetChanged();
                        Intent intent = new Intent(AddNewAddress.this, ChooseAddress.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

    }
}