package com.example.lab7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText mUsername, mPassword;
    Button mLogin;
    TextView mRegister;

    DatabaseHandler con;

    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);

        mLogin = findViewById(R.id.login);
        mRegister = findViewById(R.id.register);

        con = new DatabaseHandler(getApplicationContext());

        mSharedPreferences = getSharedPreferences("LAB7", MODE_PRIVATE);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();
                if(username.equals("") || password.equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter valid User details", Toast.LENGTH_SHORT).show();
                    return;
                }

                Boolean isLoggedIn = con.checkPassword(username, password);
                if(isLoggedIn) {
                    Toast.makeText(getApplicationContext(), "Logged In!", Toast.LENGTH_SHORT).show();

                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putString("USERNAME", username);
                    editor.commit();
                    Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Incorrect user details!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });
    }
}