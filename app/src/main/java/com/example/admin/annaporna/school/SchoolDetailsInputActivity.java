package com.example.admin.annaporna.school;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.admin.annaporna.R;
import com.example.admin.annaporna.service.DatabaseService;

/*
    TODO 11-9-16
    Validate input and provide appropriate error messages
    Allow recyclerview to view schools
    Allow addition of students and viewing of students
 */


public class SchoolDetailsInputActivity extends AppCompatActivity {
    public static final String _TAG = "school_detail_input";

    public static final String SCHOOL_NAME = "name";

    // type of school is either
    // Anganwadi, Pre-school, primary, upper primary, higher secondary, other
    public static final String SCHOOL_TYPE = "type";

    public static final String LOCATION_NAME = "location_name";

    public static final String NUM_BOYS = "number_boys";

    public static final String NUM_GIRLS = "number_girls";

    public static final String TOTAL_STUDENT_COUNT = "total_student_count";

    public static final String STAFF_COUNT = "staff_count";

    public static final String COOK_COUNT = "cook_count";

    public static final String HEADMASTER_NAME = "headmaster_name";

    public static final String HEADMASTER_PHONE_NUMBER = "headmaster_phone_number";

    TextInputEditText mSchoolName;

    TextInputEditText mSchoolLocation;

    Spinner mSchoolType;

    TextInputEditText mHmName;

    TextInputEditText mHmPhoneNumber;
    /*
        ####################################
        ####################################
        should the extension be stored? like
        +91?
        ####################################
        ####################################
        ####################################
     */

    TextInputEditText mTotStudentCount;

    TextInputEditText mBoysCount;

    TextInputEditText mGirlsCount;

    TextInputEditText mStaffCount;

    TextInputEditText mCookCount;

    FloatingActionButton mFab;

    private Bundle buildSchoolDetailBundle() {
        Bundle details = new Bundle();
        details.putString(SCHOOL_NAME, mSchoolName.getText().toString());
        details.putString(SCHOOL_TYPE, mSchoolType.getSelectedItem().toString());
        details.putString(LOCATION_NAME, mSchoolLocation.getText().toString());
        details.putString(HEADMASTER_NAME, mHmName.getText().toString());
        details.putString(HEADMASTER_PHONE_NUMBER, mHmPhoneNumber.getText().toString());
        details.putInt(NUM_BOYS, Integer.parseInt(mBoysCount.getText().toString()));
        details.putInt(NUM_GIRLS, Integer.parseInt(mGirlsCount.getText().toString()));
        details.putInt(TOTAL_STUDENT_COUNT, Integer.parseInt(mTotStudentCount.getText().toString()));
        details.putInt(STAFF_COUNT, Integer.parseInt(mStaffCount.getText().toString()));
        details.putInt(COOK_COUNT, Integer.parseInt(mCookCount.getText().toString()));
        return details;
    }

    private class FABClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // collect all data from edit text fields
            // put all data into an intent
            // send all data to IntentService which stores the data to db
            Intent intent = new Intent(SchoolDetailsInputActivity.this, DatabaseService.class);
            Bundle schoolDetails = buildSchoolDetailBundle();
            intent.putExtra(DatabaseService.BUNDLE_TYPE, DatabaseService.SCHOOL_DETAILS);
            intent.putExtra(DatabaseService.BUNDLE_DATA, schoolDetails);
            startService(intent);
            finish();
        }
    }


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

        mFab = (FloatingActionButton) findViewById(R.id.fab_school_added);
        mFab.setOnClickListener(new FABClickListener());
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
