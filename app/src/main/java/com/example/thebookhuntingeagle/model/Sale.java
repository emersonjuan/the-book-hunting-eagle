package com.example.thebookhuntingeagle.model;

import com.example.thebookhuntingeagle.util.BookCondition;
import com.example.thebookhuntingeagle.util.ShareSaleOption;

public class Sale {
    private Integer id;
    private String bookTitle;
    private String author;
    private String edition;
    private BookCondition condition;
    private Double discount;
    private Double price;
    private ShareSaleOption shareSale;
    private User seller;

    public Sale(){
    }

    public Sale(Integer id, String bookTitle, String author, String edition,
                BookCondition condition, Double discount, Double price,
                ShareSaleOption shareSale, User seller) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.author = author;
        this.edition = edition;
        this.condition = condition;
        this.discount = discount;
        this.price = price;
        this.shareSale = shareSale;
        this.seller = seller;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
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

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ShareSaleOption getShareSale() {
        return shareSale;
    }

    public void setShareSale(ShareSaleOption shareSale) {
        this.shareSale = shareSale;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }
}

