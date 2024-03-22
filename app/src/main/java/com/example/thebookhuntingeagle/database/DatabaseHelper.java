package com.example.thebookhuntingeagle.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, "EagleDB", null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String TABLE_NAME = "users";

        String query =
                "CREATE TABLE " + TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY, " +
                "email TEXT, " +
                "password TEXT, " +
                "fullName TEXT, " +
                "address TEXT, " +
                "phone TEXT, " +
                "city TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String TABLE_NAME = "users";
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addUser(String email, String password, String fullName, String address,
                           String phone, String city) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("password", password);
        values.put("fullName", fullName);
        values.put("address", address);
        values.put("phone", phone);
        values.put("city", city);

        long r = db.insert("users", null, values);
        return (r!=-1);
    }
}
