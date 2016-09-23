package com.example.admin.annaporna.school;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.admin.annaporna.R;
import com.example.admin.annaporna.student.StudentListFrag;

/**
 * Created by Admin on 22-09-2016.
 */

public class SchoolPagerAdapter extends FragmentStatePagerAdapter{
    private String mSchoolId;
    private Bundle args;
    private Context mContext;

    public SchoolPagerAdapter(Context context, FragmentManager fm, String schoolId) {
        super(fm);
        mContext = context;
        mSchoolId = schoolId;
        args = new Bundle();
        args.putString(School.SCHOOL_ID, mSchoolId);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if(position == 0) {
            fragment = new SchoolDetailsFrag();
        } else {
            fragment = new StudentListFrag();
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0) {
            return mContext.getString(R.string.school_details);
        } else {
            return mContext.getString(R.string.school_students);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
