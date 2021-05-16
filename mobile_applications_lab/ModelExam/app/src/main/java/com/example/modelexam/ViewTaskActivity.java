package com.example.modelexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class ViewTaskActivity extends AppCompatActivity {

    DatabaseHandler con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        long task_id = getIntent().getLongExtra("task_id", 0);

        con = new DatabaseHandler(this);

        Task t = con.getTask(task_id);

        TextView mTitle = findViewById(R.id.task_title);
        TextView mDescription = findViewById(R.id.task_description);
        TextView mStart = findViewById(R.id.start_time);

        LinearLayout mAdditionalInfo = findViewById(R.id.additional_info);

        Button mVote = findViewById(R.id.done_button);

        mTitle.setText(t.get_title());
        mDescription.setText(t.get_description());
        mStart.setText(con.getDateFormatter().format(t.get_start()));

        CheckBox c = findViewById(R.id.done_checkbox);

        Button b = findViewById(R.id.done_button);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!c.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Check the checkbox!", Toast.LENGTH_SHORT).show();
                    return;
                }

                t.set_end(new Date());
                t.set_done(true);
                con.updateTask(t);

                Toast.makeText(getApplicationContext(), "Marked as done!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}