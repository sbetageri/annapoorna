package com.example.admin.annaporna.model;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Admin on 08-09-2016.
 */
public class StudentProvider extends ContentProvider {
    private static final String _TAG = "student_provider";

    public static final String DATABASE_NAME = "annaporna_school_details";

    public static final int DATABASE_VERSION = 1;

    public static final String QUERY_SCHOOL_NAME = "school_name_query";

    public static final String QUERY_SCHOOL_LOCATION = "school_location_query";

    public static final String QUERY_STUDENT_NAME = "student_name";

    public static final String QUERY_STUDENT_DOB = "student_dob";

    public static final String QUERY_STUDENT_FATHER_GUARDIAN_NAME = "father_guardian_name";

    public static final String QUERY_STUDENTS_FROM_SCHOOL = "students_from_school";

    private static final String AUTHORITY = StudentContract.AUTHORITY;

    private static final String SCHOOL = StudentContract.SchoolDetails.PATH;

    private static final String STUDENT = StudentContract.StudentDetails.PATH;

    private static final String HEALTH = StudentContract.HealthReport.PATH;

    private static final String EXTRA = StudentContract.ExtraInformation.PATH;

    private static final String EXISTS = StudentContract.EXISTS;

    private static UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // query :
    // <auth>/school/exists?<name=? & location=?>
    private static final int SCHOOL_EXISTS = 90;

    // query :
    // <auth>/school/exists?<name=? & dob=? and father_guardian_name=?>
    private static final int STUDENT_EXISTS = 91;

    // insert : <auth>/school
    // query : gets all schools
    // query : <auth>/school
    private static final int SCHOOL_CODE = 101;

    // code for a specific school
    // <auth>/school/{id}
    private static final int SCHOOL_SPECIFIC_CODE = 102;

    // code to get all student details from a school
    // <auth>/school/student/{id}
    private static final int SCHOOL_ALL_STUDENTS_CODE = 103;

    // query : get all students
    // <auth>/student
    // insert : <auth>/student
    private static final int STUDENT_CODE = 104;

    // code to get a specific student details
    private static final int STUDENT_SPECIFIC_CODE = 105;

    // code to get all health reports for a student
    private static final int STUDENT_HEALTH_CODE = 106;

    // insert : <auth>/health
    private static final int HEALTH_CODE = 107;

    // get the specific health report
    private static final int HEALTH_SPECIFIC_CODE = 108;

    // code for extra information related to a health report
    private static final int EXTRA_CODE = 109;

    private static void buildUriMatcher() {
        sUriMatcher.addURI(AUTHORITY, SCHOOL, SCHOOL_CODE);

        sUriMatcher.addURI(AUTHORITY, SCHOOL + "/" + EXISTS, SCHOOL_EXISTS);

        sUriMatcher.addURI(AUTHORITY, SCHOOL + "/#", SCHOOL_SPECIFIC_CODE);
        sUriMatcher.addURI(AUTHORITY, SCHOOL + "/" + STUDENT, SCHOOL_ALL_STUDENTS_CODE);

        sUriMatcher.addURI(AUTHORITY, STUDENT, STUDENT_CODE);
        sUriMatcher.addURI(AUTHORITY, STUDENT + "/" + EXISTS, STUDENT_EXISTS);
        sUriMatcher.addURI(AUTHORITY, STUDENT + "/#", STUDENT_SPECIFIC_CODE);
        sUriMatcher.addURI(AUTHORITY, STUDENT + "/" + HEALTH, STUDENT_HEALTH_CODE);

        sUriMatcher.addURI(AUTHORITY, HEALTH, HEALTH_CODE);
        sUriMatcher.addURI(AUTHORITY, HEALTH + "/#", HEALTH_SPECIFIC_CODE);

        sUriMatcher.addURI(AUTHORITY, EXTRA, EXTRA_CODE);
    }

    static {
        buildUriMatcher();
    }

    private Context mContext;
    private StudentDbHelper mDbHelper;

    private int deleteRecord(SQLiteDatabase db,
                             String tableName,
                             String tableIdColName,
                             String tableId) {
        if(tableName == null ||
                tableIdColName == null ||
                tableId == null) {
            return 0;
        } else {
            String selection = tableIdColName + " == ?";
            String[] selectionArgs = {tableId};
            int rowsDeleted = db.delete(tableName,
                    selection,
                    selectionArgs);
            return rowsDeleted;
        }
    }

    private Cursor getStudentHealthReports(SQLiteDatabase db,
                                           String studentId) {
        Cursor cursor = null;
        String[] projection = {
                StudentContract.HealthReport.DATE_OF_EXAMINATION
        };
        String selection = StudentContract.HealthReport.STUDENT_ID + " == ? ";
        String[] selectionArgs = { studentId };
        cursor = db.query(StudentContract.HealthReport.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null);
        return cursor;
    }

