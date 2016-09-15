package com.example.admin.annaporna.school;

import android.database.Cursor;
import android.util.Log;

import com.example.admin.annaporna.model.StudentContract;

/**
 * Created by Admin on 15-09-2016.
 */
public class School {
    private static final String _TAG = "school";

    public String mSchoolId;

    public String mSchoolName;
    public String mSchoolCode;
    public String mSchoolLocation;
    public String mSchoolType;

    public String mHmName;
    public String mHmPhoneNumber;

    public int mNumBoys;
    public int mNumGirls;
    public int mTotStudents;
    public int mNumStaff;
    public int mNumCooks;

    public School(Cursor cursor) {
        cursor.moveToFirst();
        Log.e(_TAG, "cursor size : " + Integer.toString(cursor.getCount()));
        Log.e(_TAG, "name index : " + Integer.toString(cursor.getColumnIndex(StudentContract.SchoolDetails.SCHOOL_NAME)));
        mSchoolId = cursor.getString(cursor.getColumnIndex(StudentContract.SchoolDetails._ID));
        mSchoolName = cursor.getString(cursor.getColumnIndex(StudentContract.SchoolDetails.SCHOOL_NAME));
        mSchoolLocation = cursor.getString(cursor.getColumnIndex(StudentContract.SchoolDetails.LOCATION_NAME));
        mSchoolType = cursor.getString(cursor.getColumnIndex(StudentContract.SchoolDetails.SCHOOL_TYPE));
        mSchoolCode = cursor.getString(cursor.getColumnIndex(StudentContract.SchoolDetails.SCHOOL_ANNAPORNA_CODE));

        mHmName = cursor.getString(cursor.getColumnIndex(StudentContract.SchoolDetails.HEADMASTER_NAME));
        mHmPhoneNumber = cursor.getString(cursor.getColumnIndex(StudentContract.SchoolDetails.HEADMASTER_PHONE_NUMBER));

        mNumBoys = cursor.getInt(cursor.getColumnIndex(StudentContract.SchoolDetails.NUM_BOYS));
        mNumGirls = cursor.getInt(cursor.getColumnIndex(StudentContract.SchoolDetails.NUM_GIRLS));
        mTotStudents = cursor.getInt(cursor.getColumnIndex(StudentContract.SchoolDetails.TOTAL_STUDENT_COUNT));
        mNumStaff = cursor.getInt(cursor.getColumnIndex(StudentContract.SchoolDetails.STAFF_COUNT));
        mNumCooks = cursor.getInt(cursor.getColumnIndex(StudentContract.SchoolDetails.COOK_COUNT));
    }

}
