package com.example.admin.annaporna.student;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.annaporna.R;
import com.example.admin.annaporna.SchoolDetailsActivity;
import com.example.admin.annaporna.model.StudentContract;

import butterknife.BindView;

/**
 * Created by Admin on 16-09-2016.
 */
public class StudentListFrag extends Fragment{
    private String mSchoolId;

    @BindView(R.id.recycler_student_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.empty_student_overview_list)
    TextView mEmptyList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_student_list, container, false);
        FloatingActionButton fab = (FloatingActionButton)v.findViewById(R.id.fab_add_student);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), StudentDetailInputActivity.class);
                intent.putExtra(SchoolDetailsActivity.SCHOOL_ID, mSchoolId);
                startActivity(intent);
            }
        });
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        mSchoolId = args.getString(SchoolDetailsActivity.SCHOOL_ID);
    }
}
