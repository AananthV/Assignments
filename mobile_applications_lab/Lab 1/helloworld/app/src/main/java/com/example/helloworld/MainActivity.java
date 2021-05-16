package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView ans_view;

    EditText en1, en2;

    Toast invalid_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find views
        ans_view = (TextView) findViewById(R.id.answer);

        en1 = (EditText) findViewById(R.id.n1);
        en2 = (EditText) findViewById(R.id.n2);

        // Construct toast
        invalid_input = Toast.makeText(getApplicationContext(),
                "Invalid Input",
                Toast.LENGTH_SHORT);

        Button equalButton = (Button) findViewById(R.id.equal_button);

        equalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check for null input error
                if (en1.getText().toString().equals("") || en2.getText().toString().equals("")) {
                    invalid_input.show();
                } else {
                    float n1 = Float.parseFloat(en1.getText().toString());
                    float n2 = Float.parseFloat(en2.getText().toString());

                    ans_view.setText(String.valueOf(n1 + n2));
                }
            }
        });
    }
}