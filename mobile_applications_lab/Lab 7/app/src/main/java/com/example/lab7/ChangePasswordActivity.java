package com.example.lab7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePasswordActivity extends AppCompatActivity {

    String mUsername;

    EditText mOldPassword, mNewPassword, mConfirmPassword;
    Button mChangePassword;

    DatabaseHandler mDatabaseHandler;

    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        mDatabaseHandler = new DatabaseHandler(this);

        mOldPassword = findViewById(R.id.old_password);
        mNewPassword = findViewById(R.id.new_password);
        mConfirmPassword = findViewById(R.id.confirm_password);

        mChangePassword = findViewById(R.id.change_password);

        mChangePassword.setOnClickListener(this::onChangePassword);

        mSharedPreferences = getSharedPreferences("LAB7", Context.MODE_PRIVATE);
        mUsername = mSharedPreferences.getString("USERNAME", "");
    }

    public void onChangePassword(View view) {
        String oldPassword = mOldPassword.getText().toString();
        String newPassword = mNewPassword.getText().toString();
        String confirmPassword = mConfirmPassword.getText().toString();

        if (oldPassword.equals("") || newPassword.equals("") || confirmPassword.equals("")) {
            Toast.makeText(getApplicationContext(), "Enter valid input!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!mDatabaseHandler.checkPassword(mUsername, oldPassword)) {
            Toast.makeText(getApplicationContext(), "Old password doesn't match!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(getApplicationContext(), "Confirm password doesn't match!", Toast.LENGTH_SHORT).show();
            return;
        }

        mDatabaseHandler.updatePassword(mUsername, newPassword);
        Toast.makeText(getApplicationContext(), "Password changed", Toast.LENGTH_SHORT).show();
    }
}