package com.example.thebookhuntingeagle.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.thebookhuntingeagle.model.Sales;

public class SalesDataSource {
    private DatabaseHelper dbh;
    private SQLiteDatabase db;

    public SalesDataSource(Context context) { this.dbh = new DatabaseHelper(context); }

    public void open() throws SQLException {
        this.db = this.dbh.getWritableDatabase();
    }
    public boolean insert(Sales newBook) {
        ContentValues values = new ContentValues();
        values.put("user_id", newBook.getUser_id());
        values.put("book_title", newBook.getBook_title());
        values.put("author", newBook.getAuthor());
        values.put("edition", newBook.getEdition());
        values.put("condition", String.valueOf(newBook.getCondition()));
        values.put("discount", newBook.getDiscount());
        values.put("price", newBook.getPrice());
        values.put("share_sale", String.valueOf(newBook.getShare_sale()));
        long r = db.insert("sales", null, values);

        return (r!=-1);
    }
}
