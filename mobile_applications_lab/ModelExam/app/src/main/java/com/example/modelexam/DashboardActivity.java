package com.example.modelexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

public class DashboardActivity extends AppCompatActivity {

    SharedPreferences mSharedPreferences;
    DatabaseHandler con;

    ListView mTaskList;

    TaskListCursorAdapter mTaskCursorListAdapter;

    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mSharedPreferences = getSharedPreferences("employee-app", Context.MODE_PRIVATE);

        username = mSharedPreferences.getString("username", "");

        con = new DatabaseHandler(this);

        Employee e = con.getEmployee(username);

        mTaskCursorListAdapter = new TaskListCursorAdapter(this, con.getEmployeeTasks(e.get_id()), false);

        mTaskList = findViewById(R.id.task_list);

        mTaskList.setAdapter(mTaskCursorListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                clearSharedPreferences();
                Intent i2 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i2);
                break;
        }

        return true;
    }

    private void clearSharedPreferences() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}