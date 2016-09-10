package com.example.admin.annaporna.model;

import android.provider.BaseColumns;

/**
 * Created by Admin on 06-09-2016.
 */
public abstract class StudentContract {

    public static final String AUTHORITY = "com.example.sai.annaporna.provider";

    public class SchoolDetails implements BaseColumns{
        public static final String TABLE_NAME = "school_details";

        public static final String PATH = "school";

        public static final String _ID = "_id";

        public static final String NAME = "name";

        // type of school is either
        // Anganwadi, Pre-school, primary, upper primary, higher secondary, other
        public static final String TYPE = "type";

        public static final String SCHOOL_ANNAPORNA_CODE = "school_id_code";

        public static final String LOCATION_NAME = "location_name";

        public static final String NUM_BOYS = "number_boys";

        public static final String NUM_GIRLS = "number_girls";

        public static final String TOTAL_STUDENT_COUNT = "total_student_count";

        public static final String STAFF_COUNT = "staff_count";

        public static final String COOK_COUNT = "cook_count";

        public static final String HEADMASTER_NAME = "headmaster_name";

        public static final String HEADMASTER_PHONE_NUMBER = "headmaster_phone_number";

        public static final String CREATE_TABLE = "create table " + TABLE_NAME + "( " +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " TEXT, " +
                TYPE + " TEXT, " +
                LOCATION_NAME + " TEXT," +
                SCHOOL_ANNAPORNA_CODE + " TEXT," +
                NUM_BOYS + " INTEGER, " +
                NUM_GIRLS + " INTEGER, " +
                TOTAL_STUDENT_COUNT + " INTEGER, " +
                STAFF_COUNT + " INTEGER, " +
                COOK_COUNT + " INTEGER, " +
                HEADMASTER_NAME + " TEXT, " +
                HEADMASTER_PHONE_NUMBER + " TEXT);";

        public static final String DROP_TABLE = "drop table " + TABLE_NAME;
    }

    public class StudentDetails implements BaseColumns {

        public static final String TABLE_NAME = "student_detail_table";

        public static final String PATH = "student";

        public static final String _ID = "_id";

        // full name of the child
        public static final String NAME = "name";

        public static final String GENDER = "gender";

        // dob is YYYY-MM-DD
        public static final String DATE_OF_BIRTH = "date_of_birth";

        // father or guardian name
        public static final String FATHER_OR_GUARDIAN_NAME = "father_or_guardian_name";

        public static final String MOTHER_NAME = "mother_name";

        public static final String ADDRESS = "address";

        public static final String SCHOOL_ID = "school_id";

        public static final String PHOTO_PATH = "photo_path";

        public static final String CREATE_TABLE = "create table " + TABLE_NAME + "( " +
                _ID + " INTEGER AUTOINCREMENT PRIMARY KEY " +
                NAME + " TEXT, " +
                GENDER + " TEXT, " +
                DATE_OF_BIRTH + " TEXT, " +
                FATHER_OR_GUARDIAN_NAME + " TEXT, " +
                MOTHER_NAME + " TEXT, " +
                ADDRESS + " TEXT, " +
                PHOTO_PATH + " TEXT, " +
                SCHOOL_ID + " INTEGER, " +
                "FOREIGN KEY( " + SCHOOL_ID + " ) REFERENCES" + SchoolDetails.TABLE_NAME
                +" (" + SchoolDetails._ID +"));";

        public static final String DROP_TABLE = "drop table " + TABLE_NAME;
    }

    public class HealthReport implements BaseColumns {
        // Health Details are linked to each student

        public static final String TABLE_NAME = "health_report_table";

        public static final String PATH = "health";

        public static final String _ID = "_id";

        // has boolean values of 1 or 0
        public static final String ASTHMA = "asthma";

        // has boolean values of 1 or 0
        public static final String ECZEMA = "eczema";

        // has boolean values of 1 or 0
        public static final String ALLERGIES = "allergies";

        // has boolean values of 1 or 0
        public static final String SLURRED_SPEECH = "slurred_speech";

        // date format is YYYY-MM-DD
        public static final String DATE_OF_EXAMINATION = "date_of_examination";

        public static final String RIGHT_EYE = "right_eye";

        public static final String LEFT_EYE = "left_eye";

        public static final String STUDENT_ID = "student_id";

        public static final String CREATE_TABLE = "create table " + TABLE_NAME + "( " +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DATE_OF_EXAMINATION + " TEXT, " +
                ASTHMA + " INTEGER, " +
                ECZEMA + " INTEGER, " +
                ALLERGIES + " INTEGER, " +
                SLURRED_SPEECH + " INTEGER, " +
                RIGHT_EYE + " TEXT, " +
                LEFT_EYE + " TEXT, " +
                STUDENT_ID + " INTEGER, " +
                "FOREIGN KEY (" + STUDENT_ID + ") REFERENCES " + StudentDetails.TABLE_NAME +
                " ( " + StudentDetails._ID + "));";

        public static final String DROP_TABLE = "drop table " + TABLE_NAME;
    }

    public class ExtraInformation implements BaseColumns {
        // linked with HealthReport

        public static final String TABLE_NAME = "extra_information_table";

        public static final String PATH = "extra";

        public static final String _ID = "_id";

        public static final String FAMILY_HISTORY = "family_history";

        public static final String OTHER_INFORMATION = "other_information";

        public static final String HEALTH_REPORT_ID = "health_report_id";

        public static final String CREATE_TABLE = "create table " + TABLE_NAME + " ( " +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FAMILY_HISTORY + " TEXT, " +
                OTHER_INFORMATION + " TEXT, " +
                HEALTH_REPORT_ID + " INTEGER, " +
                "FOREIGN KEY (" + HEALTH_REPORT_ID + ") REFERENCES " +
                HealthReport.TABLE_NAME + "(" + HealthReport._ID + "));";

        public static final String DROP_TABLE = "drop table " + TABLE_NAME;
    }
}
