package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Email extends AppCompatActivity {

    EditText mEmail, mSubject, mBody;
    Button mSendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        mEmail = findViewById(R.id.email);
        mSubject = findViewById(R.id.subject);
        mBody = findViewById(R.id.body);

        mSendButton = findViewById(R.id.send);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mEmail.getText().toString().equals("") || mSubject.getText().toString().equals("") || mBody.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter Valid Input", Toast.LENGTH_SHORT).show();
                    return;
                }

                String toArr[] = {mEmail.getText().toString()};

                Intent i = new Intent(Intent.ACTION_SENDTO);
                i.setData(Uri.parse("mailto:")); // only email apps should handle this
                i.putExtra(Intent.EXTRA_EMAIL, toArr);
                i.putExtra(Intent.EXTRA_TEXT, mSubject.getText().toString());
                i.putExtra(Intent.EXTRA_SUBJECT, mBody.getText().toString());
                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivity(i);
                }
            }
        });
    }
}