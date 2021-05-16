package com.example.lab8;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "lab8";

    private static final String TABLE_PRODUCT = "product";
    private static final String KEY_ID = "product_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_MRP = "mrp";
    private static final String KEY_PRICE = "price";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_PRODUCT_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PRODUCT + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_MRP + " INTEGER,"
                + KEY_PRICE + " TEXT"
                + ")";

        sqLiteDatabase.execSQL(CREATE_PRODUCT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        onCreate(sqLiteDatabase);
    }

    public void addProduct(Product product) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, product.get_name());
        values.put(KEY_MRP, product.get_mrp());
        values.put(KEY_PRICE, product.get_price());

        long user_id = sqLiteDatabase.insert(TABLE_PRODUCT, null, values);

        sqLiteDatabase.close();
    }

    public void updateProduct(Product product) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, product.get_name());
        values.put(KEY_MRP, product.get_mrp());
        values.put(KEY_PRICE, product.get_price());

        sqLiteDatabase.update(
                TABLE_PRODUCT,
                values,
                KEY_ID + "=?",
                new String[] { String.valueOf(product.get_id()) }
        );

        sqLiteDatabase.close();
    }

    public List<Product> getProducts() {
        List<Product> productList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(
                TABLE_PRODUCT,
                new String[] {
                        KEY_ID,
                        KEY_NAME,
                        KEY_MRP,
                        KEY_PRICE
                },
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getInt(3)
                );
                productList.add(product);
            } while (cursor.moveToNext());
        }

        return productList;
    }

    public Product getProduct(int p_id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(
                TABLE_PRODUCT,
                new String[] {
                        KEY_ID,
                        KEY_NAME,
                        KEY_MRP,
                        KEY_PRICE
                },
                KEY_ID + "=?",
                new String[] { String.valueOf(p_id) },
                null,
                null,
                null,
                null
        );

        if (cursor.getCount() == 0)
            return null;

        cursor.moveToFirst();

        Product product = new Product(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getInt(2),
                cursor.getInt(3)
        );

        return product;
    }
}