package com.example.lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ViewActivity extends AppCompatActivity {

    TextView mName, mContact, mEmail;
    Button mBack;
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        mName = (TextView) findViewById(R.id.name);
        mEmail = (TextView) findViewById(R.id.email);
        mContact = (TextView) findViewById(R.id.phone);
        mBack = (Button) findViewById(R.id.back);

        mSharedPreferences = getSharedPreferences(((Lab6Application)this.getApplication()).getSPKey(), Context.MODE_PRIVATE);
        String[] keys = ((Lab6Application)this.getApplication()).getSPValueKeys();
        mName.setText("NAME: " + mSharedPreferences.getString(keys[0], "No Name"));
        mEmail.setText("E-MAIL: " + mSharedPreferences.getString(keys[1], "No Email"));
        mContact.setText("CONTACT: " + mSharedPreferences.getString(keys[2], "No Phone"));

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}