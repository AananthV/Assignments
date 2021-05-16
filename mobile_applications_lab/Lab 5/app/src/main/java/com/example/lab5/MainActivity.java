package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RadioGroup mGroup;
    Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    0);

            return;
        }

        mGroup = (RadioGroup) findViewById(R.id.radio);
        mSubmitButton = (Button) findViewById(R.id.submit);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = mGroup.getCheckedRadioButtonId();

                Intent intent;

                switch (i) {
                    case R.id.alarm:
                        intent = new Intent(getApplicationContext(), Alarm.class);
                        startActivity(intent);
                        break;
                    case R.id.email:
                        intent = new Intent(getApplicationContext(), Email.class);
                        startActivity(intent);
                        break;
                    case R.id.play:
                        intent = new Intent(getApplicationContext(), Music.class);
                        startActivity(intent);
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "Please choose an option", Toast.LENGTH_SHORT);
                }
            }
        });
    }
}