    private Cursor getStudentDetails(SQLiteDatabase db,
                                     String studentId) {
        Cursor cursor = null;
        String selection = StudentContract.StudentDetails._ID + " == ? ";
        String[] selectionArgs = { studentId };
        cursor = db.query(StudentContract.StudentDetails.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null);
        return cursor;
    }

    private Cursor getHealthDetails(SQLiteDatabase db,
                                    String healthId) {
        Cursor cursor;
        String selection = StudentContract.HealthReport._ID + " == ? ";
        String[] selectionArgs = { healthId };
        cursor = db.query(StudentContract.HealthReport.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null);
        return cursor;
    }

    private Cursor getAllHealthReports(SQLiteDatabase db) {
        Cursor cursor;
        String[] projection = {StudentContract.HealthReport.DATE_OF_EXAMINATION};
        cursor = db.query(StudentContract.HealthReport.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
        return cursor;
    }

    private Cursor getAllStudentOverview(SQLiteDatabase db) {
        /*

        select stdnt.name, schl.location, stdnt.dob, stdnt.gender
        from student stdnt, school schl
        where stdnt.school_id = schl._id

         */
        Cursor cursor = null;
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(StudentContract.SchoolDetails.TABLE_NAME + " INNER JOIN " +
                StudentContract.StudentDetails.TABLE_NAME + " ON " +
                StudentContract.StudentDetails.TABLE_NAME + "." +
                StudentContract.StudentDetails.SCHOOL_ID +
                StudentContract.SchoolDetails.TABLE_NAME + "." +
                StudentContract.SchoolDetails._ID
        );
        String[] projection = {
                StudentContract.StudentDetails._ID,
                StudentContract.StudentDetails.NAME,
                StudentContract.StudentDetails.GENDER,
                StudentContract.StudentDetails.DATE_OF_BIRTH
        };
        cursor = db.query(StudentContract.StudentDetails.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
        return cursor;
    }

    private Cursor getSchoolDetails(SQLiteDatabase db,
                                    String schoolId) {
        Log.e(_TAG, "School id : " + schoolId);
        Cursor cursor = null;
        String selection = StudentContract.SchoolDetails._ID + " == ? ";
        String[] selectionArgs = {schoolId};
        cursor = db.query(StudentContract.SchoolDetails.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null);
        Log.e(_TAG, "cursor size : " + Integer.toString(cursor.getCount()));
        return cursor;
    }

    private Cursor getAllStudentFromSchool(SQLiteDatabase db, String schoolId) {
        Cursor cursor = null;
        // age has to be calculated dynamically
        String[] projection = {
                StudentContract.StudentDetails._ID,
                StudentContract.StudentDetails.NAME,
                StudentContract.StudentDetails.DATE_OF_BIRTH,
                StudentContract.StudentDetails.GENDER
        };
        String selection = StudentContract.StudentDetails.SCHOOL_ID + " == ? ";
        String[] selectinArgs = { schoolId };
        cursor = db.query(StudentContract.StudentDetails.TABLE_NAME,
                projection,
                selection,
                selectinArgs,
                null,
                null,
                null);
        Log.e(_TAG, "all students from school : " + schoolId + " count : " + Integer.toString(cursor.getCount()));
        return cursor;
    }

    private Cursor getAllSchoolOverview(SQLiteDatabase db) {
        Cursor cursor;
        String[] projection = {
                StudentContract.SchoolDetails._ID,
                StudentContract.SchoolDetails.SCHOOL_NAME,
                StudentContract.SchoolDetails.SCHOOL_TYPE,
                StudentContract.SchoolDetails.SCHOOL_ANNAPORNA_CODE,
                StudentContract.SchoolDetails.TOTAL_STUDENT_COUNT
        };
        cursor = db.query(StudentContract.SchoolDetails.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
        return cursor;
    }

    @Override
    public int delete(Uri uri,
                      String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String tableName = null;
        String tableIdColName = null;
        String tableId = null;
        switch (sUriMatcher.match(uri)) {
            case HEALTH_CODE:
                tableName = StudentContract.HealthReport.TABLE_NAME;
                tableIdColName = StudentContract.HealthReport._ID;
                tableId = uri.getLastPathSegment();
                break;

            case STUDENT_CODE:
                tableName = StudentContract.StudentDetails.TABLE_NAME;
                tableIdColName = StudentContract.StudentDetails._ID;
                tableId = uri.getLastPathSegment();
                break;

            case SCHOOL_CODE:
                tableName = StudentContract.SchoolDetails.TABLE_NAME;
                tableIdColName = StudentContract.SchoolDetails._ID;
                tableId = uri.getLastPathSegment();
                break;

            default:
                break;
        }
        return deleteRecord(db, tableName, tableIdColName, tableId);
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

    private Cursor getSchool(SQLiteDatabase db,
                             String name,
                             String location) {
        Cursor cursor;
        String selection = StudentContract.SchoolDetails.SCHOOL_NAME + " == ? and " +
                StudentContract.SchoolDetails.LOCATION_NAME + " == ? ";
        String[] selectionArgs = {name, location};
        cursor = db.query(StudentContract.SchoolDetails.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null);
        return cursor;
    }

    private Cursor getStudent(SQLiteDatabase db,
                              String name,
                              String dob,
                              String fatherGuardianName) {
        String selection = StudentContract.StudentDetails.NAME + " == ? and " +
                StudentContract.StudentDetails.DATE_OF_BIRTH + " == ? and " +
                StudentContract.StudentDetails.FATHER_OR_GUARDIAN_NAME + " == ? ";
        String[] selectionArgs = {
                name,
                dob,
                fatherGuardianName
        };
        Cursor cursor = db.query(StudentContract.StudentDetails.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null);
        return cursor;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Log.e(_TAG, "query : " + uri.toString());
        Cursor cursor = null;
        String schoolId = null;
        String studentId = null;
        String healthID = null;
        switch(sUriMatcher.match(uri)) {

            case SCHOOL_CODE:
                Log.e(_TAG, "school_code");
                cursor = getAllSchoolOverview(db);
                break;

            case SCHOOL_EXISTS:
                Log.e(_TAG, "school_exists");
                String name = uri.getQueryParameter(QUERY_SCHOOL_NAME);
                String location = uri.getQueryParameter(QUERY_SCHOOL_LOCATION);
                cursor = getSchool(db, name, location);
                break;

            case SCHOOL_SPECIFIC_CODE:
                Log.e(_TAG, "specific school");
                schoolId = uri.getLastPathSegment();
                cursor = getSchoolDetails(db, schoolId);
                break;

            case SCHOOL_ALL_STUDENTS_CODE:
                Log.e(_TAG, "school_all_students_code");
                schoolId = uri.getQueryParameter(QUERY_STUDENTS_FROM_SCHOOL);
                Log.e(_TAG, "school id  : " + schoolId);
                Log.e(_TAG, "all students from school : " + schoolId);
                cursor = getAllStudentFromSchool(db, schoolId);
                break;

            case STUDENT_CODE:
                Log.e(_TAG, "student_code");
                cursor = getAllStudentOverview(db);
                break;

            case STUDENT_EXISTS:
                Log.e(_TAG, "student_exists");
                String studentName = uri.getQueryParameter(QUERY_STUDENT_NAME);
                String dob = uri.getQueryParameter(QUERY_STUDENT_DOB);
                String fatherGuardianName = uri.getQueryParameter(QUERY_STUDENT_FATHER_GUARDIAN_NAME);
                Log.e(_TAG, "checking if student " + studentName + " exists");
                cursor = getStudent(db, studentName, dob, fatherGuardianName);
                break;

            case STUDENT_SPECIFIC_CODE:
                Log.e(_TAG, "student_specific_code");
                studentId = uri.getLastPathSegment();
                cursor = getStudentDetails(db, studentId);
                break;

            case STUDENT_HEALTH_CODE:
                Log.e(_TAG, "student_health_code");
                studentId = uri.getLastPathSegment();
                cursor = getStudentHealthReports(db, studentId);
                break;

            case HEALTH_CODE:
                Log.e(_TAG, "health_code");
                cursor = getAllHealthReports(db);
                break;

            case HEALTH_SPECIFIC_CODE:
                Log.e(_TAG, "health_specific_code");
                healthID = uri.getLastPathSegment();
                cursor = getHealthDetails(db, healthID);
                break;

            default:
                Log.e(_TAG, "default");
                cursor = null;
        }
        return cursor;
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
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long rowId = -1;
        switch (sUriMatcher.match(uri)) {
            case SCHOOL_CODE:
                rowId = db.insert(StudentContract.SchoolDetails.TABLE_NAME,
                        null,
                        contentValues);
                break;
            case STUDENT_CODE:
                rowId = db.insert(StudentContract.StudentDetails.TABLE_NAME,
                        null,
                        contentValues);
                Log.e(_TAG, "inserted row : " + Long.toString(rowId));
                break;

            case HEALTH_CODE:
                rowId = db.insert(StudentContract.HealthReport.TABLE_NAME,
                        null,
                        contentValues);
                break;
        }
        Uri.Builder builder = uri.buildUpon();
        builder.appendPath(Long.toString(rowId));
        return builder.build();
    }

    @Override
    public int update(Uri uri,
                      ContentValues contentValues,
                      String selection,
                      String[] selectionArgs) {
        return 0;
    }
}
