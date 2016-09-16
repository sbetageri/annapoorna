package com.example.admin.annaporna.model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.admin.annaporna.R;
import com.example.admin.annaporna.model.StudentContract;

/**
 * Created by Admin on 13-09-2016.
 */
public class UriHelper {
    public static Uri getAllSchoolUri() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(ContentResolver.SCHEME_CONTENT);
        builder.authority(StudentContract.AUTHORITY);
        builder.appendPath(StudentContract.SchoolDetails.PATH);
        return builder.build();
    }

    public static Uri getSpecificSchoolUri(String schoolId) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(ContentResolver.SCHEME_CONTENT);
        builder.authority(StudentContract.AUTHORITY);
        builder.appendPath(StudentContract.SchoolDetails.PATH);
        builder.appendPath(schoolId);
        return builder.build();
    }

    public static Uri doesStudentExistUri(String name, String dob, String fatherGuardianName) {
        Uri.Builder builder = buildBaseUri().buildUpon();
        builder.appendPath(StudentContract.StudentDetails.PATH);
        builder.appendPath(StudentContract.EXISTS);
        builder.appendQueryParameter(StudentProvider.QUERY_STUDENT_NAME, name);
        builder.appendQueryParameter(StudentProvider.QUERY_STUDENT_DOB, dob);
        builder.appendQueryParameter(StudentProvider.QUERY_STUDENT_FATHER_GUARDIAN_NAME, fatherGuardianName);
        return builder.build();
    }

    public static Uri insertSchoolUri() {
        Uri.Builder builder = buildBaseUri().buildUpon();
        builder.appendPath(StudentContract.SchoolDetails.PATH);
        return builder.build();
    }

    public static Uri insertStudentUri() {
        Uri.Builder builder = buildBaseUri().buildUpon();
        builder.appendPath(StudentContract.StudentDetails.PATH);
        return builder.build();
    }

    public static Uri buildBaseUri() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(ContentResolver.SCHEME_CONTENT);
        builder.authority(StudentContract.AUTHORITY);
        return builder.build();
    }

    public static Uri doesSchoolExistUri(String name, String location) {
        Uri.Builder builder = buildBaseUri().buildUpon();
        builder.appendPath(StudentContract.StudentDetails.PATH);
        builder.appendPath(StudentContract.EXISTS);
        builder.appendQueryParameter(StudentProvider.QUERY_SCHOOL_NAME, name);
        builder.appendQueryParameter(StudentProvider.QUERY_SCHOOL_LOCATION, location);
        return builder.build();
    }
}
