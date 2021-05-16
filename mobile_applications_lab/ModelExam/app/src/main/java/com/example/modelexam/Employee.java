package com.example.modelexam;

public class Employee {

    long _id;
    String _name, _username, _password;

    public Employee(long _id, String _name, String _username, String _password) {
        this._id = _id;
        this._name = _name;
        this._username = _username;
        this._password = _password;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_username() {
        return _username;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }
}
