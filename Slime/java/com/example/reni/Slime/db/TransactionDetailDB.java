package com.example.reni.Slime.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.reni.Slime.model.TransactionDetail;

import java.util.Vector;

public class TransactionDetailDB {
    DBHelper dbHelper;

    public static Vector<TransactionDetail> vTransactionDetail = new Vector<>();
    public static Vector<TransactionDetail> currentTransactionDetail = new Vector<>();
    public static Vector<TransactionDetail> specificTransactionDetail = new Vector<>();

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

    public void loadTransactionDetail(Context context){
        vTransactionDetail.clear();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectTransactionHeader = "SELECT * FROM " + dbHelper.TABLE_TRANSACTION_DETAIL;

        Cursor cursor = db.rawQuery(selectTransactionHeader, null);

        while(cursor.moveToNext()){
            TransactionDetail transactionDetail = new TransactionDetail();

            transactionDetail.transactionID = cursor.getInt(cursor.getColumnIndex(dbHelper.TRANSACTION_ID));
            transactionDetail.snackID = cursor.getInt(cursor.getColumnIndex(dbHelper.SNACK_ID));
            transactionDetail.quantity = cursor.getInt(cursor.getColumnIndex(dbHelper.QUANTITY));

            vTransactionDetail.add(transactionDetail);
        }

        cursor.close();
    }

    public void loadCurrentTransactionDetail(Context context, int userID){
        currentTransactionDetail.clear();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectCurrentTransactionHeader = "SELECT * FROM " + dbHelper.TABLE_TRANSACTION_DETAIL +
                " JOIN " + dbHelper.TABLE_TRANSACTION_HEADER + " ON " +
                dbHelper.TABLE_TRANSACTION_DETAIL + "." + dbHelper.TRANSACTION_ID + " = " + dbHelper.TABLE_TRANSACTION_HEADER + "." + dbHelper.TRANSACTION_ID +
                " WHERE " + dbHelper.USER_ID + " = " + userID;

        Cursor cursor = db.rawQuery(selectCurrentTransactionHeader, null);

        while(cursor.moveToNext()){
            TransactionDetail transactionDetail = new TransactionDetail();

            transactionDetail.transactionID = cursor.getInt(cursor.getColumnIndex(dbHelper.TRANSACTION_ID));
            transactionDetail.snackID = cursor.getInt(cursor.getColumnIndex(dbHelper.SNACK_ID));
            transactionDetail.quantity = cursor.getInt(cursor.getColumnIndex(dbHelper.QUANTITY));

            currentTransactionDetail.add(transactionDetail);
        }

        cursor.close();
    }

    public void loadSpecificTransactionDetail(Context context, int transID){
        specificTransactionDetail.clear();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectCurrentTransactionDETAIL = "SELECT * FROM " + dbHelper.TABLE_TRANSACTION_DETAIL +
                " JOIN " + dbHelper.TABLE_TRANSACTION_HEADER + " ON " +
                dbHelper.TABLE_TRANSACTION_DETAIL + "." + dbHelper.TRANSACTION_ID + " = " + dbHelper.TABLE_TRANSACTION_HEADER + "." + dbHelper.TRANSACTION_ID +
                " WHERE " + dbHelper.TABLE_TRANSACTION_DETAIL + "." + dbHelper.TRANSACTION_ID + " = " + transID;

        Cursor cursor = db.rawQuery(selectCurrentTransactionDETAIL, null);

        while(cursor.moveToNext()){
            TransactionDetail transactionDetail = new TransactionDetail();

            transactionDetail.transactionID = cursor.getInt(cursor.getColumnIndex(dbHelper.TRANSACTION_ID));
            transactionDetail.snackID = cursor.getInt(cursor.getColumnIndex(dbHelper.SNACK_ID));
            transactionDetail.quantity = cursor.getInt(cursor.getColumnIndex(dbHelper.QUANTITY));

            specificTransactionDetail.add(transactionDetail);
        }

        cursor.close();
    }
}
