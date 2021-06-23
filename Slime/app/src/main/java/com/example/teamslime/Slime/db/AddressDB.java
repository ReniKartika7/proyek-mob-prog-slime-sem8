package com.example.teamslime.Slime.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.teamslime.Slime.model.Address;

import java.util.Vector;

public class AddressDB {
    DBHelper dbHelper;

    public static Vector<Address> vAddress = new Vector<>();
    public static Vector<Address> currentAddress = new Vector<>();

    public static boolean IS_FROM_CHOOSE_ADDRESS = false;

    public AddressDB(Context context){
        dbHelper = new DBHelper(context);
    }

    public void insertAddress(Address address){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(dbHelper.USER_ID, address.userID);
        cv.put(dbHelper.ADDRESS_DETAIL, address.detail);
        cv.put(dbHelper.ADDRESS_FULL_NAME, address.fullName);
        cv.put(dbHelper.ADDRESS_PHONE_NUMBER, address.phoneNumber);
        cv.put(dbHelper.ADDRESS_PROVINCE, address.province);
        cv.put(dbHelper.ADDRESS_DISTRICT, address.district);
        cv.put(dbHelper.ADDRESS_SUB_DISTRICT, address.subDistrict);
        cv.put(dbHelper.ADDRESS_POSTAL_CODE, address.postalCode);

        db.insert(dbHelper.TABLE_ADDRESS, null, cv);
    }

    public void updateAddress(Address address){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(dbHelper.USER_ID, address.userID);
        cv.put(dbHelper.ADDRESS_DETAIL, address.detail);
        cv.put(dbHelper.ADDRESS_FULL_NAME, address.fullName);
        cv.put(dbHelper.ADDRESS_PHONE_NUMBER, address.phoneNumber);
        cv.put(dbHelper.ADDRESS_PROVINCE, address.province);
        cv.put(dbHelper.ADDRESS_DISTRICT, address.district);
        cv.put(dbHelper.ADDRESS_SUB_DISTRICT, address.subDistrict);
        cv.put(dbHelper.ADDRESS_POSTAL_CODE, address.postalCode);

        db.update(dbHelper.TABLE_ADDRESS, cv, dbHelper.ADDRESS_ID + " = " + address.AddressID, null);
    }

    public void loadAddress(Context context){
        vAddress.clear();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectAddress = "SELECT * FROM " + dbHelper.TABLE_ADDRESS;

        Cursor cursor = db.rawQuery(selectAddress, null);

        while (cursor.moveToNext()) {
            Address address = new Address();

            address.AddressID = cursor.getInt(cursor.getColumnIndex(dbHelper.ADDRESS_ID));
            address.userID = cursor.getInt(cursor.getColumnIndex(dbHelper.USER_ID));
            address.detail = cursor.getString(cursor.getColumnIndex(dbHelper.ADDRESS_DETAIL));
            address.fullName = cursor.getString(cursor.getColumnIndex(dbHelper.ADDRESS_FULL_NAME));
            address.phoneNumber = cursor.getString(cursor.getColumnIndex(dbHelper.ADDRESS_PHONE_NUMBER));
            address.province = cursor.getString(cursor.getColumnIndex(dbHelper.ADDRESS_PROVINCE));
            address.district = cursor.getString(cursor.getColumnIndex(dbHelper.ADDRESS_DISTRICT));
            address.subDistrict = cursor.getString(cursor.getColumnIndex(dbHelper.ADDRESS_SUB_DISTRICT));
            address.postalCode = cursor.getString(cursor.getColumnIndex(dbHelper.ADDRESS_POSTAL_CODE));

            vAddress.add(address);
        }
        cursor.close();
    }

    public Address loadSpecificAddress(int addressID){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectSpecificAddress = "SELECT * FROM " + dbHelper.TABLE_ADDRESS + " WHERE " + dbHelper.ADDRESS_ID + " = " + addressID;

        Cursor cursor = db.rawQuery(selectSpecificAddress, null);

        cursor.moveToFirst();

        Address address = new Address();

        address.AddressID = cursor.getInt(cursor.getColumnIndex(dbHelper.ADDRESS_ID));
        address.userID = cursor.getInt(cursor.getColumnIndex(dbHelper.USER_ID));
        address.detail = cursor.getString(cursor.getColumnIndex(dbHelper.ADDRESS_DETAIL));
        address.fullName = cursor.getString(cursor.getColumnIndex(dbHelper.ADDRESS_FULL_NAME));
        address.phoneNumber = cursor.getString(cursor.getColumnIndex(dbHelper.ADDRESS_PHONE_NUMBER));
        address.province = cursor.getString(cursor.getColumnIndex(dbHelper.ADDRESS_PROVINCE));
        address.district = cursor.getString(cursor.getColumnIndex(dbHelper.ADDRESS_DISTRICT));
        address.subDistrict = cursor.getString(cursor.getColumnIndex(dbHelper.ADDRESS_SUB_DISTRICT));
        address.postalCode = cursor.getString(cursor.getColumnIndex(dbHelper.ADDRESS_POSTAL_CODE));

        cursor.close();

        return address;
    }

    public void loadCurrentAddress(Context context, int userID){
        currentAddress.clear();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectAddress = "SELECT * FROM " + dbHelper.TABLE_ADDRESS + " WHERE " + dbHelper.USER_ID + " = " + userID;

        Cursor cursor = db.rawQuery(selectAddress, null);

        while (cursor.moveToNext()) {
            Address address = new Address();

            address.AddressID = cursor.getInt(cursor.getColumnIndex(dbHelper.ADDRESS_ID));
            address.userID = cursor.getInt(cursor.getColumnIndex(dbHelper.USER_ID));
            address.detail = cursor.getString(cursor.getColumnIndex(dbHelper.ADDRESS_DETAIL));
            address.fullName = cursor.getString(cursor.getColumnIndex(dbHelper.ADDRESS_FULL_NAME));
            address.phoneNumber = cursor.getString(cursor.getColumnIndex(dbHelper.ADDRESS_PHONE_NUMBER));
            address.province = cursor.getString(cursor.getColumnIndex(dbHelper.ADDRESS_PROVINCE));
            address.district = cursor.getString(cursor.getColumnIndex(dbHelper.ADDRESS_DISTRICT));
            address.subDistrict = cursor.getString(cursor.getColumnIndex(dbHelper.ADDRESS_SUB_DISTRICT));
            address.postalCode = cursor.getString(cursor.getColumnIndex(dbHelper.ADDRESS_POSTAL_CODE));

            currentAddress.add(address);
        }
        cursor.close();
    }
}
