package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText mNameInput, mRollInput;
    Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(getApplicationContext(), "Welcome to Activity 1", Toast.LENGTH_SHORT).show();

        mNameInput = (EditText) findViewById(R.id.name_input);
        mRollInput = (EditText) findViewById(R.id.roll_input);

        mSubmitButton = (Button) findViewById(R.id.submit_button);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mNameInput.getText().toString();
                String roll = mRollInput.getText().toString();

                if (name.equals("") || roll.equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter valid Name and Roll Number", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent i = new Intent(getApplicationContext(), SecondActivity.class);
                i.putExtra("NAME", name);
                i.putExtra("ROLL", Integer.parseInt(roll));

                startActivity(i);
            }
        });
    }
}