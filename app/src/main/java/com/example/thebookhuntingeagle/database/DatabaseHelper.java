package com.example.thebookhuntingeagle.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CITIES_TABLE);
        db.execSQL(SQL_CREATE_USERS_TABLE);

        //Insert  cities
        initializeCities(db);
        initializeUsers(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS cities");
        db.execSQL("DROP TABLE IF EXISTS users");
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
            values.put("city_id", i);
            values.put("phone", phone[i]);
            values.put("email", email[i]);
            values.put("password", password[i]);
            db.insert("users", null, values);
        }
    }

}
