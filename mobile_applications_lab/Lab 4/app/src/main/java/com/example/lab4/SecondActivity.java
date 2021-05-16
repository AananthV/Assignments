package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    EditText mMessageInput;
    RadioGroup mActionGroup;
    Button mSubmitButton, mBackButton;

    String name;
    Integer roll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mMessageInput = (EditText) findViewById(R.id.message_input);
        mActionGroup = (RadioGroup) findViewById(R.id.action_group);
        mSubmitButton = (Button) findViewById(R.id.launch_button);
        mBackButton = (Button) findViewById(R.id.back_button);

        name = getIntent().getStringExtra("NAME");
        roll = getIntent().getIntExtra("ROLL", 0);

        Toast.makeText(getApplicationContext(), "Welcome to Activity 2 " + name + " (" + roll + ")", Toast.LENGTH_SHORT).show();

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMessageInput.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter Valid Message", Toast.LENGTH_SHORT).show();
                    return;
                }

                int selectedId = mActionGroup.getCheckedRadioButtonId();

                switch (selectedId) {
                    case R.id.message:
                        Uri uri = Uri.parse("smsto:");
                        Intent i1 = new Intent(Intent.ACTION_SENDTO, uri);
                        i1.putExtra("sms_body", mMessageInput.getText().toString());
                        startActivity(i1);
                        break;

                    case R.id.email:
                        Intent i2 = new Intent(Intent.ACTION_SENDTO);
                        i2.setData(Uri.parse("mailto:")); // only email apps should handle this
                        i2.putExtra(Intent.EXTRA_TEXT, mMessageInput.getText().toString());
                        if (i2.resolveActivity(getPackageManager()) != null) {
                            startActivity(i2);
                        }
                        break;

                    case R.id.search:
                        Intent i3 = new Intent(Intent.ACTION_WEB_SEARCH);
                        i3.putExtra(SearchManager.QUERY, mMessageInput.getText().toString());
                        startActivity(i3);
                        break;

                    default:
                        Toast.makeText(getApplicationContext(), "Please choose an option", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(activityIntent);
                finish();
            }
        });
    }
}