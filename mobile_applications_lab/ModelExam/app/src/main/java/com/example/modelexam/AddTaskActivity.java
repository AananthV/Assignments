package com.example.modelexam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    EditText mTitle, mDescription, mUsername;

    TextView mStart;

    Button mPickStart, mSubmit;

    int mYear, mMonth, mDay, mHour, mMinute;
    Date start;

    SharedPreferences mSharedPreferences;
    DatabaseHandler con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        mPickStart = findViewById(R.id.pick_start);
        mTitle = findViewById(R.id.task_title);
        mDescription = findViewById(R.id.task_description);
        mUsername = findViewById(R.id.username);

        mStart = findViewById(R.id.start_time);

        mSubmit = findViewById(R.id.submit);

        con = new DatabaseHandler(this);

        mPickStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker();
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = mTitle.getText().toString();
                String desc = mDescription.getText().toString();
                String username = mUsername.getText().toString();

                if(title.equals("") || desc.equals("") || username.equals("") || start == null) {
                    Toast.makeText(getApplicationContext(), "Enter valid info!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!con.checkIfEmployeeExists(username)) {
                    Toast.makeText(getApplicationContext(), "Employee does not exist!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Employee e = con.getEmployee(username);

                Date end = new Date();

                Task t = new Task(0, e.get_id(), title, desc, start, end,false);
                long poll_id = con.addTask(t);

                Toast.makeText(getApplicationContext(), "Task Added!", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getApplicationContext(), AdminActivity.class);
                finish();
                startActivity(i);
            }
        });
    }

    private void datePicker(){
        Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddTaskActivity.this, AddTaskActivity.this, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        mYear = year;
        mMonth = month;
        mDay = day;
        timePicker();
    }

    @Override
    public void onTimeSet(TimePicker view, int hour, int minute) {
        mHour = hour;
        mMinute = minute;
        Calendar c = Calendar.getInstance();
        c.set(mYear, mMonth, mDay, mHour, mMinute);
        start = c.getTime();
        mStart.setText(mHour + ":" + mMinute + "," + mMonth + "-" + mDay + "-" + mYear);
    }

    private void timePicker(){
        Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.HOUR);
        int mMonth = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(AddTaskActivity.this, AddTaskActivity.this, mYear, mMonth, true);
        timePickerDialog.show();
    }
}