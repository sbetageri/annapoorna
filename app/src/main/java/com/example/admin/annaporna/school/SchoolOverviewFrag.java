package com.example.admin.annaporna.school;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.annaporna.R;
import com.example.admin.annaporna.utils.ModelUtils;

/**
 * Created by Admin on 13-09-2016.
 */
public class SchoolOverviewFrag extends Fragment {
    private Context mContext;

    public Cursor getSchoolOverviewCursor() {
        ContentResolver resolver = mContext.getContentResolver();

        Uri schoolOverviewUri = ModelUtils.getAllSchoolOverview();

        Cursor cursor = resolver.query(schoolOverviewUri,
                null,
                null,
                null,
                null);
        return cursor;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_school_overview, container, false);

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.list_school_overview);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));

        SchoolOverviewAdapter adapter = new SchoolOverviewAdapter(getSchoolOverviewCursor());

        recyclerView.setAdapter(adapter);

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
}
