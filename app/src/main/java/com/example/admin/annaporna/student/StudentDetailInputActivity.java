package com.example.admin.annaporna.student;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.admin.annaporna.R;
import com.example.admin.annaporna.Utils;
import com.example.admin.annaporna.school.School;
import com.example.admin.annaporna.model.StudentContract;
import com.example.admin.annaporna.service.DatabaseService;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentDetailInputActivity extends AppCompatActivity {

    /*
        Used in isInputValid()
     */
    public static final int INPUT_COUNT = 6;

    private static final String _TAG = "student_input";

    private static final int IMAGE_REQUEST_CODE = 9;

    @BindView(R.id.student_picture)
    ImageView mStudentPhoto;

    @BindView(R.id.take_photo_view_btn)
    ImageButton mTakePhoto;

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
        Log.e(_TAG, "gender btn click");
        if (v.getId() == R.id.radio_btn_boys) {
            mGender = "male";
        } else {
            mGender = "female";
        }
    }

    private File createTempImage() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "JPEG_" + timeStamp;
        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File temp = null;
        try {
            temp = File.createTempFile(fileName, ".jpg", dir);
        } catch (IOException e) {
            Log.e(_TAG, "unable to create file");
            e.printStackTrace();
        }
        String filePath = "file:" + temp.getAbsolutePath();
        return temp;
    }

    public void takeStudentPhoto(View v) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            File image = createTempImage();
            if (image != null) {
                Uri uri = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        image);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, IMAGE_REQUEST_CODE);
            }
        }
    }

    private Student parseInput() {
        Student student = new Student();

        student.setName(mStudentName.getText().toString());
        student.setGender(mGender);

        student.setDob(mDob.getText().toString());

        student.setFatherGuardianName(mFatherGuardianName.getText().toString());
        student.setMotherName(mMotherName.getText().toString());
        student.setAddress(mAddress.getText().toString());
        return student;
    }

    private boolean isInputValid(Student student) {
        /*
            INPUT_COUNT is the number of input fields
            count is the local count of number of valid inputs
            if count != INPUT_COUNT, then error
                else OK
         */
        Log.e(_TAG, "in input valid");
        String errMsg = getString(R.string.blank_input_field_error);
        int count = 0;
        if (student.mGender == null) {
            Toast.makeText(this, getString(R.string.error_gender), Toast.LENGTH_LONG).show();
        } else {
            count++;
        }

        if (student.mName.length() == 0) {
            mNameLayout.setError(errMsg);
        } else {
            count++;
        }

        if(Utils.isValidDate(mDob.getText().toString())) {
            Log.e(_TAG, "valid date");
            count++;
        }

        if (student.mFatherGuardianName.length() == 0) {
            mFatherGuardianLayout.setError(errMsg);
        } else {
            count++;
        }

        if (student.mMotherName.length() == 0) {
            mMotherLayout.setError(errMsg);
        } else {
            count++;
        }

        if (student.mAddress.length() == 0) {
            mAddressLayout.setError(errMsg);
        } else {
            count++;
        }

        Log.e(_TAG, Integer.toString(count));

        if (count == INPUT_COUNT) {
            return true;
        } else {
            return false;
        }
    }

    private ContentValues parseStudentToContentValues(Student student) {
        ContentValues values = new ContentValues();
        values.put(StudentContract.StudentDetails.NAME, student.mName);
        student.mDob = Utils.convertDateToYYYYMMDDFormat(student.mDob);
        values.put(StudentContract.StudentDetails.DATE_OF_BIRTH, student.mDob);
        values.put(StudentContract.StudentDetails.GENDER, student.mGender);
        values.put(StudentContract.StudentDetails.FATHER_OR_GUARDIAN_NAME, student.mFatherGuardianName);
        values.put(StudentContract.StudentDetails.MOTHER_NAME, student.mMotherName);
        values.put(StudentContract.StudentDetails.ADDRESS, student.mAddress);
        values.put(StudentContract.StudentDetails.SCHOOL_ID, student.mSchoolId);
        return values;
    }

    private void showHealthDetailsInputChoiceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Health Details")
                .setMessage(R.string.health_dialog_msg)
                .setPositiveButton(R.string.positive_health_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(R.string.negative_health_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create().show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail_input);
        getSupportActionBar().setTitle(getString(R.string.student_details_input_title));
        ButterKnife.bind(this);

        Intent intent = getIntent();
        final String schoolId = intent.getStringExtra(School.SCHOOL_ID);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_student_added);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student student = parseInput();
                if (isInputValid(student)) {
                    Log.e(_TAG, "valid student input");
                    student.mSchoolId = schoolId;
                    ContentValues values = parseStudentToContentValues(student);
                    Intent intent = new Intent(view.getContext(), DatabaseService.class);
                    intent.putExtra(DatabaseService.DATA_TYPE, DatabaseService.STUDENT_DETAILS);
                    intent.putExtra(DatabaseService.CONTENT_VALUE, values);
                    startService(intent);
                    showHealthDetailsInputChoiceDialog();
                }
            }
        });
    }
}
