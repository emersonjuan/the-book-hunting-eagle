package com.example.thebookhuntingeagle.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.thebookhuntingeagle.model.City;

import java.util.ArrayList;
import java.util.List;

public class CityDataSource {
    private DatabaseHelper dbh;
    private SQLiteDatabase db;

    public CityDataSource(Context context) {
        this.dbh = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        this.db = this.dbh.getWritableDatabase();
    }

    public void close() {
        this.dbh.close();
    }


    public boolean insert(SQLiteDatabase db, City newCity) {
        ContentValues values = new ContentValues();
        values.put("name", newCity.getName());
        long r = db.insert("cities", null, values);

        return (r!=-1);
    }

    public boolean insert(City newCity) {
        return insert(this.db, newCity);
    }
    public List<City> findAll() {
        List<City> cities = new ArrayList<>();

        String query = "SELECT * FROM cities ORDER BY name";
        Cursor res = db.rawQuery(query, null);
        if (res != null && res.moveToFirst()) {
            do {
                City city = new City(res.getInt(0), res.getString(1));
                cities.add(city);
            } while (res.moveToNext());
            res.close();
        }
        return cities;
    }
}
