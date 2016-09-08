package com.example.admin.annaporna.models;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Admin on 08-09-2016.
 */
public class StudentProvider extends ContentProvider {

    public static final String DATABASE_NAME = "annaporna_school_details";

    public static final int DATABASE_VERSION = 1;

    private static final String AUTHORITY = StudentContract.AUTHORITY;

    private static final String SCHOOL = StudentContract.SchoolDetails.PATH;

    private static final String STUDENT = StudentContract.StudentDetails.PATH;

    private static final String HEALTH = StudentContract.HealthReport.PATH;

    private static final String EXTRA = StudentContract.ExtraInformation.PATH;

    private static UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int SCHOOL_CODE = 101;

    // code for a specific school
    private static final int SCHOOL_SPECIFIC_CODE = 102;

    // code to get all student details from a school
    private static final int SCHOOL_ALL_STUDENTS_CODE = 103;

    private static final int STUDENT_CODE = 104;

    // code to get a specific student details
    private static final int STUDENT_SPECIFIC_CODE = 105;

    // code to get all health reports for a student
    private static final int STUDENT_HEALTH_CODE = 106;

    private static final int HEALTH_CODE = 107;

    // get the specific health report
    private static final int HEALTH_SPECIFIC_CODE = 108;

    // code for extra information related to a health report
    private static final int EXTRA_CODE = 109;

    private Context mContext;
    private StudentDbHelper mDbHelper;

    private void buildUriMatcher() {
        sUriMatcher.addURI(AUTHORITY, SCHOOL, SCHOOL_CODE);
        sUriMatcher.addURI(AUTHORITY, SCHOOL + "/#", SCHOOL_SPECIFIC_CODE);
        sUriMatcher.addURI(AUTHORITY, SCHOOL + "/" + STUDENT, SCHOOL_ALL_STUDENTS_CODE);

        sUriMatcher.addURI(AUTHORITY, STUDENT, STUDENT_CODE);
        sUriMatcher.addURI(AUTHORITY, STUDENT + "/#", STUDENT_SPECIFIC_CODE);
        sUriMatcher.addURI(AUTHORITY, STUDENT + "/" + HEALTH, STUDENT_HEALTH_CODE);

        sUriMatcher.addURI(AUTHORITY, HEALTH, HEALTH_CODE);
        sUriMatcher.addURI(AUTHORITY, HEALTH + "/#", HEALTH_SPECIFIC_CODE);

        sUriMatcher.addURI(AUTHORITY, EXTRA, EXTRA_CODE);
    }

    @Override
    public int delete(Uri uri,
                      String selection,
                      String[] selectionArgs) {
        return 0;
    }

    @Override
    public boolean onCreate() {
        mContext = getContext();
        mDbHelper = new StudentDbHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION);
        if(mDbHelper == null) {
            return true;
        } else {
            return false;
        }
    }

    @Nullable
    @Override
    public Cursor query(Uri uri,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri,
                      ContentValues contentValues) {
        return null;
    }

    @Override
    public int update(Uri uri,
                      ContentValues contentValues,
                      String selection,
                      String[] selectionArgs) {
        return 0;
    }
}
