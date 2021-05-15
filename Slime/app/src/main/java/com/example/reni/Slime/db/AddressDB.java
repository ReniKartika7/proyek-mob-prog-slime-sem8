package com.example.reni.Slime.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.reni.Slime.model.Address;

import java.util.Vector;

public class AddressDB {
    DBHelper dbHelper;

    public static Vector<Address> vAddress = new Vector<>();
    public static Vector<Address> currentAddress = new Vector<>();

    public static boolean ADDRESS_HAS_INSERTED = false;

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
        cv.put(dbHelper.ADDRESS_LATITUDE, address.latitude);
        cv.put(dbHelper.ADDRESS_LONGITUDE, address.longitude);

        db.insert(dbHelper.TABLE_ADDRESS, null, cv);
    }

    public void updateAddress(Address address){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(dbHelper.USER_ID, address.userID);
        cv.put(dbHelper.ADDRESS_DETAIL, address.detail);
        cv.put(dbHelper.ADDRESS_FULL_NAME, address.fullName);
        cv.put(dbHelper.ADDRESS_PHONE_NUMBER, address.phoneNumber);
        cv.put(dbHelper.ADDRESS_LATITUDE, address.latitude);
        cv.put(dbHelper.ADDRESS_LONGITUDE, address.longitude);

        db.update(dbHelper.TABLE_ADDRESS, cv, dbHelper.ADDRESS_ID + " = '" + address.AddressID + "'", null);
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
            address.latitude = cursor.getString(cursor.getColumnIndex(dbHelper.ADDRESS_LATITUDE));
            address.longitude = cursor.getString(cursor.getColumnIndex(dbHelper.ADDRESS_LONGITUDE));

            vAddress.add(address);
        }
    }
}
