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
import android.widget.ListView;
import android.widget.TextView;

public class EmployeeListCursorAdapter extends CursorAdapter {

    DatabaseHandler con;

    public EmployeeListCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);

        con = new DatabaseHandler(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.employee_list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        LinearLayout mTitle = view.findViewById(R.id.title_row);
        TextView mName = view.findViewById(R.id.name);
        TextView mUsername = view.findViewById(R.id.username);
        ListView mTaskList = view.findViewById(R.id.task_list);

        LinearLayout mAdditionalInfo = view.findViewById(R.id.additional_info);

        long id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("_id")));
        String title = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String description = cursor.getString(cursor.getColumnIndexOrThrow("username"));

        mName.setText(title);
        mUsername.setText(description);

        TaskListCursorAdapter mTaskCursorListAdapter = new TaskListCursorAdapter(context, con.getEmployeeTasks(id), false);

        mTaskList.setAdapter(mTaskCursorListAdapter);

        mTitle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mAdditionalInfo.setVisibility(
                        mAdditionalInfo.getVisibility() == View.VISIBLE ?
                                View.GONE : View.VISIBLE
                );
            }
        });
    }
}
