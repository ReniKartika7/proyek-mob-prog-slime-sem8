package com.example.reni.Slime.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "SlimeDB";
    public static final int DB_VERSION = 1;

    // Tabel User
    public static final String TABLE_USERS = "Users";
    public static final String USER_ID = "UserID";
    public static final String USER_EMAIL = "UserEmail";
    public static final String USER_NAME = "UserName";
    public static final String USER_PHONE = "UserPhone";
    public static final String USER_PASSWORD = "UserPassword";
    public static final String USER_GENDER = "UserGender";

    public static String SQL_CREATE_USER_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + "(" +
                    USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    USER_EMAIL + " TEXT," +
                    USER_NAME + " TEXT," +
                    USER_PHONE + " TEXT," +
                    USER_PASSWORD + " TEXT," +
                    USER_GENDER + " TEXT" +
                    ")";

    public static String SQL_DROP_USER_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_USERS;

    //Tabel Address
    public static final String TABLE_ADDRESS = "Address";
    public static final String ADDRESS_ID = "AddressID";
    public static final String ADDRESS_DETAIL = "AddressDetail";
    public static final String ADDRESS_FULL_NAME = "FullName";
    public static final String ADDRESS_PHONE_NUMBER = "PhoneNumber";
    public static final String ADDRESS_LATITUDE = "AddressLatitude";
    public static final String ADDRESS_LONGITUDE = "AddressLongitude";
    public static final String ADDRESS_PROVINCE = "AddressProvince";
    public static final String ADDRESS_DISTRICT = "AddressDistrict";
    public static final String ADDRESS_SUB_DISTRICT = "AddressSubDistrict";
    public static final String ADDRESS_POSTAL_CODE = "AddressPostalCode";

    public static String SQL_CREATE_ADDRESS_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_ADDRESS + "(" +
                    ADDRESS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    USER_ID + " INTEGER, " +
                    ADDRESS_DETAIL + " TEXT," +
                    ADDRESS_FULL_NAME + " TEXT," +
                    ADDRESS_PHONE_NUMBER + " TEXT," +
                    ADDRESS_LATITUDE + " TEXT," +
                    ADDRESS_LONGITUDE + " TEXT," +
                    ADDRESS_PROVINCE + " TEXT," +
                    ADDRESS_DISTRICT + " TEXT," +
                    ADDRESS_SUB_DISTRICT + " TEXT," +
                    ADDRESS_POSTAL_CODE + " TEXT," +
                    " FOREIGN KEY (" + USER_ID + ") REFERENCES " + TABLE_USERS + "(" + USER_ID + ")" +
                    ")";

    public static String SQL_DROP_ADDRESS_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_ADDRESS;

    // Tabel Snack Category ID
    public static final String TABLE_SNACK_CATEGORY = "SnackCategory";
    public static final String SNACK_CATEGORY_ID = "SnackCategoryID";
    public static final String SNACK_CATEGORY_NAME = "SnackCategoryName";
    public static final String SNACK_CATEGORY_COVER_URL = "SnackCategoryCoverUrl";

    public static String SQL_CREATE_SNACK_CATEGORY_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_SNACK_CATEGORY + "(" +
                    SNACK_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    SNACK_CATEGORY_NAME + " TEXT," +
                    SNACK_CATEGORY_COVER_URL + " TEXT" +
                    ")";

    public static String SQL_DROP_SNACK_CATEGORY_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_SNACK_CATEGORY;

    // Tabel Snack
    public static final String TABLE_SNACKS = "Snacks";
    public static final String SNACK_ID = "SnackID";
    public static final String SNACK_NAME = "SnackName";
    public static final String SNACK_PRICE = "SnackPrice";
    public static final String SNACK_STOCK = "SnackStock";
    public static final String SNACK_COVER_URL = "SnackCoverUrl";
    public static final String SNACK_DETAIL = "SnackDetail";

    public static String SQL_CREATE_SNACK_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_SNACKS + "(" +
                    SNACK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    SNACK_NAME + " TEXT," +
                    SNACK_PRICE + " INTEGER," +
                    SNACK_STOCK + " INTEGER," +
                    SNACK_COVER_URL + " TEXT," +
                    SNACK_CATEGORY_ID + " INTEGER," +
                    SNACK_DETAIL + " TEXT," +
                    " FOREIGN KEY (" + SNACK_CATEGORY_ID + ") REFERENCES " + TABLE_SNACK_CATEGORY + "(" + SNACK_CATEGORY_ID + ")" +
                    ")";

    public static String SQL_DROP_SNACK_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_SNACKS;

    // Tabel Cart
    public static final String TABLE_CART = "Cart";
    public static final String CART_ID = "CartID";
    public static final String QUANTITY = "Quantity";


    public static String SQL_CREATE_CART_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_CART + "(" +
                    CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USER_ID + " INTEGER, " +
                    SNACK_ID + " INTEGER," +
                    QUANTITY + " INTEGER, "+
                    " FOREIGN KEY (" + USER_ID + ") REFERENCES " + TABLE_USERS + "(" + USER_ID + ")," +
                    " FOREIGN KEY (" + SNACK_ID + ") REFERENCES " + TABLE_SNACKS + "(" + SNACK_ID + ")," +
                    " UNIQUE (" + CART_ID + ", " + SNACK_ID + ", " + USER_ID + ")" +
                    ")";

    public static String SQL_DROP_CART_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_CART;

    // Tabel Transaction Header
    public static final String TABLE_TRANSACTION_HEADER = "TransactionHeader";
    public static final String TRANSACTION_ID = "TransactionID";
    public static final String TRANSACTION_DATE = "TransactionDate";

    public static String SQL_CREATE_TRANSACTION_HEADER_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_TRANSACTION_HEADER + "(" +
                    TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USER_ID + " INTEGER, " +
                    ADDRESS_ID + " INTEGER," +
                    TRANSACTION_DATE + " DATE, " +
                    " FOREIGN KEY (" + USER_ID + ") REFERENCES " + TABLE_USERS + "(" + USER_ID + ")," +
                    " FOREIGN KEY (" + ADDRESS_ID + ") REFERENCES " + TABLE_ADDRESS + "(" + ADDRESS_ID + ")" +
                    ")";

    public static String SQL_DROP_TRANSACTION_HEADER_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_TRANSACTION_HEADER;

    // Tabel Transaction Detail
    public static final String TABLE_TRANSACTION_DETAIL = "TransactionDetail";

    public static String SQL_CREATE_TRANSACTION_DETAIL_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_TRANSACTION_DETAIL + "(" +
                    TRANSACTION_ID + " INTEGER," +
                    SNACK_ID + " INTEGER," +
                    QUANTITY + " INTEGER, "+
                    " FOREIGN KEY (" + TRANSACTION_ID + ") REFERENCES " + TABLE_TRANSACTION_HEADER + "(" + TRANSACTION_ID + ")," +
                    " FOREIGN KEY (" + SNACK_ID + ") REFERENCES " + TABLE_SNACKS + "(" + SNACK_ID + ")," +
                    " PRIMARY KEY (" + TRANSACTION_ID + ", " + SNACK_ID + ")" +
                    ")";

    public static String SQL_DROP_TRANSACTION_DETAIL_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_TRANSACTION_DETAIL;

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER_TABLE);
        db.execSQL(SQL_CREATE_SNACK_CATEGORY_TABLE);
        db.execSQL(SQL_CREATE_SNACK_TABLE);
        db.execSQL(SQL_CREATE_ADDRESS_TABLE);
        db.execSQL(SQL_CREATE_TRANSACTION_HEADER_TABLE);
        db.execSQL(SQL_CREATE_CART_TABLE);
        db.execSQL(SQL_CREATE_TRANSACTION_DETAIL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int OldVersion, int NewVersion) {
        db.execSQL(SQL_DROP_CART_TABLE);
        db.execSQL(SQL_DROP_TRANSACTION_HEADER_TABLE);
        db.execSQL(SQL_DROP_ADDRESS_TABLE);
        db.execSQL(SQL_DROP_SNACK_TABLE);
        db.execSQL(SQL_DROP_SNACK_CATEGORY_TABLE);
        db.execSQL(SQL_DROP_USER_TABLE);

        onCreate(db);
    }
}
