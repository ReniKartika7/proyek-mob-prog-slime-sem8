package com.example.reni.Slime.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.reni.Slime.model.CartHeader;

import java.util.Vector;

public class CartHeaderDB {
    DBHelper dbHelper;

    public static Vector<CartHeader> vCartHeader = new Vector<>();

    public CartHeaderDB(Context context){
        dbHelper = new DBHelper(context);
    }

    public void insertCartHeader(CartHeader cartHeader){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(dbHelper.USER_ID, cartHeader.userID);

        db.insert(dbHelper.TABLE_CART_HEADER, null, cv);
    }
}
