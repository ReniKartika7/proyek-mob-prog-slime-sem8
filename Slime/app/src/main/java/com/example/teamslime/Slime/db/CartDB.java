package com.example.teamslime.Slime.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.teamslime.Slime.model.Cart;

import java.util.Vector;

public class CartDB {
    DBHelper dbHelper;

    public static Vector<Cart> vCart = new Vector<>();
    public static Vector<Cart> currentCart = new Vector<>();
    public static Cart vBuy = new Cart();

    public static boolean IS_CHECKED_BUY_NOW = false;

    public CartDB(Context context){
        dbHelper = new DBHelper(context);
    }

    public void insertCart(Cart cart){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(dbHelper.USER_ID, cart.userID);
        cv.put(dbHelper.SNACK_ID, cart.snackID);
        cv.put(dbHelper.QUANTITY, cart.quantity);

        db.insert(dbHelper.TABLE_CART, null, cv);
    }

    public void deleteSpecificCart(int userID){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(dbHelper.TABLE_CART, dbHelper.USER_ID + " = '" + userID + "'", null );
    }

    public void deleteSpecificCartMyCart(int cartID){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(dbHelper.TABLE_CART, dbHelper.CART_ID + " = '" + cartID +"'", null );
    }

    public void loadCart(Context context){
        vCart.clear();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectCart = "SELECT * FROM " + dbHelper.TABLE_CART;

        Cursor cursor = db.rawQuery(selectCart, null);

        while(cursor.moveToNext()){
            Cart cart = new Cart();

            cart.ID = cursor.getInt(cursor.getColumnIndex(dbHelper.CART_ID));
            cart.userID = cursor.getInt(cursor.getColumnIndex(dbHelper.USER_ID));
            cart.snackID = cursor.getInt(cursor.getColumnIndex(dbHelper.SNACK_ID));
            cart.quantity = cursor.getInt(cursor.getColumnIndex(dbHelper.QUANTITY));

            vCart.add(cart);
        }

        cursor.close();
    }

    public void loadCurrentCart(Context context, int userID){
        currentCart.clear();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectCurrentCart = "SELECT * FROM " + dbHelper.TABLE_CART + " WHERE " + dbHelper.USER_ID + " = " + userID;

        Cursor cursor = db.rawQuery(selectCurrentCart, null);

        while(cursor.moveToNext()){
            Cart cart = new Cart();

            cart.ID = cursor.getInt(cursor.getColumnIndex(dbHelper.CART_ID));
            cart.userID = cursor.getInt(cursor.getColumnIndex(dbHelper.USER_ID));
            cart.snackID = cursor.getInt(cursor.getColumnIndex(dbHelper.SNACK_ID));
            cart.quantity = cursor.getInt(cursor.getColumnIndex(dbHelper.QUANTITY));

            currentCart.add(cart);
        }

        cursor.close();
    }

    public int loadCurrentCartID(Context context, int cartID){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectCurrentCartID = "SELECT * FROM " + dbHelper.TABLE_CART + " WHERE " + dbHelper.CART_ID + " = " + cartID;

        Cursor cursor = db.rawQuery(selectCurrentCartID, null);

        cursor.moveToFirst();

        Cart cart = new Cart();

        cart.quantity = cursor.getInt(cursor.getColumnIndex(dbHelper.QUANTITY));

        return cart.quantity;
    }

    public void updateCart(Context context, Cart cart){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int old_qty = loadCurrentCartID(context, cart.ID);

        ContentValues cv = new ContentValues();
        cv.put(dbHelper.CART_ID, cart.ID);
        cv.put(dbHelper.USER_ID, cart.userID);
        cv.put(dbHelper.SNACK_ID, cart.snackID);
        cv.put(dbHelper.QUANTITY, cart.quantity + old_qty);

        db.update(dbHelper.TABLE_CART, cv, dbHelper.CART_ID+ " = '" + cart.ID + "'", null);
    }

    public void updateCartMyCart(Context context, Cart cart){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(dbHelper.CART_ID, cart.ID);
        cv.put(dbHelper.USER_ID, cart.userID);
        cv.put(dbHelper.SNACK_ID, cart.snackID);
        cv.put(dbHelper.QUANTITY, cart.quantity);

        db.update(dbHelper.TABLE_CART, cv, dbHelper.CART_ID+ " = '" + cart.ID + "'", null);
    }

}
