package com.example.reni.Slime.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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

        db.insert(dbHelper.TABLE_SNACKS, null, cv);
    }

    public void loadSnack(Context context){
        vSnack.clear();
        currentSnackItem.clear();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectSnack = "SELECT * FROM " + dbHelper.TABLE_SNACKS;

        Cursor cursor = db.rawQuery(selectSnack, null);

        while(cursor.moveToNext()){
            Log.i("HAHA4", "MAMA");
            Snack snack = new Snack();

            snack.ID = cursor.getInt(cursor.getColumnIndex(dbHelper.SNACK_ID));
            snack.name = cursor.getString(cursor.getColumnIndex(dbHelper.SNACK_NAME));
            snack.price = cursor.getInt(cursor.getColumnIndex(dbHelper.SNACK_PRICE));
            snack.stock = cursor.getInt(cursor.getColumnIndex(dbHelper.SNACK_STOCK));
            snack.coverUrl = cursor.getString(cursor.getColumnIndex(dbHelper.SNACK_COVER_URL));
            snack.categoryID = cursor.getInt(cursor.getColumnIndex(dbHelper.SNACK_CATEGORY_ID));

            vSnack.add(snack);
            currentSnackItem.add(snack);
        }

        cursor.close();
    }
}
