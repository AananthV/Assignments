package com.example.coursefragment;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CourseListRecyclerViewAdapter extends RecyclerView.Adapter<CourseListRecyclerViewAdapter.ViewHolder> {

    private final List<Course> mValues;
    public ArrayList<Course> mSelectedCourses = new ArrayList<Course>();

    public CourseListRecyclerViewAdapter(List<Course> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_course_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mCourse = mValues.get(position);
        holder.mCourseCode.setText(mValues.get(position).code);
        holder.mCourseName.setText(mValues.get(position).name);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mCourseCode;
        public final TextView mCourseName;
        public boolean isChecked = false;
        public Course mCourse;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mCourseCode = (TextView) view.findViewById(R.id.code);
            mCourseName = (TextView) view.findViewById(R.id.name);
            mCourse = new Course(mCourseCode.getText().toString(), mCourseName.getText().toString());

            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isChecked = !isChecked;

                    if (isChecked) {
                        mSelectedCourses.add(mCourse);
                        mCourseName.setBackgroundColor(0xff669900);
                    } else {
                        mSelectedCourses.remove(mCourse);
                        mCourseName.setBackgroundColor(0xffcc0000);
                    }
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mCourseName.getText() + "'";
        }
    }
}