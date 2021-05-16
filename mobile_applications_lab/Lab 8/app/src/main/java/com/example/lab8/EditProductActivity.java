package com.example.lab8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class EditProductActivity extends AppCompatActivity {

    int product_id;

    EditText mId, mName, mMRP, mPrice;
    Button mSubmit, mEdit;

    LinearLayout mIdLayout, mFormLayout;

    DatabaseHandler mDatabaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        mId = findViewById(R.id.product_id);
        mName = findViewById(R.id.product_name);
        mMRP = findViewById(R.id.product_mrp);
        mPrice = findViewById(R.id.product_price);

        mSubmit = findViewById(R.id.submit_product_id);
        mEdit = findViewById(R.id.edit_product);

        mIdLayout = findViewById(R.id.layout_product_id);
        mFormLayout = findViewById(R.id.layout_product_form);

        mDatabaseHandler = new DatabaseHandler(this);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s_product_id = mId.getText().toString();

                if (s_product_id.equals("")) {
                    Toast.makeText(getApplicationContext(), "Invalid input", Toast.LENGTH_SHORT).show();
                    return;
                }

                product_id = Integer.parseInt(s_product_id);

                Product p = mDatabaseHandler.getProduct(product_id);

                mName.setText(p.get_name());
                mMRP.setText("" + p.get_mrp());
                mPrice.setText("" + p.get_price());

                mIdLayout.setVisibility(View.GONE);
                mFormLayout.setVisibility(View.VISIBLE);
            }
        });

        mEdit.setOnClickListener(new View.OnClickListener() {
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

                Product p = new Product(product_id, name, mrp, price);

                if (p == null) {
                    Toast.makeText(getApplicationContext(), "Product doesn't exist", Toast.LENGTH_SHORT).show();
                    return;
                }

                mDatabaseHandler.updateProduct(p);

                Toast.makeText(getApplicationContext(), "Edit Successful", Toast.LENGTH_SHORT).show();

                finish();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }
}