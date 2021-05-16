package com.example.lab7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText mName, mUsername, mEmail, mPhone, mPassword, mConfirmPassword;

    TextView mLogin;

    Button mRegister;

    DatabaseHandler mDatabaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mName = findViewById(R.id.reg_name);
        mUsername = findViewById(R.id.reg_username);
        mConfirmPassword = findViewById(R.id.reg_confirm_password);
        mPassword = findViewById(R.id.reg_password);
        mEmail = findViewById(R.id.reg_email);
        mPhone = findViewById(R.id.reg_contact);

        mRegister = findViewById(R.id.register);
        mLogin = findViewById(R.id.login);

        mDatabaseHandler = new DatabaseHandler(getApplicationContext());

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();
                String confirmPassword = mConfirmPassword.getText().toString();
                String email = mEmail.getText().toString();
                String phone = mPhone.getText().toString();
                String name = mName.getText().toString();

                if(username.equals("") || password.equals("") || confirmPassword.equals("") || email.equals("") || phone.equals("") || name.equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter valid details!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!password.equals(confirmPassword)) {
                    Toast.makeText(getApplicationContext(), "Confirm password not matching.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mDatabaseHandler.checkIfUserExists(username)) {
                    Toast.makeText(getApplicationContext(), "Username already registered.", Toast.LENGTH_SHORT).show();
                    return;
                }

                User user = new User(0, name, username, password, email, phone);
                mDatabaseHandler.addUser(user);

                Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }
}