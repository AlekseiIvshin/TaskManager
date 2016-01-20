package com.alekseiivhsin.taskmanager.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alekseiivhsin.taskmanager.R;
import com.alekseiivhsin.taskmanager.models.Task;
import com.alekseiivhsin.taskmanager.views.viewholders.TaskListItemViewHolder;

import java.util.List;

/**
 * Created on 14/12/2015.
 */
public class UserTaskListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Task> mTaskList;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new TaskListItemViewHolder(
                LayoutInflater
                        .from(parent.getContext()).inflate(R.layout.listitem_task, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((TaskListItemViewHolder) holder).setContent(mTaskList.get(position).name);

    }

    @Override
    public int getItemCount() {
        if (mTaskList != null) {
            return mTaskList.size();
        }
        return 0;
    }

    public void setTaskList(List<Task> tasksList) {
        mTaskList = tasksList;
        notifyDataSetChanged();
    }
}