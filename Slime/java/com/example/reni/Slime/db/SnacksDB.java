package com.example.reni.Slime.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.reni.Slime.model.Snack;

import java.util.Vector;

public class SnacksDB {
    DBHelper dbHelper;

    public static Vector<Snack> vSnack = new Vector<>();
    public static Vector<Snack> currentSnackItem = new Vector<>();

    public static boolean SNACK_HAS_INSERTED = false;

    public SnacksDB(Context context){
        dbHelper = new DBHelper(context);
    }

    public void insertSnack(Snack snack){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(dbHelper.SNACK_NAME, snack.name);
        cv.put(dbHelper.SNACK_PRICE, snack.price);
        cv.put(dbHelper.SNACK_STOCK, snack.stock);
        cv.put(dbHelper.SNACK_CATEGORY_ID, snack.categoryID);
        cv.put(dbHelper.SNACK_COVER_URL, snack.coverUrl);
        cv.put(dbHelper.SNACK_DETAIL, snack.detail);

        db.insert(dbHelper.TABLE_SNACKS, null, cv);
    }

    public void updateSnack(Snack snack) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(dbHelper.SNACK_ID, snack.ID);
        cv.put(dbHelper.SNACK_NAME, snack.name);
        cv.put(dbHelper.SNACK_PRICE, snack.price);
        cv.put(dbHelper.SNACK_STOCK, snack.stock);
        cv.put(dbHelper.SNACK_CATEGORY_ID, snack.categoryID);
        cv.put(dbHelper.SNACK_COVER_URL, snack.coverUrl);
        cv.put(dbHelper.SNACK_DETAIL, snack.detail);

        db.update(dbHelper.TABLE_SNACKS, cv, dbHelper.SNACK_ID + " = '" + snack.ID + "'", null);
    }

    public void loadSnack(Context context){
        vSnack.clear();
        currentSnackItem.clear();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectSnack = "SELECT * FROM " + dbHelper.TABLE_SNACKS;

        Cursor cursor = db.rawQuery(selectSnack, null);

        while(cursor.moveToNext()){
            Snack snack = new Snack();

            snack.ID = cursor.getInt(cursor.getColumnIndex(dbHelper.SNACK_ID));
            snack.name = cursor.getString(cursor.getColumnIndex(dbHelper.SNACK_NAME));
            snack.price = cursor.getInt(cursor.getColumnIndex(dbHelper.SNACK_PRICE));
            snack.stock = cursor.getInt(cursor.getColumnIndex(dbHelper.SNACK_STOCK));
            snack.coverUrl = cursor.getString(cursor.getColumnIndex(dbHelper.SNACK_COVER_URL));
            snack.detail = cursor.getString(cursor.getColumnIndex(dbHelper.SNACK_DETAIL));
            snack.categoryID = cursor.getInt(cursor.getColumnIndex(dbHelper.SNACK_CATEGORY_ID));

            if(snack.stock > 0){
                vSnack.add(snack);
                currentSnackItem.add(snack);
            }
        }

        cursor.close();
    }

    public Snack loadSpecificSnack(int snackID){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectSpesificSnack = "SELECT * FROM " + dbHelper.TABLE_SNACKS + " WHERE " + dbHelper.SNACK_ID + " = " + snackID;

        Cursor cursor = db.rawQuery(selectSpesificSnack, null);

        cursor.moveToFirst();
        Snack snack = new Snack();

        snack.ID = cursor.getInt(cursor.getColumnIndex(dbHelper.SNACK_ID));
        snack.name = cursor.getString(cursor.getColumnIndex(dbHelper.SNACK_NAME));
        snack.price = cursor.getInt(cursor.getColumnIndex(dbHelper.SNACK_PRICE));
        snack.stock = cursor.getInt(cursor.getColumnIndex(dbHelper.SNACK_STOCK));
        snack.coverUrl = cursor.getString(cursor.getColumnIndex(dbHelper.SNACK_COVER_URL));
        snack.detail = cursor.getString(cursor.getColumnIndex(dbHelper.SNACK_DETAIL));
        snack.categoryID = cursor.getInt(cursor.getColumnIndex(dbHelper.SNACK_CATEGORY_ID));

        cursor.close();

        return snack;
    }

    public void loadSearchSnack(String keyword){
        currentSnackItem.clear();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        keyword = keyword.toLowerCase();


        String selectSnack = "SELECT * FROM " + dbHelper.TABLE_SNACKS + " WHERE LOWER(" + dbHelper.SNACK_NAME + ") LIKE '%" + keyword + "%'" ;

        Cursor cursor = db.rawQuery(selectSnack, null);

        while(cursor.moveToNext()){
            Snack snack = new Snack();

            snack.ID = cursor.getInt(cursor.getColumnIndex(dbHelper.SNACK_ID));
            snack.name = cursor.getString(cursor.getColumnIndex(dbHelper.SNACK_NAME));
            snack.price = cursor.getInt(cursor.getColumnIndex(dbHelper.SNACK_PRICE));
            snack.stock = cursor.getInt(cursor.getColumnIndex(dbHelper.SNACK_STOCK));
            snack.coverUrl = cursor.getString(cursor.getColumnIndex(dbHelper.SNACK_COVER_URL));
            snack.detail = cursor.getString(cursor.getColumnIndex(dbHelper.SNACK_DETAIL));
            snack.categoryID = cursor.getInt(cursor.getColumnIndex(dbHelper.SNACK_CATEGORY_ID));

            if(snack.stock > 0){
                currentSnackItem.add(snack);
            }
        }

        cursor.close();
    }
}
