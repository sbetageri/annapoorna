package com.example.admin.annaporna;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.admin.annaporna.school.SchoolDetailsInputActivity;

public class MainActivity extends AppCompatActivity {
    private static final String _TAG = "main_activity";
    private static final int SCHOOL_DETAILS_ACTIVITY_CODE = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, SchoolDetailsInputActivity.class);
        startActivity(intent);
    }
}
