package com.example.thebookhuntingeagle.model;

import com.example.thebookhuntingeagle.model.enums.OrderStatus;
import com.example.thebookhuntingeagle.model.enums.ShipOption;

import java.io.Serializable;
import java.time.LocalDate;

public class Order implements Serializable {

    private Integer id;
    private Sale sale;
    private User buyer;
    private ShipOption ship;
    private OrderStatus status;
    private LocalDate startDate;
    private LocalDate endDate;

    public Order() {
    }

    public Order(Integer id, Sale sale, User buyer, ShipOption ship, OrderStatus status, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.sale = sale;
        this.buyer = buyer;
        this.ship = ship;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public ShipOption getShip() {
        return ship;
    }

    public void setShip(ShipOption ship) {
        this.ship = ship;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
