package com.example.admin.annaporna.student;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.admin.annaporna.R;

public class StudentDetailInputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail_input);
        getSupportActionBar().setTitle(getString(R.string.student_details_input_title));
    }
}
