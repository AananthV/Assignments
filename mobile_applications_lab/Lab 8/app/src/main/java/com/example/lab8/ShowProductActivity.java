package com.example.lab8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ShowProductActivity extends AppCompatActivity {

    int product_id;

    EditText mId;
    TextView mName, mMRP, mPrice;
    Button mSubmit;

    LinearLayout mIdLayout, mFormLayout;

    DatabaseHandler mDatabaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);

        mId = findViewById(R.id.product_id);
        mName = findViewById(R.id.product_name);
        mMRP = findViewById(R.id.product_mrp);
        mPrice = findViewById(R.id.product_price);

        mSubmit = findViewById(R.id.submit_product_id);

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

                if (p == null) {
                    Toast.makeText(getApplicationContext(), "Product doesn't exist", Toast.LENGTH_SHORT).show();
                    return;
                }

                mName.setText(p.get_name());
                mMRP.setText("" + p.get_mrp());
                mPrice.setText("" + p.get_price());

                mIdLayout.setVisibility(View.GONE);
                mFormLayout.setVisibility(View.VISIBLE);
            }
        });
    }
}