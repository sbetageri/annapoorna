package com.example.admin.annaporna.school;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

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

    // count of the number of input fields
    public static final int INPUT_FIELD_COUNT = 10;

    public static final int INVALID_INPUT = 0;

    public static final int VALID_INPUT = 1;

    public String ERROR_MSG;

    TextInputLayout mSchoolNameLayout;
    TextInputEditText mSchoolName;

    TextInputLayout mSchoolLocationLayout;
    TextInputEditText mSchoolLocation;

    Spinner mSchoolType;

    TextInputLayout mHmNameLayout;
    TextInputEditText mHmName;

    TextInputLayout mHmPhoneNumberLayout;
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

    TextInputLayout mTotStudentCountLayout;
    TextInputEditText mTotStudentCount;

    TextInputLayout mBoysCountLayout;
    TextInputEditText mBoysCount;

    TextInputLayout mGirlsCountLayout;
    TextInputEditText mGirlsCount;

    TextInputLayout mStaffCountLayout;
    TextInputEditText mStaffCount;

    TextInputLayout mCookCountLayout;
    TextInputEditText mCookCount;

    FloatingActionButton mFab;

    /*
        How is input validated?
            if the length of the input of each editText element is gr8 than 0, then the input is valid
            for each valid input, VALID_INPUT is returned
            if the total number of valid inputs is equal to the number of INPUT_FIELD_COUNT, then
            input is valid
            else
            input is invalid
     */

    private boolean areAllInputValid() {
        int count = areSchoolDetailsValid() + isHmDetailsValid() + isCountValid();
        if(count == INPUT_FIELD_COUNT) {
            return true;
        } else {
            return false;
        }
    }

    private int areSchoolDetailsValid() {
        return isSchoolLocationValid() + isSchoolNameValid() + isSchoolTypeValid();
    }

    private int isSchoolNameValid() {
        if(mSchoolName.getText().length() == 0) {
            mSchoolNameLayout.setError(ERROR_MSG);
            return INVALID_INPUT;
        }
        return VALID_INPUT;
    }

    private int isSchoolLocationValid() {
        if(mSchoolLocation.getText().length() == 0) {
            mSchoolLocationLayout.setError(ERROR_MSG);
            return INVALID_INPUT;
        }
        return VALID_INPUT;
    }

    private int isSchoolTypeValid() {
        if(mSchoolType.getSelectedItem().toString().length() == 0) {
            return INVALID_INPUT;
        }
        return VALID_INPUT;
    }

    private int isHmDetailsValid() {
        int count = 0;
        if(mHmName.getText().length() == 0) {
            mHmNameLayout.setError(ERROR_MSG);
        } else {
            count += VALID_INPUT;
        }
        if(mHmPhoneNumber.getText().length() == 0) {
            mHmPhoneNumberLayout.setError(ERROR_MSG);
        } else {
            count += VALID_INPUT;
        }
        return count;
    }

    private int isCountValid() {
        int count = 0;
        if(mBoysCount.getText().length() == 0) {
            mBoysCountLayout.setError(ERROR_MSG);
        } else {
            count += VALID_INPUT;
        }
        if(mGirlsCount.getText().length() == 0) {
            mGirlsCountLayout.setError(ERROR_MSG);
        } else {
            count += VALID_INPUT;
        }
        if(mTotStudentCount.getText().length() == 0) {
            mTotStudentCountLayout.setError(ERROR_MSG);
        } else {
            count += VALID_INPUT;
        }
        if(mCookCount.getText().length() == 0) {
            mCookCountLayout.setError(ERROR_MSG);
        } else {
            count += VALID_INPUT;
        }
        if(mStaffCount.getText().length() == 0) {
            mStaffCountLayout.setError(ERROR_MSG);
        } else {
            count += VALID_INPUT;
        }
        return count;
    }

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
            if(areAllInputValid()) {
                Intent intent = new Intent(SchoolDetailsInputActivity.this, DatabaseService.class);
                Bundle schoolDetails = buildSchoolDetailBundle();
                intent.putExtra(DatabaseService.BUNDLE_TYPE, DatabaseService.SCHOOL_DETAILS);
                intent.putExtra(DatabaseService.BUNDLE_DATA, schoolDetails);
                startService(intent);
                finish();
            } else {
                Toast.makeText(SchoolDetailsInputActivity.this, R.string.invalid_input_toast_msg, Toast.LENGTH_LONG).show();
            }
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
        mSchoolNameLayout = (TextInputLayout)findViewById(R.id.school_name_input_layout);
        mSchoolLocationLayout = (TextInputLayout)findViewById(R.id.school_location_input_layout);

        mHmNameLayout = (TextInputLayout)findViewById(R.id.hm_name_input_layout);
        mHmPhoneNumberLayout = (TextInputLayout)findViewById(R.id.hm_phone_input_layout);

        mTotStudentCountLayout = (TextInputLayout)findViewById(R.id.tot_count_input_layout);
        mBoysCountLayout = (TextInputLayout)findViewById(R.id.boys_count_input_layout);
        mGirlsCountLayout = (TextInputLayout)findViewById(R.id.girls_count_input_layout);

        mStaffCountLayout = (TextInputLayout)findViewById(R.id.staff_count_input_layout);
        mCookCountLayout = (TextInputLayout)findViewById(R.id.cook_count_input_layout);

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
        ERROR_MSG = getString(R.string.blank_input_field_error);
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
