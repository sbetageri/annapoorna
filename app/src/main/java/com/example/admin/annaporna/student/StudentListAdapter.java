package com.example.admin.annaporna.student;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.annaporna.R;
import com.example.admin.annaporna.Utils;
import com.example.admin.annaporna.model.StudentContract;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admin on 19-09-2016.
 */
public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.StudentViewHolder> {
    private static final String _TAG = "student_list_adapter";
    private Cursor mCursor;
    private Context mContext;

    public StudentListAdapter(Context context) {
        mContext = context;
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.overview_student_name)
        public TextView mName;

        @BindView(R.id.overview_student_age)
        public TextView mAge;

        @BindView(R.id.overview_student_gender)
        public TextView mGender;

        public StudentViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public void setStudentData(Cursor cursor) {
            mName.setText(cursor.getString(cursor.getColumnIndex(StudentContract.StudentDetails.NAME)));
            mGender.setText(cursor.getString(cursor.getColumnIndex(StudentContract.StudentDetails.GENDER)));
            String[] dob = cursor.getString(cursor.getColumnIndex(StudentContract.StudentDetails.DATE_OF_BIRTH)).split("/");
            String age = Integer.toString(Utils.getAge(dob));
            mAge.setText(age);
        }
    }

    private void logCursorContent() {
        for(int i = 0; i < mCursor.getCount(); i++) {
            mCursor.moveToPosition(i);
            String[] cols = mCursor.getColumnNames();
            Log.e(_TAG, cols[i] + " : " + mCursor.getString(mCursor.getColumnIndex(cols[i])));
        }
    }

    public void swapCursor(Cursor cursor) {
        mCursor = cursor;
        if(mCursor != null) {
            mCursor.moveToFirst();
            logCursorContent();
            mCursor.moveToFirst();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mCursor != null) {
            return mCursor.getCount();
        }
        return 0;
    }

    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_overview_element, parent, false);
        StudentViewHolder viewHolder = new StudentViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StudentViewHolder holder, int position) {
        if(mCursor != null && mCursor.getCount() > 0) {
            mCursor.moveToPosition(position);
            holder.setStudentData(mCursor);
        }
    }
}
