package com.example.admin.annaporna.service;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.admin.annaporna.model.StudentContract;
import com.example.admin.annaporna.model.StudentProvider;
import com.example.admin.annaporna.model.UriHelper;
import com.example.admin.annaporna.school.SchoolDetailsInputActivity;

import java.util.Set;

/**
 * Created by Admin on 11-09-2016.
 */
public class DatabaseService extends IntentService {
    private static final String _TAG = "database_service";

    // DATA_TYPE denotes whether student, school or health details need
    // to be pushed to the db
    public static final String DATA_TYPE = "bundle_type";

    // CONTENT_VALUE is the tag to get the bundle with the respective data
    public static final String CONTENT_VALUE = "bundle_data";

    public static final int NO_DETAILS = 100;
    public static final int SCHOOL_DETAILS = 101;
    public static final int STUDENT_DETAILS = 102;
    public static final int HEALTH_DETAILS = 103;
    public static final int EXTRA_DETAILS = 104;

    public DatabaseService() {
        super("DatabaseService");
    }

    private void logBundle(Bundle bundle) {
        Set<String> keys = bundle.keySet();
        for(String s : keys) {
            Log.e(_TAG, s + " : " + bundle.getString(s, "not a string value"));
        }
    }

    private ContentValues parseSchoolBundleToCV(Bundle details) {
        ContentValues values = new ContentValues();
        String schoolName = details.getString(SchoolDetailsInputActivity.SCHOOL_NAME);
        String schoolType = details.getString(SchoolDetailsInputActivity.SCHOOL_TYPE);
        String locationName = details.getString(SchoolDetailsInputActivity.LOCATION_NAME);

        String hmName = details.getString(SchoolDetailsInputActivity.HEADMASTER_NAME);
        String hmNumber = details.getString(SchoolDetailsInputActivity.HEADMASTER_PHONE_NUMBER);

        int numBoys = details.getInt(SchoolDetailsInputActivity.NUM_BOYS);
        int numGirls = details.getInt(SchoolDetailsInputActivity.NUM_GIRLS);
        int numStudents = details.getInt(SchoolDetailsInputActivity.TOTAL_STUDENT_COUNT);
        Log.e(_TAG, "total students : " + numStudents);

        int numStaff = details.getInt(SchoolDetailsInputActivity.STAFF_COUNT);
        int numCook = details.getInt(SchoolDetailsInputActivity.COOK_COUNT);

        values.put(StudentContract.SchoolDetails.SCHOOL_NAME, schoolName);
        values.put(StudentContract.SchoolDetails.SCHOOL_TYPE, schoolType);
        values.put(StudentContract.SchoolDetails.LOCATION_NAME, locationName);

        values.put(StudentContract.SchoolDetails.HEADMASTER_NAME, hmName);
        values.put(StudentContract.SchoolDetails.HEADMASTER_PHONE_NUMBER, hmNumber);

        values.put(StudentContract.SchoolDetails.NUM_BOYS, numBoys);
        values.put(StudentContract.SchoolDetails.NUM_GIRLS, numGirls);
        values.put(StudentContract.SchoolDetails.TOTAL_STUDENT_COUNT, numStudents);

        values.put(StudentContract.SchoolDetails.STAFF_COUNT, numStaff);
        values.put(StudentContract.SchoolDetails.COOK_COUNT, numCook);

        return values;
    }

    private boolean isSchoolPresentInDb(ContentResolver resolver, ContentValues data) {
        String name = data.getAsString(SchoolDetailsInputActivity.SCHOOL_NAME);
        String location = data.getAsString(SchoolDetailsInputActivity.LOCATION_NAME);

        Uri uri = UriHelper.doesSchoolExistUri(name, location);

        Cursor cursor = resolver.query(uri, null, null, null, null);
        if(cursor.getCount() > 0) {
            return true;
        }
        return false;
    }

    private void pushSchoolDetailsToDb(ContentResolver resolver, ContentValues details) {
        Uri uri = UriHelper.insertSchoolUri();
        resolver.insert(uri, details);
    }

    private boolean isStudentPresentInDb(ContentResolver resolver, ContentValues data) {
        String name = data.getAsString(StudentContract.StudentDetails.NAME);
        String dob = data.getAsString(StudentContract.StudentDetails.DATE_OF_BIRTH);
        String fatherGuardianName = data.getAsString(StudentContract.StudentDetails.FATHER_OR_GUARDIAN_NAME);
        Uri uri = UriHelper.doesStudentExistUri(name, dob, fatherGuardianName);
        Cursor cursor = resolver.query(uri, null, null, null, null);
        if(cursor != null && cursor.getCount() == 0) {
            return false;
        }
        return true;
    }

    private void pushStudentDetailsToDb(ContentResolver resolver, ContentValues data) {
        Log.e(_TAG, "pushing to db");
        Uri uri = UriHelper.insertStudentUri();
        resolver.insert(uri, data);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ContentResolver resolver = getContentResolver();
        int bundleType = intent.getIntExtra(DATA_TYPE, NO_DETAILS);
        ContentValues data = intent.getParcelableExtra(CONTENT_VALUE);
        switch (bundleType) {
            case SCHOOL_DETAILS:
                Log.e(_TAG, "school_details");
                if( !isSchoolPresentInDb(resolver, data)) {
                    pushSchoolDetailsToDb(resolver, data);
                } else {
                    Log.e(_TAG, "school already exists");
                    // TODO
                    // display toast informing user that school exists
                }
                break;

            case STUDENT_DETAILS:
                Log.e(_TAG, "student_details");
                if(!isStudentPresentInDb(resolver, data)) {
                    pushStudentDetailsToDb(resolver, data);
                } else {
                    Log.e(_TAG, "student already exists in school");
                }
                break;

            case NO_DETAILS:
                break;

            default:
                break;
        }
    }
}
