package com.example.coursefragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class CourseListFragment extends Fragment {

    RecyclerView mRecyclerView;
    CourseListRecyclerViewAdapter mRecyclerViewAdapter;
    CourseDisplayFragment mCourseDisplayFragment;
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ROLL_NUMBER = "roll";

    private ArrayList<Course> courseList = new ArrayList<Course>();
    private int mColumnCount = 1;
    private String mRollNUmber;
    private TextView rollNumber;
    private Button submit;

    public CourseListFragment() {
    }

    @SuppressWarnings("unused")
    public static CourseListFragment newInstance(int columnCount, String rollNumber) {
        CourseListFragment fragment = new CourseListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putString(ROLL_NUMBER, rollNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        courseList.add(new Course("CSPC12", "Crypto"));
        courseList.add(new Course("CSPS13", "Probability"));
        courseList.add(new Course("CSPC14", "MAD"));
        courseList.add(new Course("CSPC21", "MAD Lab"));
        courseList.add(new Course("CSPC22", "Data Structures"));
        courseList.add(new Course("CSPC32", "DS Lab"));
        courseList.add(new Course("CSPC33", "Algorithms"));
        courseList.add(new Course("CSPC34", "Automata"));
        courseList.add(new Course("CSPC35", "OS"));
        courseList.add(new Course("CSPC36", "CO"));
        courseList.add(new Course("CSPC41", "CA"));
        courseList.add(new Course("CSPC42", "DBMS"));
        courseList.add(new Course("CSPC43", "ML"));
        courseList.add(new Course("CSPC44", "Compilers"));
        courseList.add(new Course("CSPC45", "MPMC"));


        mRecyclerViewAdapter = new CourseListRecyclerViewAdapter(courseList);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mRollNUmber = getArguments().getString(ROLL_NUMBER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_list_list, container, false);

        View recyclerView = view.findViewById(R.id.list);

        if (recyclerView instanceof RecyclerView) {
            Context context = recyclerView.getContext();
            mRecyclerView = (RecyclerView) recyclerView;
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            mRecyclerView.setAdapter(mRecyclerViewAdapter);
        }

        rollNumber = (TextView) view.findViewById(R.id.roll);
        submit = (Button) view.findViewById(R.id.submit);
        rollNumber.setText(mRollNUmber);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCourseDisplayFragment = CourseDisplayFragment.newInstance(rollNumber.getText().toString(), mRecyclerViewAdapter.mSelectedCourses);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, mCourseDisplayFragment).commit();
            }
        });
        return view;
    }
}