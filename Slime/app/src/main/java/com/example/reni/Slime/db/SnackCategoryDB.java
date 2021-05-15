package com.example.reni.Slime.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.reni.Slime.model.SnackCategory;

import java.util.Vector;

public class SnackCategoryDB {
    DBHelper dbHelper;

    public static Vector<SnackCategory> vSnackCategory = new Vector<>();
    public static SnackCategory activeSnackCategory = new SnackCategory();

    public static boolean SNACK_CATEGORY_HAS_INSERTED = false;

    public SnackCategoryDB(Context context){
        dbHelper = new DBHelper(context);
    }

    public void insertSnackCategory(SnackCategory snackCategory){
        Log.i("HAHAHA", "OMYGOD");
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(dbHelper.SNACK_CATEGORY_NAME, snackCategory.name);
        cv.put(dbHelper.SNACK_CATEGORY_COVER_URL, snackCategory.coverUrl);

        db.insert(dbHelper.TABLE_SNACK_CATEGORY, null, cv);
    }

    public void loadSnackCategory(Context context){
        vSnackCategory.clear();


        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectSnackCategory = "SELECT * FROM " + dbHelper.TABLE_SNACK_CATEGORY;

        Cursor cursor = db.rawQuery(selectSnackCategory, null);

        while(cursor.moveToNext()){
            Log.i("HAHASQL", "MASUKGA");
            SnackCategory snackCategory = new SnackCategory();

            snackCategory.ID = cursor.getInt(cursor.getColumnIndex(dbHelper.SNACK_CATEGORY_ID));
            snackCategory.name = cursor.getString(cursor.getColumnIndex(dbHelper.SNACK_CATEGORY_NAME));
            snackCategory.coverUrl = cursor.getString(cursor.getColumnIndex(dbHelper.SNACK_CATEGORY_COVER_URL));

            vSnackCategory.add(snackCategory);
        }

        cursor.close();
    }
}
