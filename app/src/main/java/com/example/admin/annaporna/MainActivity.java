package com.example.admin.annaporna;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.admin.annaporna.school.SchoolListFrag;

public class MainActivity extends AppCompatActivity {
    private static final String _TAG = "main_activity";
    private static final int SCHOOL_DETAILS_ACTIVITY_CODE = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle(getString(R.string.main_activity_title));
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.frag_container, new SchoolListFrag(), null);
        ft.commit();
    }
}
