package com.example.lab8;

public class Product {
    long _id;
    String _name;
    Integer _mrp, _price;

    public Product(long _id, String _name, Integer _mrp, Integer _price) {
        this._id = _id;
        this._name = _name;
        this._mrp = _mrp;
        this._price = _price;
    }

    public Product() {
    }

    public long get_id() {
        return _id;
    }

    public String get_name() {
        return _name;
    }

    public Integer get_mrp() {
        return _mrp;
    }

    public Integer get_price() {
        return _price;
    }
}