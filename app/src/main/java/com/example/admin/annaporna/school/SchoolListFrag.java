package com.example.admin.annaporna.school;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.admin.annaporna.utils.ModelUtils;

/**
 * Created by Admin on 13-09-2016.
 */
public class SchoolListFrag extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private Context mContext;
    private SchoolListAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private TextView mEmptyList;
    private SchoolContentObserver mObserver;

    private static final String _TAG = "school_overview_frag";

    private static final int CURSOR_LOADER_TAG = 9;

    private class SchoolContentObserver extends ContentObserver {
        public SchoolContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            Log.e(_TAG, "change has occurred");
            getLoaderManager().restartLoader(CURSOR_LOADER_TAG, null, SchoolListFrag.this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // there has to be a better way to check the size of school records
        // a call to restartLoader?
        /*
        int numSchoolRecords = ModelUtils.getNumSchoolRecords(mContext);
        if(numSchoolRecords != mAdapter.getItemCount()) {
            getLoaderManager().restartLoader(CURSOR_LOADER_TAG, null, SchoolListFrag.this);
        }
        */
        getLoaderManager().restartLoader(CURSOR_LOADER_TAG, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_school_overview, container, false);

        mEmptyList = (TextView)v.findViewById(R.id.empty_school_list_textview);

        mObserver = new SchoolContentObserver(new Handler());

        mRecyclerView = (RecyclerView) v.findViewById(R.id.list_school_overview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));

        mAdapter = new SchoolListAdapter(getContext());

        getLoaderManager().initLoader(CURSOR_LOADER_TAG, null, this);

        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab_add_school);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // verify text
                Intent intent = new Intent(mContext, SchoolDetailsInputActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = new CursorLoader(mContext,
                ModelUtils.getAllSchoolUri(),
                null,
                null,
                null,
                null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data == null || data.getCount() == 0) {
            mRecyclerView.setVisibility(View.GONE);
            mEmptyList.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            mEmptyList.setVisibility(View.GONE);
        }
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
