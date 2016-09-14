package com.example.admin.annaporna.service;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.admin.annaporna.model.StudentContract;
import com.example.admin.annaporna.model.StudentProvider;
import com.example.admin.annaporna.school.SchoolDetailsInputActivity;

import java.util.Set;

/**
 * Created by Admin on 11-09-2016.
 */
public class DatabaseService extends IntentService {
    private static final String _TAG = "database_service";

    public static final String BUNDLE_TYPE = "bundle_type";
    public static final String BUNDLE_DATA = "bundle_data";

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

    private boolean isSchoolPresentInDb(ContentResolver resolver, Bundle data) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content");
        builder.authority(StudentContract.AUTHORITY);
        builder.appendPath(StudentContract.SchoolDetails.PATH);
        builder.appendPath(StudentContract.SchoolDetails.EXISTS);

        String name = data.getString(SchoolDetailsInputActivity.SCHOOL_NAME);
        builder.appendQueryParameter(StudentProvider.QUERY_SCHOOL_NAME, name);

        String location = data.getString(SchoolDetailsInputActivity.LOCATION_NAME);
        builder.appendQueryParameter(StudentProvider.QUERY_SCHOOL_LOCATION, location);

        Cursor cursor = resolver.query(builder.build(), null, null, null, null);
        if(cursor.getCount() > 0) {
            return true;
        }
        return false;
    }

    private void pushSchoolDetailsToDb(ContentResolver resolver, Bundle details) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content");
        builder.authority(StudentContract.AUTHORITY);
        builder.appendPath(StudentContract.SchoolDetails.PATH);
        ContentValues values = parseSchoolBundleToCV(details);
        resolver.insert(builder.build(), values);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ContentResolver resolver = getContentResolver();
        int bundleType = intent.getIntExtra(BUNDLE_TYPE, NO_DETAILS);
        Bundle data = intent.getBundleExtra(BUNDLE_DATA);
        switch (bundleType) {
            case SCHOOL_DETAILS:
                Log.e(_TAG, "school_details");
                logBundle(data);
                if( !isSchoolPresentInDb(resolver, data)) {
                    pushSchoolDetailsToDb(resolver, data);
                } else {
                    // display toast informing user that school exists
                }
                break;

            case NO_DETAILS:
                break;

            default:
                break;
        }
    }
}
