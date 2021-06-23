package com.example.teamslime.Slime.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.teamslime.Slime.model.User;

import java.util.Vector;

public class UsersDB {
    DBHelper dbHelper;
    public static Vector<User> vUser = new Vector<>();

    public static User activeUser = new User();

    public UsersDB(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void insertUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(dbHelper.USER_EMAIL, user.email);
        cv.put(dbHelper.USER_NAME, user.name);
        cv.put(dbHelper.USER_PHONE, user.phone);
        cv.put(dbHelper.USER_PASSWORD, user.password);
        cv.put(dbHelper.USER_GENDER, user.gender);

        db.insert(dbHelper.TABLE_USERS, null, cv);
        vUser.add(user);
    }

    public void updateUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(dbHelper.USER_ID, user.ID);
        cv.put(dbHelper.USER_EMAIL, user.email);
        cv.put(dbHelper.USER_NAME, user.name);
        cv.put(dbHelper.USER_PHONE, user.phone);
        cv.put(dbHelper.USER_PASSWORD, user.password);
        cv.put(dbHelper.USER_GENDER, user.gender);

        db.update(dbHelper.TABLE_USERS, cv, dbHelper.USER_ID + " = '" + user.ID + "'", null);
    }

    public void loadUser(Context context) {
        vUser.clear();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectUsers = "SELECT * FROM " + dbHelper.TABLE_USERS;

        Cursor cursor = db.rawQuery(selectUsers, null);

        while (cursor.moveToNext()) {
            User user = new User();

            user.ID = cursor.getInt(cursor.getColumnIndex(dbHelper.USER_ID));
            user.email = cursor.getString(cursor.getColumnIndex(dbHelper.USER_EMAIL));
            user.name = cursor.getString(cursor.getColumnIndex(dbHelper.USER_NAME));
            user.phone = cursor.getString(cursor.getColumnIndex(dbHelper.USER_PHONE));
            user.password = cursor.getString(cursor.getColumnIndex(dbHelper.USER_PASSWORD));
            user.gender = cursor.getString(cursor.getColumnIndex(dbHelper.USER_GENDER));

            vUser.add(user);
        }

        cursor.close();
    }

    public void loadCurrentUser(int ID) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectUsers = "SELECT * FROM " + dbHelper.TABLE_USERS + " WHERE " + dbHelper.USER_ID + " = " + ID;

        Cursor cursor = db.rawQuery(selectUsers, null);

        cursor.moveToFirst();

        activeUser.ID = cursor.getInt(cursor.getColumnIndex(dbHelper.USER_ID));
        activeUser.email = cursor.getString(cursor.getColumnIndex(dbHelper.USER_EMAIL));
        activeUser.name = cursor.getString(cursor.getColumnIndex(dbHelper.USER_NAME));
        activeUser.phone = cursor.getString(cursor.getColumnIndex(dbHelper.USER_PHONE));
        activeUser.password = cursor.getString(cursor.getColumnIndex(dbHelper.USER_PASSWORD));
        activeUser.gender = cursor.getString(cursor.getColumnIndex(dbHelper.USER_GENDER));


        cursor.close();
    }
}
