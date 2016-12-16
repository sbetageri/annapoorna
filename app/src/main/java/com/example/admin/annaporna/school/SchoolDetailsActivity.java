package com.example.admin.annaporna.school;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;

import com.example.admin.annaporna.R;

/*
    TODO
    Create school detail fragment
    Create student overview fragment
 */

public class SchoolDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_details);

        FragmentManager fm = getSupportFragmentManager();
        Intent intent = getIntent();
        String schoolName = intent.getStringExtra(School.SCHOOL_NAME);
        String schoolId = intent.getStringExtra(School.SCHOOL_ID);
        getSupportActionBar().setTitle(schoolName);
        getSupportActionBar().setElevation(0.0f);

        FragmentStatePagerAdapter pagerAdapter = new SchoolPagerAdapter(this, fm, schoolId);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.school_details_tab_layout);

        ViewPager viewPager = (ViewPager) findViewById(R.id.school_details_viewpager);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
