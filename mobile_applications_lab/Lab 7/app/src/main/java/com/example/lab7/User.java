package com.example.lab7;

public class User {
    int _id;
    String _name, _username, _password, _email, _phone;

    public User() {
    }

    public User(int _id, String _name, String _username, String _password, String _email, String _phone) {
        this._id = _id;
        this._name = _name;
        this._username = _username;
        this._password = _password;
        this._email = _email;
        this._phone = _phone;
    }

    public int get_id() {
        return _id;
    }

    public String get_name() {
        return _name;
    }

    public String get_username() {
        return _username;
    }

    public String get_password() {
        return _password;
    }

    public String get_email() {
        return _email;
    }

    public String get_phone() {
        return _phone;
    }

    public void set_password(String _password) {
        this._password = _password;
    }
}
