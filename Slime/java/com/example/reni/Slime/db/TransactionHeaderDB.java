package com.example.reni.Slime.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.reni.Slime.model.TransactionHeader;

import java.util.Vector;

public class TransactionHeaderDB {
    DBHelper dbHelper;

    public static Vector<TransactionHeader> vTransactionHeader = new Vector<>();
    public static Vector<TransactionHeader> currentTransactionHeader = new Vector<>();

    public TransactionHeaderDB(Context context){
        dbHelper = new DBHelper(context);
    }

    public void insertTransactionHeader(TransactionHeader transactionHeader){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(dbHelper.TRANSACTION_DATE, transactionHeader.date);
        cv.put(dbHelper.USER_ID, transactionHeader.userID);
        cv.put(dbHelper.ADDRESS_ID, transactionHeader.addressID);

        db.insert(dbHelper.TABLE_TRANSACTION_HEADER, null, cv);
    }

    public void loadTransactionHeader(Context context){
        vTransactionHeader.clear();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectTransactionHeader = "SELECT * FROM " + dbHelper.TABLE_TRANSACTION_HEADER;

        Cursor cursor = db.rawQuery(selectTransactionHeader, null);

        while(cursor.moveToNext()){
            TransactionHeader transactionHeader = new TransactionHeader();

            transactionHeader.ID = cursor.getInt(cursor.getColumnIndex(dbHelper.TRANSACTION_ID));
            transactionHeader.userID = cursor.getInt(cursor.getColumnIndex(dbHelper.USER_ID));
            transactionHeader.date = cursor.getString(cursor.getColumnIndex(dbHelper.TRANSACTION_DATE));
            transactionHeader.addressID = cursor.getInt(cursor.getColumnIndex(dbHelper.ADDRESS_ID));

            vTransactionHeader.add(transactionHeader);
        }

        cursor.close();
    }

    public void loadCurrentTransactionHeader(Context context, int userID){
        currentTransactionHeader.clear();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectCurrentTransactionHeader = "SELECT * FROM " + dbHelper.TABLE_TRANSACTION_HEADER + " WHERE " + dbHelper.USER_ID + " = " + userID;

        Cursor cursor = db.rawQuery(selectCurrentTransactionHeader, null);

        while(cursor.moveToNext()){
            TransactionHeader transactionHeader = new TransactionHeader();

            transactionHeader.ID = cursor.getInt(cursor.getColumnIndex(dbHelper.TRANSACTION_ID));
            transactionHeader.userID = cursor.getInt(cursor.getColumnIndex(dbHelper.USER_ID));
            transactionHeader.date = cursor.getString(cursor.getColumnIndex(dbHelper.TRANSACTION_DATE));
            transactionHeader.addressID = cursor.getInt(cursor.getColumnIndex(dbHelper.ADDRESS_ID));

            currentTransactionHeader.add(transactionHeader);
        }

        cursor.close();
    }

    public int getLastTransactionID(Context context){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectTransactionHeader = "SELECT * FROM " + dbHelper.TABLE_TRANSACTION_HEADER;

        Cursor cursor = db.rawQuery(selectTransactionHeader, null);

        cursor.moveToLast();

        return cursor.getInt(cursor.getColumnIndex(dbHelper.TRANSACTION_ID));
    }

    public TransactionHeader loadSpecificTransactionHeader(int transactionID){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectSpecificTransactionHeader = "SELECT * FROM " + dbHelper.TABLE_TRANSACTION_HEADER + " WHERE " + dbHelper.TRANSACTION_ID + " = " + transactionID;

        Cursor cursor = db.rawQuery(selectSpecificTransactionHeader, null);

        cursor.moveToFirst();
        TransactionHeader transactionHeader = new TransactionHeader();

        transactionHeader.ID = cursor.getInt(cursor.getColumnIndex(dbHelper.TRANSACTION_ID));
        transactionHeader.userID = cursor.getInt(cursor.getColumnIndex(dbHelper.USER_ID));
        transactionHeader.date = cursor.getString(cursor.getColumnIndex(dbHelper.TRANSACTION_DATE));
        transactionHeader.addressID = cursor.getInt(cursor.getColumnIndex(dbHelper.ADDRESS_ID));

        cursor.close();

        return transactionHeader;
    }
}
