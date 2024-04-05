package com.example.thebookhuntingeagle.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.thebookhuntingeagle.model.City;
import com.example.thebookhuntingeagle.model.Order;
import com.example.thebookhuntingeagle.model.Sale;
import com.example.thebookhuntingeagle.model.User;
import com.example.thebookhuntingeagle.model.enums.OrderStatus;
import com.example.thebookhuntingeagle.model.enums.ShipOption;
import com.example.thebookhuntingeagle.util.BookCondition;
import com.example.thebookhuntingeagle.util.ShareSaleOption;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public boolean updateShip(int orderId, ShipOption shipOption) {
        ContentValues values = new ContentValues();
        values.put("ship", shipOption.name());

        return update(orderId, values);
    }

    public boolean conclude(int orderId) {
        ContentValues values = new ContentValues();
        values.put("status", OrderStatus.CONCLUDED.name());

        return update(orderId, values);
    }

    public boolean cancel(int orderId) {
        ContentValues values = new ContentValues();
        values.put("status", OrderStatus.CANCELLED.name());

        return update(orderId, values);
    }

    private boolean update(int orderId, ContentValues values) {
        long r = db.update("orders", values,
                "id = ?", new String[]{String.valueOf(orderId)});

        return (r!=-1);
    }

    public List<Order> findByBuyerOrSeller(User user) {
        List<Order> orders = new ArrayList<>();
        Map<Integer, City> cities = new HashMap<>();
        Map<Integer, User> users = new HashMap<>();
        Map<Integer, Sale> sales = new HashMap<>();

        String query =
            "SELECT o.*, s.*, u_buyer.*, c_buyer.*, u_seller.*, c_seller.* " +
            "FROM orders o " +
            "LEFT JOIN sales s ON o.sale_id=s.id " +
            "LEFT JOIN  users u_buyer ON o.buyer_id=u_buyer.id "+
            "LEFT JOIN  cities c_buyer ON u_buyer.city_id=c_buyer.id " +
            "LEFT JOIN  users u_seller ON s.user_id=u_seller.id " +
            "LEFT JOIN  cities c_seller ON u_seller.city_id=c_seller.id " +
            "WHERE s.user_id=? OR o.buyer_id=? " +
            "ORDER BY o.start_date DESC ";

        Cursor res = db.rawQuery(query, new String[]{String.valueOf(user.getId()), String.valueOf(user.getId())});
        if (res != null && res.moveToFirst()) {
            do {
                //Buyer City
                int buyerCityId = res.getInt(24);
                City buyerCity = cacheCity(buyerCityId, cities, res);
                //Buyer User
                int buyerId = res.getInt(16);
                User buyer;
                if (!users.containsKey(buyerId)) {
                    //User(Integer id, Integer avatar, String name, String address, City city, String phone, String email, String password)
                    buyer = new User(
                        buyerId,
                        res.getInt(17),
                        res.getString(18),
                        res.getString(19),
                        buyerCity,
                        res.getString(21),
                        res.getString(22),
                        null
                    );
                    users.put(buyerId, buyer);
                } else {
                    buyer = users.get(buyerId);
                }
                //Seller City
                int sellerCityId = res.getInt(24);
                City sellerCity = cacheCity(sellerCityId, cities, res);
                //Seller User
                int sellerId = res.getInt(26);
                User seller;
                if (!users.containsKey(sellerId)) {
                    //User(Integer id, Integer avatar, String name, String address, City city, String phone, String email, String password)
                    seller = new User(
                            sellerId,
                            res.getInt(27),
                            res.getString(28),
                            res.getString(29),
                            sellerCity,
                            res.getString(31),
                            res.getString(32),
                            null
                    );
                    users.put(sellerId, seller);
                } else {
                    seller = users.get(buyerId);
                }
                //Sale
                int saleId = res.getInt(7);
                Sale sale;
                //Sale(Integer id, String bookTitle, String author, String edition, BookCondition condition, Double discount, Double price, ShareSaleOption shareSale, User seller) {
                if (!sales.containsKey(saleId)) {
                    sale = new Sale(
                            saleId,
                            res.getString(9),
                            res.getString(10),
                            res.getString(11),
                            BookCondition.valueOf(res.getString(12)),
                            res.getDouble(13),
                            res.getDouble(14),
                            ShareSaleOption.valueOf(res.getString(15)),
                            seller
                    );
                    sales.put(saleId, sale);
                } else
                    sale = sales.get(saleId);

                //Order(Integer id, Sale sale, User buyer, ShipOption ship, OrderStatus status, LocalDate startDate, LocalDate endDate)
                Order order = new Order(
                    res.getInt(0),
                    sale,
                    buyer,
                    ShipOption.valueOf(res.getString(3)),
                    OrderStatus.valueOf(res.getString(4)),
                    LocalDate.parse(res.getString(5)),
                    null
                );
                orders.add(order);
            } while (res.moveToNext());
            res.close();
        }
        return orders;
    }

    public City cacheCity(int cityId, Map<Integer, City> cities, Cursor rs) {
        City city;
        if (!cities.containsKey(cityId)) {
            city = new City(
                    cityId,
                    rs.getString(25)
            );
            cities.put(cityId, city);
        } else
            city = cities.get(cityId);

        return city;
    }
}
