package com.example.admin.annaporna.school;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.admin.annaporna.student.StudentListFrag;

/**
 * Created by Admin on 22-09-2016.
 */

public class SchoolPagerAdapter extends FragmentStatePagerAdapter{
    private String mSchoolId;
    private Bundle args;
    private String mSchoolName;

    public SchoolPagerAdapter(FragmentManager fm, String schoolId, String schoolName) {
        super(fm);
        mSchoolId = schoolId;
        mSchoolName = schoolName;
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
        return mSchoolName;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
