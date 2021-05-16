package com.example.coursefragment;

public class Course {
    public final String code;
    public final String name;

    public Course(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return code + ": " + name;
    }
}