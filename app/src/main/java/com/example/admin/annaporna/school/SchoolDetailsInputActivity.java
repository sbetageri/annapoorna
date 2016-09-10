package com.example.admin.annaporna.school;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.admin.annaporna.R;


public class SchoolDetailsInputActivity extends AppCompatActivity {
    TextInputEditText mSchoolName;

    TextInputEditText mSchoolLocation;

    Spinner mSchoolType;

    TextInputEditText mHmName;

    TextInputEditText mHmPhoneNumber;

    TextInputEditText mTotStudentCount;

    TextInputEditText mBoysCount;

    TextInputEditText mGirlsCount;

    TextInputEditText mStaffCount;

    TextInputEditText mCookCount;

    private void setSpinnerAdapter() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.school_types,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSchoolType.setAdapter(adapter);
    }

    private void bindViews() {
        mSchoolName = (TextInputEditText) findViewById(R.id.school_name_input);
        mSchoolLocation = (TextInputEditText) findViewById(R.id.school_location_input);
        mSchoolType = (Spinner) findViewById(R.id.school_type_spinner);
        mHmName = (TextInputEditText) findViewById(R.id.hm_name_input);
        mHmPhoneNumber = (TextInputEditText) findViewById(R.id.hm_phone_number_input);
        mTotStudentCount = (TextInputEditText)findViewById(R.id.total_student_count);
        mBoysCount = (TextInputEditText) findViewById(R.id.total_boys_count);
        mGirlsCount = (TextInputEditText) findViewById(R.id.total_girls_count);
        mStaffCount = (TextInputEditText) findViewById(R.id.total_staff_count);
        mCookCount = (TextInputEditText) findViewById(R.id.cook_count);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_details_input);
        bindViews();
        setSpinnerAdapter();
    }

    @Override
    protected void onPause() {
        super.onPause();
        StringBuilder sb = new StringBuilder();
        sb.append(mSchoolName.getText().toString());
        sb.append(mSchoolLocation.getText().toString());
        sb.append(mSchoolType.getSelectedItem().toString());
        Log.e("SAI", sb.toString());
    }
}
