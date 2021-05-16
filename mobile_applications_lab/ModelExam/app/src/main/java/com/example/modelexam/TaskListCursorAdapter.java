package com.example.modelexam;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TaskListCursorAdapter extends CursorAdapter {

    public TaskListCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.task_list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView mTitle = view.findViewById(R.id.task_title);
        TextView mDescription = view.findViewById(R.id.task_description);
        TextView mStart = view.findViewById(R.id.start_time);
        TextView mEnd = view.findViewById(R.id.end_time);

        LinearLayout mAdditionalInfo = view.findViewById(R.id.additional_info);

        Button mVote = view.findViewById(R.id.done_button);

        long id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("_id")));
        String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
        String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
        String start = cursor.getString(cursor.getColumnIndexOrThrow("start"));
        String end = cursor.getString(cursor.getColumnIndexOrThrow("end_time"));

        mTitle.setText(title);
        mDescription.setText(description);
        mStart.setText(start);
        mEnd.setText(end);

        boolean done = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("done"))) != 0;

        if (done) {
            view.setBackgroundColor(0xff669900);
            mVote.setVisibility(View.GONE);
            mEnd.setVisibility(View.VISIBLE);
        }

        mTitle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mAdditionalInfo.setVisibility(
                        mAdditionalInfo.getVisibility() == View.VISIBLE ?
                                View.GONE : View.VISIBLE
                );
            }
        });

        mVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ViewTaskActivity.class);
                i.putExtra("task_id", id);
                context.startActivity(i);
            }
        });
    }
}
