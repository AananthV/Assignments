package com.example.simple_calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView op_view, ans_view;

    EditText en1, en2;

    Toast invalid_input;

    String op;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find views
        op_view = (TextView) findViewById(R.id.op);
        ans_view = (TextView) findViewById(R.id.answer);

        en1 = (EditText) findViewById(R.id.n1);
        en2 = (EditText) findViewById(R.id.n2);

        // Construct toast
        invalid_input = Toast.makeText(getApplicationContext(),
                "Invalid Input",
                Toast.LENGTH_SHORT);


        // Select the operand
        op = "+"; // Initial value

        RadioGroup op_select = (RadioGroup) findViewById(R.id.op_group);

        op_select.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.add:
                        changeOperand("+");
                        break;

                    case R.id.sub:
                        changeOperand("-");
                        break;

                    case R.id.mul:
                        changeOperand("×");
                        break;

                    case R.id.div:
                        changeOperand("÷");
                        break;
                }
            }
        });

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

                    float ans = 0;

                    switch (op) {
                        case "+":
                            ans = n1 + n2;
                            break;

                        case "-":
                            ans = n1 - n2;
                            break;

                        case "×":
                            ans = n1 * n2;
                            break;

                        case "÷":
                            if (n2 == 0)
                                invalid_input.show();
                            else
                                ans = n1 / n2;
                            break;
                    }

                    ans_view.setText(String.valueOf(ans));
                }
            }
        });
    }

    private void changeOperand(String operand) {
        op = operand;

        op_view.setText(operand);
    }
}