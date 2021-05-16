package com.example.lab8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddProductActivity extends AppCompatActivity {

    EditText mName, mMRP, mPrice;
    Button mAdd;

    DatabaseHandler mDatabaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        mDatabaseHandler = new DatabaseHandler(this);

        mName = findViewById(R.id.product_name);
        mMRP = findViewById(R.id.product_mrp);
        mPrice = findViewById(R.id.product_price);

        mAdd = findViewById(R.id.add_product);

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mName.getText().toString();
                String s_mrp = mMRP.getText().toString();
                String s_price = mPrice.getText().toString();

                if (name.equals("") || s_mrp.equals("") || s_price.equals("")) {
                    Toast.makeText(getApplicationContext(), "Invalid input", Toast.LENGTH_SHORT).show();
                    return;
                }

                int mrp = Integer.parseInt(s_mrp);
                int price = Integer.parseInt(s_price);

                Product p = new Product(0, name, mrp, price);

                mDatabaseHandler.addProduct(p);

                Toast.makeText(getApplicationContext(), "Product Added!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }
}