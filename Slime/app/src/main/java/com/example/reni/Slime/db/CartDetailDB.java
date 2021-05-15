package com.example.reni.Slime.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.reni.Slime.model.CartDetail;

import java.util.Vector;

public class CartDetailDB {
    DBHelper dbHelper;

    public static Vector<CartDetail> vCartDetail = new Vector<>();

    public CartDetailDB(Context context){
        dbHelper = new DBHelper(context);
    }

    public void insertCartDetail(CartDetail cartDetail){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(dbHelper.CART_ID, cartDetail.cartID);
        cv.put(dbHelper.SNACK_ID, cartDetail.snackID);
        cv.put(dbHelper.QUANTITY, cartDetail.quantity);

        db.insert(dbHelper.TABLE_CART_DETAIL, null, cv);
    }
}
