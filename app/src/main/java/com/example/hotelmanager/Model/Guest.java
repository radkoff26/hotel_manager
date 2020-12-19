package com.example.hotelmanager.Model;

import java.io.Serializable;

public class Guest implements Serializable {
    private Integer id;
    private String name;
    private Integer room;
    private Double totalPrice;
    private String sex;

    public Guest(Integer id, String name, Integer room, Double totalPrice, String sex) {
        this.id = id;
        this.name = name;
        this.room = room;
        this.totalPrice = totalPrice;
        this.sex = sex;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getRoom() {
        return room;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public String getSex() {
        return sex;
    }
}
