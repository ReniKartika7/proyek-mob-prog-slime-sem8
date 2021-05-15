package com.example.reni.Slime.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.reni.Slime.model.TransactionDetail;

import java.util.Vector;

public class TransactionDetailDB {
    DBHelper dbHelper;

    public static Vector<TransactionDetail> vTransactionDetail = new Vector<>();

    public TransactionDetailDB(Context context){
        dbHelper = new DBHelper(context);
    }

    public void insertTransactionDetail(TransactionDetail transactionDetail){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(dbHelper.TRANSACTION_ID, transactionDetail.transactionID);
        cv.put(dbHelper.SNACK_ID, transactionDetail.snackID);
        cv.put(dbHelper.QUANTITY, transactionDetail.quantity);

        db.insert(dbHelper.TABLE_TRANSACTION_DETAIL, null, cv);
    }
}
