package com.example.admin.annaporna.school;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.annaporna.R;
import com.example.admin.annaporna.model.StudentContract;

/**
 * Created by Admin on 13-09-2016.
 */
public class SchoolListAdapter extends RecyclerView.Adapter<SchoolListAdapter.ViewHolder> {
    public static final String _TAG = "school_overview_adapter";
    Cursor mCursor;
    Context mContext;

    public SchoolListAdapter(Context context) {
        mContext = context;
    }

    public void swapCursor(Cursor cursor) {
        mCursor = cursor;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mName;
        TextView mStrength;

        public ViewHolder(View v) {
            super(v);
            mName = (TextView) v.findViewById(R.id.overview_school_name);
            mStrength = (TextView) v.findViewById(R.id.overview_school_strength);
        }

    }

    public void setOverviewContent(ViewHolder vh, Cursor cursor) {
        int kidStrength = cursor.getInt(cursor.getColumnIndex(StudentContract.SchoolDetails.TOTAL_STUDENT_COUNT));
        String strength = Integer.toString(kidStrength);
        strength += " ";
        if(kidStrength == 1) {
            strength += mContext.getString(R.string.kid);
        } else {
            strength += mContext.getString(R.string.kids);
        }
        vh.mName.setText(cursor.getString(cursor.getColumnIndex(StudentContract.SchoolDetails.SCHOOL_NAME)));
        vh.mStrength.setText(strength);
        Log.e(_TAG, vh.mName.getText().toString());
        Log.e(_TAG, vh.mStrength.getText().toString());
    }

    @Override
    public int getItemCount() {
        if (isCursorValid()) {
            return mCursor.getCount();
        }
        return 0;
    }

    private boolean isCursorValid() {
        if (mCursor != null && !mCursor.isClosed()) {
            return true;
        }
        return false;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.school_overview_element, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        setOverviewContent(holder, mCursor);
    }
}
