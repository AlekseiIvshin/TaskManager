package com.alekseiivhsin.taskmanager.views.adapters;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alekseiivhsin.taskmanager.R;
import com.alekseiivhsin.taskmanager.views.viewholders.TaskListViewHolder;

/**
 * Created on 14/12/2015.
 */
public class TaskListAdapter extends RecyclerView.Adapter<TaskListViewHolder> {

    Cursor mCursor;

    public TaskListAdapter(Cursor cursor) {
        this.mCursor = cursor;
    }

    @Override
    public TaskListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_task, parent, false);
        return new TaskListViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(TaskListViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            throw new IllegalStateException(" Couldn't move cursor to position " + position);
        }
        holder.setContent(mCursor.getString(mCursor.getColumnIndex("task_name")));
    }

    @Override
    public int getItemCount() {
        if (mCursor != null) {
            return mCursor.getCount();
        }
        return 0;
    }

    public void changeCursor(Cursor newCursor) {
        Cursor oldCursor = swapCursor(newCursor);
        if (oldCursor != null) {
            oldCursor.close();
        }
    }

    private Cursor swapCursor(Cursor newCursor) {
        if (newCursor == mCursor) {
            return null;
        }

        final Cursor oldCursor = mCursor;
        mCursor = newCursor;
        notifyDataSetChanged();
        return oldCursor;
    }
}