package com.example.modelexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText mName, mUsername, mPassword, mConfirmPassword;

    TextView mLogin;

    Button mRegister;

    DatabaseHandler con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mName = findViewById(R.id.reg_name);
        mUsername = findViewById(R.id.reg_username);
        mConfirmPassword = findViewById(R.id.reg_confirm_password);
        mPassword = findViewById(R.id.reg_password);

        mRegister = findViewById(R.id.register);
        mLogin = findViewById(R.id.login);

        con = new DatabaseHandler(getApplicationContext());

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();
                String confirmPassword = mConfirmPassword.getText().toString();
                String name = mName.getText().toString();

                if(username.equals("") || password.equals("") || confirmPassword.equals("") || name.equals("") || username.equals("admin")) {
                    Toast.makeText(getApplicationContext(), "Invalid Details", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!password.equals(confirmPassword)) {
                    Toast.makeText(getApplicationContext(), "Password Mismatch", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(con.checkIfEmployeeExists(username)) {
                    Toast.makeText(getApplicationContext(), "Username Already Exists", Toast.LENGTH_SHORT).show();
                    return;
                }

                Employee e = new Employee(0, name, username, password);
                con.addEmployee(e);

                Toast.makeText(getApplicationContext(), "Registration Success", Toast.LENGTH_SHORT).show();

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