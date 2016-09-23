package com.example.admin.annaporna.student;

import android.content.ContentValues;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.admin.annaporna.R;
import com.example.admin.annaporna.school.School;
import com.example.admin.annaporna.school.SchoolDetailsActivity;
import com.example.admin.annaporna.model.StudentContract;
import com.example.admin.annaporna.service.DatabaseService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentDetailInputActivity extends AppCompatActivity {

    /*
        Used in isInputValid()
     */
    public static final int INPUT_COUNT = 6;

    private Student mStudent;

    @BindView(R.id.student_name_input_layout)
    TextInputLayout mNameLayout;

    @BindView(R.id.student_name_input)
    TextInputEditText mStudentName;

    String mGender;

    @BindView(R.id.dob_input_layout)
    TextInputLayout mDobLayout;

    @BindView(R.id.date_dob_input)
    TextInputEditText mDob;

    @BindView(R.id.father_guardian_input_layout)
    TextInputLayout mFatherGuardianLayout;

    @BindView(R.id.father_guardian_input)
    TextInputEditText mFatherGuardianName;

    @BindView(R.id.mother_input_layout)
    TextInputLayout mMotherLayout;

    @BindView(R.id.mother_input)
    TextInputEditText mMotherName;

    @BindView(R.id.student_address_input_layout)
    TextInputLayout mAddressLayout;

    @BindView(R.id.student_address_input)
    TextInputEditText mAddress;

    public void onGenderBtnClick(View v) {
        if(v.getId() == R.id.radio_btn_boys) {
            mGender = "male";
        } else {
            mGender = "female";
        }
    }

    private int verifyDate() {
        // date is supposed to be dd/mm/yyyy
        String input = mDob.getText().toString();
        String[] date = input.split("/");
        int day = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        if(day < 1 || day > 31) {
            return 0;
        }
        if(month < 0 && month > 13) {
            return 0;
        }
        if(date[2].length() != 4) {
            return 0;
        }
        return 1;
    }

    private void parseInput() {
        if(mStudent == null) {
            mStudent = new Student();
        }

        mStudent.setName(mStudentName.getText().toString());
        mStudent.setGender(mGender);

        String dob = convertDateToYYYYMMDDFormat(mDob.getText().toString());
        mStudent.setDob(dob);

        mStudent.setFatherGuardianName(mFatherGuardianName.getText().toString());
        mStudent.setMotherName(mMotherName.getText().toString());
        mStudent.setAddress(mAddress.getText().toString());
    }

    private boolean isInputValid() {
        /*
            INPUT_COUNT is the number of input fields
            count is the local count of number of valid inputs
            if count != INPUT_COUNT, then error
                else OK
         */
        String errMsg = getString(R.string.blank_input_field_error);
        int count = 0;
        if(mStudent.mGender == null) {
            Toast.makeText(this, getString(R.string.error_gender), Toast.LENGTH_LONG).show();
        } else {
            count++;
        }

        if(mStudent.mName.length() == 0) {
            mNameLayout.setError(errMsg);
        } else {
            count++;
        }

        count += verifyDate();

        if(mStudent.mFatherGuardianName.length() == 0) {
            mFatherGuardianLayout.setError(errMsg);
        } else {
            count++;
        }

        if(mStudent.mMotherName.length() == 0) {
            mMotherLayout.setError(errMsg);
        } else {
            count++;
        }

        if(mStudent.mAddress.length() == 0) {
            mAddressLayout.setError(errMsg);
        } else {
            count++;
        }

        if(count == INPUT_COUNT) {
            return true;
        } else {
            return false;
        }
    }

    private String convertDateToYYYYMMDDFormat(String date) {
        String[] dob = date.split("/");
        StringBuilder sb = new StringBuilder();
        sb.append(dob[2]);
        sb.append("/");
        sb.append(dob[1]);
        sb.append("/");
        sb.append(dob[0]);
        return sb.toString();
    }

    private ContentValues parseStudentToContentValues() {
        ContentValues values = new ContentValues();
        values.put(StudentContract.StudentDetails.NAME, mStudent.mName);
        values.put(StudentContract.StudentDetails.DATE_OF_BIRTH, mStudent.mDob);
        values.put(StudentContract.StudentDetails.GENDER, mStudent.mGender);
        values.put(StudentContract.StudentDetails.FATHER_OR_GUARDIAN_NAME, mStudent.mFatherGuardianName);
        values.put(StudentContract.StudentDetails.MOTHER_NAME, mStudent.mMotherName);
        values.put(StudentContract.StudentDetails.ADDRESS, mStudent.mAddress);
        values.put(StudentContract.StudentDetails.SCHOOL_ID, mStudent.mSchoolId);
        return values;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail_input);
        getSupportActionBar().setTitle(getString(R.string.student_details_input_title));
        ButterKnife.bind(this);
        mStudent = new Student();

        Intent intent = getIntent();
        String schoolId = intent.getStringExtra(School.SCHOOL_ID);

        mStudent.mSchoolId = schoolId;

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab_student_added);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parseInput();
                if(isInputValid()) {
                    ContentValues values = parseStudentToContentValues();
                    Intent intent = new Intent(view.getContext(), DatabaseService.class);
                    intent.putExtra(DatabaseService.DATA_TYPE, DatabaseService.STUDENT_DETAILS);
                    intent.putExtra(DatabaseService.CONTENT_VALUE, values);
                    startService(intent);
                    finish();
                }
            }
        });
    }
}
