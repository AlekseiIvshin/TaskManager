package com.alekseiivhsin.taskmanager.views.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alekseiivhsin.taskmanager.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created on 14/12/2015.
 */
public class TaskListViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.task_name)
    TextView mTaskName;

    public TaskListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setContent(String taskName) {
        mTaskName.setText(taskName);
    }
}

