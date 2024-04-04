package com.example.thebookhuntingeagle.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.thebookhuntingeagle.model.City;
import com.example.thebookhuntingeagle.model.Sale;
import com.example.thebookhuntingeagle.model.User;
import com.example.thebookhuntingeagle.util.BookCondition;
import com.example.thebookhuntingeagle.util.ShareSaleOption;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaleDataSource {
    private DatabaseHelper dbh;
    private SQLiteDatabase db;

    public SaleDataSource(Context context) { this.dbh = new DatabaseHelper(context); }

    public void open() throws SQLException {
        this.db = this.dbh.getWritableDatabase();
    }

    public void close() {
        this.dbh.close();
    }

    public boolean insert(Sale newBook) {
        ContentValues values = new ContentValues();
        values.put("user_id", newBook.getSeller().getId());
        values.put("book_title", newBook.getBookTitle());
        values.put("author", newBook.getAuthor());
        values.put("edition", newBook.getEdition());
        values.put("condition", newBook.getCondition().name());
        values.put("discount", newBook.getDiscount());
        values.put("price", newBook.getPrice());
        values.put("share_sale", newBook.getShareSale().name());
        long r = db.insert("sales", null, values);

        return (r!=-1);
    }

    public List<Sale> findAll() {

        //The SQL query
        String query =
                "SELECT * FROM sales s " +
                "LEFT JOIN users u ON s.user_id=u.id " +
                "LEFT JOIN cities c ON u.city_id=c.id ORDER BY book_title";
        //Query execution and result
        Cursor rs = db.rawQuery(query, null);

        //Format result set into List
        return makeSaleList(rs);
    }

    public List<Sale> findAvailableBooksByTitle(String partialBookTitle, int buyer_id) {

        //The SQL query
        String query =
                "SELECT * FROM sales s " +
                        "LEFT JOIN users u ON s.user_id=u.id " +
                        "LEFT JOIN cities c ON u.city_id=c.id " +
                        "LEFT JOIN (SELECT sale_id FROM orders WHERE status = 'CREATED') o " +
                        "ON s.id = o.sale_id " +
                        "WHERE o.sale_id IS NULL " +
                        "AND LOWER(s.book_title) LIKE LOWER(?) " +
                        "AND s.user_id <> ?" +
                        "ORDER BY book_title ";
        //Query execution and result
        Cursor rs = db.rawQuery(query, new String[]{"%" + partialBookTitle + "%", String.valueOf(buyer_id)});

        //Format result set into List
        return makeSaleList(rs);
    }

    public List<Sale> findByTitle(String partialBookTitle) {

        //The SQL query
        String query =
                "SELECT * FROM sales s " +
                        "LEFT JOIN users u ON s.user_id=u.id " +
                        "LEFT JOIN cities c ON u.city_id=c.id " +
                        "WHERE LOWER(s.book_title) LIKE LOWER(?) " +
                        "ORDER BY book_title";
        //Query execution and result
        Cursor rs = db.rawQuery(query, new String[]{"%" + partialBookTitle + "%"});

        //Format result set into List
        return makeSaleList(rs);
    }

    private List<Sale> makeSaleList(Cursor rs) {
        //Variables for caching
        Map<Integer, City> cities = new HashMap<>();
        Map<Integer, User> users = new HashMap<>();
        List<Sale> sales = new ArrayList<>();

        if (rs != null && rs.moveToFirst()) {
            do {
                //Caching cities
                int cityId = rs.getInt(17);
                City city;
                if (!cities.containsKey(cityId)) {
                    city = new City(cityId, rs.getString(18));
                    cities.put(cityId, city);
                } else
                    city = cities.get(cityId);
                //Caching users
                int userId = rs.getInt(9);
                User user;
                if (!users.containsKey(userId)) {
                    user = new User(
                            userId,                            //id
                            rs.getInt(10),         //avatar
                            rs.getString(11),      //name
                            rs.getString(12),      //address
                            city,                              //city
                            rs.getString(14),      //phone
                            rs.getString(15),      //e-mail
                            null                               //password
                    );
                    users.put(userId, user);
                } else
                    user = users.get(userId);
                //The book itself
                Sale sale = new Sale(
                        rs.getInt(0),                              //id
                        rs.getString(2),                           //bookTitle
                        rs.getString(3),                           //author
                        rs.getString(4),                           //edition
                        BookCondition.valueOf(rs.getString(5)),    //condition
                        rs.getDouble(6),                           //discount
                        rs.getDouble(7),                           //price
                        ShareSaleOption.valueOf(rs.getString(8)),  //share_sale
                        user                                                   //seller
                );
                sales.add(sale);
            } while (rs.moveToNext());
            rs.close();
        }
        return sales;
    }
}
