package com.example.admin.annaporna.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

import com.example.admin.annaporna.R;
import com.example.admin.annaporna.model.StudentContract;

/**
 * Created by Admin on 13-09-2016.
 */
public class ModelUtils {
    public static Uri getAllSchoolOverview() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(ContentResolver.SCHEME_CONTENT);
        builder.authority(StudentContract.AUTHORITY);
        builder.appendPath(StudentContract.SchoolDetails.PATH);
        return builder.build();
    }
}
