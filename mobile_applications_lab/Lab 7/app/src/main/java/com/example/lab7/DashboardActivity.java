package com.example.lab7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class DashboardActivity extends AppCompatActivity {

    DatabaseHandler con;

    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mSharedPreferences = getSharedPreferences("username", Context.MODE_PRIVATE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reset_pass:
                Intent i = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                startActivity(i);
                break;
            case R.id.logout:
                clearSharedPreferences();
                Intent i2 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i2);
                break;
            case R.id.exit:
                finish();
                System.exit(0);
        }

        return true;
    }

    private void clearSharedPreferences() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}