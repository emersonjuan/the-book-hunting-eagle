package com.example.thebookhuntingeagle.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.thebookhuntingeagle.util.BookCondition;
import com.example.thebookhuntingeagle.util.ShareSaleOption;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Eagle.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_CITIES_TABLE =
            "CREATE TABLE " + "Cities" + " (" +
                    "id" + " INTEGER PRIMARY KEY, " +
                    "name" + " TEXT NOT NULL UNIQUE " +
                    ")";
    private static final String SQL_CREATE_USERS_TABLE =
            "CREATE TABLE " + "Users" + " (" +
                    "id" + " INTEGER PRIMARY KEY, " +
                    "avatar" + " INT NOT NULL, " +
                    "name" + " TEXT NOT NULL, " +
                    "address" + " TEXT NOT NULL, " +
                    "city_id" + " INTEGER NOT NULL, " +
                    "phone" + " TEXT NOT NULL, " +
                    "email" + " TEXT NOT NULL UNIQUE, " +
                    "password" + " TEXT NOT NULL, " +
                    "FOREIGN KEY (city_id) REFERENCES Cities (id) " +
                    ")";

    private static final String SQL_CREATE_SALES_TABLE =
            "CREATE TABLE " + "Sales" + " (" +
                    "id" + " INTEGER PRIMARY KEY, " +
                    "user_id" + " INT NOT NULL, " +
                    "book_title" + " TEXT NOT NULL, " +
                    "author" + " TEXT NOT NULL, " +
                    "edition" + " TEXT NOT NULL, " +
                    "condition" + " TEXT CHECK( condition IN ('NEW', 'GOOD', 'FAIR', 'POOR') ) NOT NULL, " +
                    "discount" + " REAL NOT NULL, " +
                    "price" + " REAL NOT NULL, " +
                    "share_sale" + " TEXT CHECK( share_sale IN ('SELL', 'SHARE') ) NOT NULL, " +
                    "FOREIGN KEY (user_id) REFERENCES Users (id) " +
                    ")";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CITIES_TABLE);
        db.execSQL(SQL_CREATE_USERS_TABLE);
        db.execSQL(SQL_CREATE_SALES_TABLE);

        //Insert  cities
        initializeCities(db);
        initializeUsers(db);
        initializeBooks(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS cities");
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS sales");
        onCreate(db);
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    private void initializeCities(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("name", "New Westminster");
        db.insert("cities", null, values);
        values.put("name", "Vancouver");
        db.insert("cities", null, values);
        values.put("name", "Burnaby");
        db.insert("cities", null, values);
        values.put("name", "Surrey");
        db.insert("cities", null, values);
        values.put("name", "Coquitlam");
        db.insert("cities", null, values);
    }

    // Initialized users for test purpose.
    private void initializeUsers(SQLiteDatabase db) {
        String[] name = {"Alessandro", "Emerson", "Patricia"};
        int[] avatar = {2131165428, 2131165430, 2131165429};
        String[] address = {"777 Royals", "2024 False Creek", "2372 Edmonds"};
        String[] phone = {"0000000001", "0000000002", "0000000003"};
        String[] email = {"alessandro@test.ca", "emerson@test.ca", "patricia@test.ca"};
        String[] password = {"2023", "2024", "2025"};

        for (int i = 0; i < name.length; i++) {
            ContentValues values = new ContentValues();
            values.put("avatar", avatar[i]);
            values.put("name", name[i]);
            values.put("address", address[i]);
            values.put("city_id", i+1);
            values.put("phone", phone[i]);
            values.put("email", email[i]);
            values.put("password", password[i]);
            db.insert("users", null, values);
        }
    }

    // Initialized books for test purpose.
    private void initializeBooks(SQLiteDatabase db){
        int[] user_id = {1, 2, 3};
        String[] book = {"The Philosophy of Snoopy", "Harry Potter and the Order of the Phoenix", "The Alchemist"};
        String[] author = {"Charles M. Schulz","J.K. Rowling","Paulo Coelho"};
        String[] edition = {"Hardcover – Illustrated","Fifth","25th anniversary edition"};
        BookCondition[] condition = {BookCondition.POOR, BookCondition.NEW, BookCondition.GOOD};
        int[] discount = {10, 5, 0};
        double[] price = {21.35, 17.21, 19.37};
        ShareSaleOption[] share_sale = {ShareSaleOption.SELL, ShareSaleOption.SELL, ShareSaleOption.SHARE};

        for (int i = 0; i < book.length; i++){
            ContentValues values = new ContentValues();
            values.put("user_id", user_id[i]);
            values.put("book_title", book[i]);
            values.put("author", author[i]);
            values.put("edition", edition[i]);
            values.put("condition", condition[i].name());
            values.put("discount", discount[i]);
            values.put("price", price[i]);
            values.put("share_sale", share_sale[i].name());
            db.insert("Sales", null, values);
        }
    }

}
