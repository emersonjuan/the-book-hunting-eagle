package com.example.thebookhuntingeagle.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.thebookhuntingeagle.model.Order;

public class OrderDataSource {
    private DatabaseHelper dbh;
    private SQLiteDatabase db;

    public OrderDataSource(Context context) {
        this.dbh = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        this.db = this.dbh.getWritableDatabase();
    }

    public void close() {
        this.dbh.close();
    }


    public boolean insert(Order newOrder) {
        ContentValues values = new ContentValues();
        values.put("sale_id", newOrder.getSale().getId());
        values.put("buyer_id", newOrder.getBuyer().getId());
        values.put("ship", newOrder.getShip().name());
        values.put("status", newOrder.getStatus().name());
        values.put("start_date", newOrder.getStartDate().toString());

        long r = db.insert("orders", null, values);

        return (r!=-1);
    }

//    public List<City> findAll() {
//        List<City> cities = new ArrayList<>();
//
//        String query = "SELECT * FROM cities ORDER BY name";
//        Cursor res = db.rawQuery(query, null);
//        if (res != null && res.moveToFirst()) {
//            do {
//                City city = new City(res.getInt(0), res.getString(1));
//                cities.add(city);
//            } while (res.moveToNext());
//            res.close();
//        }
//        return cities;
//    }
}
