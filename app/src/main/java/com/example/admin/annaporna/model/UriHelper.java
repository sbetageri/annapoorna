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
}
