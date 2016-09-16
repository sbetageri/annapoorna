package com.example.admin.annaporna;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.admin.annaporna.school.SchoolDetailsFrag;
import com.example.admin.annaporna.student.StudentListFrag;

/*
    TODO
    Create school detail fragment
    Create student overview fragment
 */

public class SchoolDetailsActivity extends AppCompatActivity {
    public static final String SCHOOL_ID = "school_id";
    public static final String SCHOOL_NAME = "school_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_details);
        getSupportActionBar().setTitle("SAI RAM");
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment = new StudentListFrag();

        // the args hold the schoolID
        Bundle args = getIntent().getExtras();
        fragment.setArguments(args);

        ft.add(R.id.placeholder, fragment, null);
        ft.commit();
    }
}
