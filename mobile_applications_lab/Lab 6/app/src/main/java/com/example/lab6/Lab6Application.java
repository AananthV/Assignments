package com.example.lab6;

import android.app.Application;

public class Lab6Application extends Application {
    private String SPKey = "secret";
    private String[] SPValueKeys = {"name", "email", "contact"};

    public String getSPKey() {
        return SPKey;
    }

    public String[] getSPValueKeys() {
        return SPValueKeys;
    }
}