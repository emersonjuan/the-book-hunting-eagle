package com.example.thebookhuntingeagle.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.thebookhuntingeagle.model.City;
import com.example.thebookhuntingeagle.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDataSource {
    private DatabaseHelper dbh;
    private SQLiteDatabase db;

    public UserDataSource(Context context) {
        this.dbh = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        this.db = this.dbh.getWritableDatabase();
    }

    public void close() {
        this.dbh.close();
    }


    public boolean insert(User newUser) {
        ContentValues values = new ContentValues();
        values.put("name", newUser.getName());
        values.put("address", newUser.getAddress());
        values.put("city_id", newUser.getCity().getId());
        values.put("phone", newUser.getPhone());
        values.put("email", newUser.getEmail());
        values.put("password", newUser.getPassword());
        long r = db.insert("users", null, values);

        return (r!=-1);
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        String query = "SELECT u.*, c.* FROM users u LEFT JOIN cities c ON u.city_id=c.id ORDER BY email";
        Cursor res = db.rawQuery(query, null);
        if (res != null && res.moveToFirst()) {
            do {
                User user = new User(
                        res.getInt(0),
                        res.getString(1),
                        res.getString(2),
                        new City(res.getInt(8), res.getString(9)),
                        res.getString(5),
                        res.getString(6),
                        res.getString(7)
                );
                users.add(user);
            } while (res.moveToNext());
            res.close();
        }
        return users;
    }

    public Optional<User> findByEmail(String email) {

        String query =
                "SELECT u.*, c.* FROM users u " +
                "LEFT JOIN cities c ON u.city_id=c.id "+
                "WHERE u.email=?";
        String[] selectionArgs = { email };
        Cursor res = db.rawQuery(query, selectionArgs);
        try {
            if (res != null && res.moveToFirst()) {
                return Optional.of(new User(
                        res.getInt(0),
                        res.getString(1),
                        res.getString(2),
                        new City(res.getInt(7), res.getString(8)),
                        res.getString(4),
                        res.getString(5),
                        res.getString(6))
                );

            }
        } finally {
            if (res!= null)
                res.close();
        }
        return Optional.empty();
    }
}
