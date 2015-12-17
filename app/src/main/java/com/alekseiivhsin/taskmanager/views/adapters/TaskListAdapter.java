package com.alekseiivhsin.taskmanager.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alekseiivhsin.taskmanager.R;
import com.alekseiivhsin.taskmanager.models.Task;
import com.alekseiivhsin.taskmanager.views.viewholders.TaskListItemViewHolder;

import java.util.List;

/**
 * Created on 14/12/2015.
 */
public class TaskListAdapter extends RecyclerView.Adapter<TaskListItemViewHolder> {

    private List<Task> mTaskList;

    @Override
    public TaskListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_task, parent, false);
        return new TaskListItemViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(TaskListItemViewHolder holder, int position) {
        holder.setContent(mTaskList.get(position).name);
    }

    @Override
    public int getItemCount() {
        if (mTaskList == null) {
            return 0;
        }
        return mTaskList.size();
    }

    public void setTaskList(List<Task> taskList) {
        mTaskList = taskList;
    }
}