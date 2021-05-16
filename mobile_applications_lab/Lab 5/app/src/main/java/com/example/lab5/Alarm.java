package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;

public class Alarm extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    EditText mMessage;
    TextView mTime;
    Button mSetButton, mPick;

    int mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        mTime = findViewById(R.id.alarm_time);
        mMessage = findViewById(R.id.alarm_message);
        mSetButton = findViewById(R.id.set_alarm);
        mPick = (Button) findViewById(R.id.pick);

        mPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR);
                int minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(Alarm.this, Alarm.this, hour, minute, true);
                timePickerDialog.show();
            }
        });
        mSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTime.getText().toString().equals("") || mMessage.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter Valid Input", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
                i.putExtra(AlarmClock.EXTRA_MESSAGE, mMessage.getText().toString());
                i.putExtra(AlarmClock.EXTRA_HOUR, mHour);
                i.putExtra(AlarmClock.EXTRA_MINUTES, mMinute);
                startActivity(i);
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        mHour = hourOfDay;
        mMinute = minute;
        mTime.setText(mHour + ":" + mMinute);
    }
}