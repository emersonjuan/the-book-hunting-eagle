package com.example.thebookhuntingeagle.model;

import com.example.thebookhuntingeagle.util.BookCondition;
import com.example.thebookhuntingeagle.util.ShareSaleCondition;

public class Sales {
    private Integer id;
    private Integer user_id;
    private String book_title;
    private String author;
    private String edition;
    private BookCondition condition;
    private Integer discount;
    private Double price;
    private ShareSaleCondition share_sale;

    public Sales(){
    }

    public Sales(Integer id, Integer user_id, String book_title, String author, String edition,
                 BookCondition condition, Integer discount, Double price, ShareSaleCondition share_sale){

        this.id = id;
        this.user_id = user_id;
        this.book_title = book_title;
        this.author = author;
        this.edition = edition;
        this.condition = condition;
        this.discount = discount;
        this.price = price;
        this.share_sale = share_sale;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public BookCondition getCondition() {
        return condition;
    }

    public void setCondition(BookCondition condition) {
        this.condition = condition;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ShareSaleCondition getShare_sale() {
        return share_sale;
    }

    public void setShare_sale(ShareSaleCondition share_sale) {
        this.share_sale = share_sale;
    }
}

