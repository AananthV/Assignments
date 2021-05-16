package com.example.modelexam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "employee_app";

    public static final String TABLE_EMPLOYEE = "employee";
    public static final String TABLE_TASK = "task";

    public static final String KEY_ID = "_id";

    public static final String KEY_NAME = "name";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";

    public static final String KEY_EMPLOYEE_ID = "employee_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_START = "start";
    public static final String KEY_END = "end_time";
    public static final String KEY_DONE = "done";

    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_EMPLOYEE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EMPLOYEE + " ("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_USERNAME + " TEXT UNIQUE,"
                + KEY_NAME + " TEXT,"
                + KEY_PASSWORD + " TEXT"
                + ")";

        String CREATE_TASK_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_TASK + " ("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_EMPLOYEE_ID + " INTEGER,"
                + KEY_TITLE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_START + " DATETIME,"
                + KEY_END + " DATETIME,"
                + KEY_DONE + " INTEGER,"
                + "FOREIGN KEY (" + KEY_EMPLOYEE_ID + ") REFERENCES " + TABLE_EMPLOYEE + "(" + KEY_ID + ")"
                + ")";

        sqLiteDatabase.execSQL(CREATE_EMPLOYEE_TABLE);
        sqLiteDatabase.execSQL(CREATE_TASK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(
                "DROP TABLE IF EXISTS " + TABLE_EMPLOYEE
        );
        sqLiteDatabase.execSQL(
                "DROP TABLE IF EXISTS " + TABLE_TASK
        );

        onCreate(sqLiteDatabase);
    }

    void addEmployee(Employee e) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, e.get_name());
        values.put(KEY_USERNAME, e.get_username());
        values.put(KEY_PASSWORD, e.get_password());

        sqLiteDatabase.insert(TABLE_EMPLOYEE, null, values);

        sqLiteDatabase.close();
    }

    boolean checkIfEmployeeExists(String username) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(
                TABLE_EMPLOYEE,
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

    Employee getEmployee(String username) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(
                TABLE_EMPLOYEE,
                new String[] {
                        KEY_ID,
                        KEY_NAME,
                        KEY_USERNAME,
                        KEY_PASSWORD
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

        Employee e = new Employee(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3)
        );

        return e;
    }

    Cursor getAllEmployees() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(
                TABLE_EMPLOYEE,
                new String[] {
                        KEY_ID,
                        KEY_NAME,
                        KEY_USERNAME
                },
                null,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();

        return cursor;
    }

    public boolean checkPassword(String username, String password) {
        Employee e = this.getEmployee(username);

        return password.equals(e.get_password());
    }

    public SimpleDateFormat getDateFormatter() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    long addTask(Task t) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EMPLOYEE_ID, t.get_employee_id());
        values.put(KEY_TITLE, t.get_title());
        values.put(KEY_DESCRIPTION, t.get_description());
        values.put(KEY_START, getDateFormatter().format(t.get_start()));
        values.put(KEY_END, getDateFormatter().format(t.get_end()));
        values.put(KEY_DONE, false);

        long task_id = sqLiteDatabase.insert(TABLE_TASK, null, values);

        sqLiteDatabase.close();

        return task_id;
    }

    Task getTask(long task_id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(
                TABLE_TASK,
                new String[] {
                        KEY_ID,
                        KEY_EMPLOYEE_ID,
                        KEY_TITLE,
                        KEY_DESCRIPTION,
                        KEY_START,
                        KEY_END,
                        KEY_DONE
                },
                KEY_ID + "=?",
                new String[] { String.valueOf(task_id) },
                null,
                null,
                null,
                null
        );

        if (cursor != null)
            cursor.moveToFirst();

        try {
            Task t = new Task(
                    Integer.parseInt(cursor.getString(0)),
                    Integer.parseInt(cursor.getString(1)),
                    cursor.getString(2),
                    cursor.getString(3),
                    getDateFormatter().parse(cursor.getString(4)),
                    getDateFormatter().parse(cursor.getString(5)),
                    Integer.parseInt(cursor.getString(6)) != 0
            );

            return t;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    Cursor getAllTasks() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String current_date = this.getDateFormatter().format(new Date());

        Cursor cursor = sqLiteDatabase.query(
                TABLE_TASK,
                new String[] {
                        KEY_ID,
                        KEY_EMPLOYEE_ID,
                        KEY_TITLE,
                        KEY_DESCRIPTION,
                        KEY_START,
                        KEY_END,
                        KEY_DONE
                },
                null,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();

        return cursor;
    }

    Cursor getEmployeeTasks(long employee_id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String current_date = this.getDateFormatter().format(new Date());

        Cursor cursor = sqLiteDatabase.query(
                TABLE_TASK,
                new String[] {
                        KEY_ID,
                        KEY_EMPLOYEE_ID,
                        KEY_TITLE,
                        KEY_DESCRIPTION,
                        KEY_START,
                        KEY_END,
                        KEY_DONE
                },
                KEY_EMPLOYEE_ID + "=?",
                new String[] { String.valueOf(employee_id) },
                null,
                null,
                null
        );

        cursor.moveToFirst();

        return cursor;
    }

    void updateTask(Task t) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EMPLOYEE_ID, t.get_employee_id());
        values.put(KEY_TITLE, t.get_title());
        values.put(KEY_DESCRIPTION, t.get_description());
        values.put(KEY_START, getDateFormatter().format(t.get_start()));
        values.put(KEY_END, getDateFormatter().format(t.get_end()));
        values.put(KEY_DONE, t.is_done());

        sqLiteDatabase.update(
                TABLE_TASK,
                values,
                KEY_ID + "=?",
                new String[] { String.valueOf(t.get_id()) }
        );

        sqLiteDatabase.close();
    }
}
