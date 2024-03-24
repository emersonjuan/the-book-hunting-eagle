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

}
