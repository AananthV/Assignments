package com.example.coursefragment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    CourseListFragment mCourseListFragment;
    Button submitRoll;
    EditText rollNum;
    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rollNum = (EditText) findViewById(R.id.rollInput);
        submitRoll = (Button) findViewById(R.id.submitRoll);
        ll = (LinearLayout) findViewById(R.id.init);

        submitRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), rollNum.getText().toString(), Toast.LENGTH_SHORT).show();

                mCourseListFragment = CourseListFragment.newInstance(1, rollNum.getText().toString());
                getSupportFragmentManager().beginTransaction().add(
                        R.id.fragment,
                        mCourseListFragment,
                        "CourseDisplayFragment"
                ).commit();
            }
        });
    }
}