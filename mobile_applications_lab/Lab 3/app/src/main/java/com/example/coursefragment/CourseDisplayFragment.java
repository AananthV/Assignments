package com.example.coursefragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 */
public class CourseDisplayFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_ROLL_NUMBER = "roll-number";

    // TODO: Customize parameters
    private String mRollNumber;
    public ArrayList<Course> mSelectedCourses;

    // Recycler View
    RecyclerView mRecyclerView;
    CourseRecyclerViewAdapter mCourseRecyclerViewAdapter;

    // Roll number
    TextView mRollNumberView;

    // Back button
    Button mBackButton;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CourseDisplayFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CourseDisplayFragment newInstance(String rollNumber, ArrayList<Course> selectedCourses) {
        CourseDisplayFragment fragment = new CourseDisplayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ROLL_NUMBER, rollNumber);
        fragment.mSelectedCourses = selectedCourses;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCourseRecyclerViewAdapter = new CourseRecyclerViewAdapter(mSelectedCourses);

        if (getArguments() != null) {
            mRollNumber = getArguments().getString(ARG_ROLL_NUMBER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_display_list, container, false);

        View recyclerView = view.findViewById(R.id.list);
        mBackButton = view.findViewById(R.id.back_button);
        mRollNumberView = view.findViewById(R.id.roll_number);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFragment();
            }
        });

        mRollNumberView.setText(mRollNumber);

        // Set the adapter
        if (recyclerView instanceof RecyclerView) {
            Context context = recyclerView.getContext();
            mRecyclerView = (RecyclerView) recyclerView;
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            mRecyclerView.setAdapter(mCourseRecyclerViewAdapter);
        }
        return view;
    }

    private void closeFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }
}