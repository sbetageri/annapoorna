package com.example.admin.annaporna.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Admin on 07-09-2016.
 */
public class StudentDbHelper extends SQLiteOpenHelper {
    public StudentDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(StudentContract.SchoolDetails.CREATE_TABLE);
        sqLiteDatabase.execSQL(StudentContract.StudentDetails.CREATE_TABLE);
        sqLiteDatabase.execSQL(StudentContract.HealthReport.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
