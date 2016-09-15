package com.example.admin.annaporna;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class SchoolDetailsActivity extends AppCompatActivity {
    public static final String SCHOOL_ID = "school_id";
    public static final String SCHOOL_NAME = "school_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_details);
        getSupportActionBar().setTitle("SAI RAM");
    }
}
