package com.example.admin.annaporna.student;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.annaporna.R;
import com.example.admin.annaporna.SchoolDetailsActivity;
import com.example.admin.annaporna.model.UriHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admin on 16-09-2016.
 */
public class StudentListFrag extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final String _TAG = "student_list_frag";
    private static final int STUDENT_CURSOR_TAG = 108;
    private String mSchoolId;
    private StudentListAdapter mAdapter;

    @BindView(R.id.recycler_student_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.empty_student_overview_list)
    TextView mEmptyList;

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(STUDENT_CURSOR_TAG, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_student_list, container, false);

        ButterKnife.bind(this, v);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getLoaderManager().initLoader(STUDENT_CURSOR_TAG, null, this);

        FloatingActionButton fab = (FloatingActionButton)v.findViewById(R.id.fab_add_student);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), StudentDetailInputActivity.class);
                intent.putExtra(SchoolDetailsActivity.SCHOOL_ID, mSchoolId);
                startActivity(intent);
            }
        });
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        mSchoolId = args.getString(SchoolDetailsActivity.SCHOOL_ID);
        mAdapter = new StudentListAdapter();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = UriHelper.getAllStudentsFromSchool(mSchoolId);
        Log.e(_TAG, uri.toString());
        return new CursorLoader(getContext(), uri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data != null && data.getCount() > 0) {
            mEmptyList.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mEmptyList.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
