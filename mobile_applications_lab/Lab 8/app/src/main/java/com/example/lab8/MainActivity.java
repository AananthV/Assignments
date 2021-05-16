package com.example.lab8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_product:
                Intent i = new Intent(getApplicationContext(), AddProductActivity.class);
                finish();
                startActivity(i);
                break;
            case R.id.view_product:
                Intent i2 = new Intent(getApplicationContext(), ShowProductActivity.class);
                startActivity(i2);
                break;
            case R.id.edit_product:
                Intent i3 = new Intent(getApplicationContext(), EditProductActivity.class);
                finish();
                startActivity(i3);
                break;
            case R.id.all_products:
                Intent i4 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i4);
                break;
        }

        return true;
    }
}