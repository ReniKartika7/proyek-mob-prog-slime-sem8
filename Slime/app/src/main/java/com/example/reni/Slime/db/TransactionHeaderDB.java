package com.example.reni.Slime.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.reni.Slime.model.TransactionHeader;

import java.util.Vector;

public class TransactionHeaderDB {
    DBHelper dbHelper;

    public static Vector<TransactionHeader> vTransactionHeader = new Vector<>();

    public TransactionHeaderDB(Context context){
        dbHelper = new DBHelper(context);
    }

    public void insertTransactionHeader(TransactionHeader transactionHeader){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(dbHelper.TRANSACTION_DATE, transactionHeader.date);
        cv.put(dbHelper.USER_ID, transactionHeader.userID);

        db.insert(dbHelper.TABLE_TRANSACTION_HEADER, null, cv);
    }
}
