package com.example.lab7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "lab7db";
    private static final String TABLE_USERS = "users";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_USERNAME + " TEXT,"
                + KEY_PASSWORD + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_PHONE + " TEXT"
                + ")";
        sqLiteDatabase.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        onCreate(sqLiteDatabase);
    }

    void addUser(User user) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.get_name());
        values.put(KEY_USERNAME, user.get_username());
        values.put(KEY_PASSWORD, user.get_password());
        values.put(KEY_EMAIL, user.get_email());
        values.put(KEY_PHONE, user.get_phone());

        sqLiteDatabase.insert(TABLE_USERS, null, values);
        sqLiteDatabase.close();
    }

    boolean checkIfUserExists(String username) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(
                TABLE_USERS,
                new String[] {},
                KEY_USERNAME + "=?",
                new String[] { String.valueOf(username) },
                null,
                null,
                null,
                null
        );

        boolean userExists = true;
        if (cursor.getCount() <= 0)
            userExists = false;

        cursor.close();

        return userExists;
    }

    User getUser(String username) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(
                TABLE_USERS,
                new String[] {
                        KEY_ID,
                        KEY_NAME,
                        KEY_USERNAME,
                        KEY_PASSWORD,
                        KEY_EMAIL,
                        KEY_PHONE
                },
                KEY_USERNAME + "=?",
                new String[] { String.valueOf(username) },
                null,
                null,
                null,
                null
        );

        if (cursor != null)
            cursor.moveToFirst();

        User user = new User(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5)
        );

        return user;
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<User>();
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Log.d("TAG", "" + cursor.getString(0));
                User user = new User(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)
                );
                userList.add(user);
            } while (cursor.moveToNext());
        }

        return userList;
    }

    public boolean checkPassword(String username, String password) {
        if (!checkIfUserExists(username))
            return false;

        User user = this.getUser(username);

        return password.equals(user.get_password());
    }

    public int updatePassword(String username, String newPassword) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PASSWORD, newPassword);

        return sqLiteDatabase.update(TABLE_USERS, values, KEY_USERNAME + " = ?",
                new String[] { username });
    }
}
