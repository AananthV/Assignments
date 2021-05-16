package com.example.modelexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

public class AdminActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        DatabaseHandler con = new DatabaseHandler(this);

        EmployeeListCursorAdapter mEmployeeListCursorAdapter = new EmployeeListCursorAdapter(this, con.getAllEmployees(), false);

        ListView mEmployeeList = findViewById(R.id.employee_list);

        mEmployeeList.setAdapter(mEmployeeListCursorAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_task:
                Intent i = new Intent(getApplicationContext(), AddTaskActivity.class);
                startActivity(i);
                break;
            case R.id.logout:
                Intent i2 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i2);
                break;
        }

        return true;
    }
}