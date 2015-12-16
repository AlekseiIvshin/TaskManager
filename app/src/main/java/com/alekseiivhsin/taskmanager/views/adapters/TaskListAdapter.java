package com.alekseiivhsin.taskmanager.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alekseiivhsin.taskmanager.R;
import com.alekseiivhsin.taskmanager.views.viewholders.TaskListItemViewHolder;

import java.util.List;

/**
 * Created on 14/12/2015.
 */
public class TaskListAdapter extends RecyclerView.Adapter<TaskListItemViewHolder> {

    List<String> tasksList;

    @Override
    public TaskListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_task, parent, false);
        return new TaskListItemViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(TaskListItemViewHolder holder, int position) {
        holder.setContent(tasksList.get(position));
    }

    @Override
    public int getItemCount() {
        if (tasksList != null) {
            return tasksList.size();
        }
        return 0;
    }
}