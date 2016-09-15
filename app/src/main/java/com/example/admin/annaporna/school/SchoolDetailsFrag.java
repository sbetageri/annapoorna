package com.example.admin.annaporna.school;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.annaporna.R;
import com.example.admin.annaporna.SchoolDetailsActivity;
import com.example.admin.annaporna.model.UriHelper;

/**
 * Created by Admin on 15-09-2016.
 */
public class SchoolDetailsFrag extends Fragment {
    private static final String _TAG = "school_detail_frag";
    private School mSchool;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_school_details, container, false);
        populateView(v);
        return v;
    }

    private void setTextView(View v, int resId, String text) {
        ((TextView) v.findViewById(resId)).setText(text);
    }

    private void populateView(View v) {
        // school details
        setTextView(v, R.id.school_location, mSchool.mSchoolLocation);
        setTextView(v, R.id.school_name, mSchool.mSchoolName);
        setTextView(v, R.id.school_code, mSchool.mSchoolCode);

        // headmaster details
        setTextView(v, R.id.headmaster_name, mSchool.mHmName);
        setTextView(v, R.id.headmaster_phone_number, mSchool.mHmPhoneNumber);

        // student details
        String boys = getContext().getString(R.string.num_boys) + " : ";
        boys += Integer.toString(mSchool.mNumBoys);
        setTextView(v, R.id.boys_count, boys);

        String girls = getContext().getString(R.string.num_girls) + " : ";
        girls += Integer.toString(mSchool.mNumGirls);
        setTextView(v, R.id.girls_count, girls);

        String total = getString(R.string.tot_students) + " : ";
        total += Integer.toString(mSchool.mNumBoys + mSchool.mNumGirls);
        setTextView(v, R.id.total_students, total);

        // staff details
        String totalStaff = getString(R.string.staff) + " : ";
        totalStaff += Integer.toString(mSchool.mNumStaff + mSchool.mNumCooks);
        setTextView(v, R.id.total_staff, totalStaff);

        String teachers = getString(R.string.num_teachers) + " : ";
        teachers += Integer.toString(mSchool.mNumStaff);
        setTextView(v, R.id.teachers, teachers);

        String cooks = getString(R.string.num_cooks) + " : ";
        cooks += Integer.toString(mSchool.mNumCooks);
        setTextView(v, R.id.cooks, cooks);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        String schoolId = args.getString(SchoolDetailsActivity.SCHOOL_ID);
        mSchool = getSchoolDetails(schoolId);
    }

    public School getSchoolDetails(String schoolId) {
        Uri uri = UriHelper.getSpecificSchoolUri(schoolId);
        Log.e(_TAG, uri.toString());
        ContentResolver resolver = getContext().getContentResolver();
        Cursor cursor = resolver.query(uri,
                null,
                null,
                null,
                null);
        return new School(cursor);
    }
}
