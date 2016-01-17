package com.alekseiivhsin.taskmanager.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alekseiivhsin.taskmanager.App;
import com.alekseiivhsin.taskmanager.R;
import com.alekseiivhsin.taskmanager.models.Task;
import com.alekseiivhsin.taskmanager.views.viewholders.TaskListHeaderViewHolder;
import com.alekseiivhsin.taskmanager.views.viewholders.TaskListItemViewHolder;

import java.util.List;

/**
 * Created on 14/12/2015.
 */
public class TaskListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ASSIGNED_HEADER = 0;
    private static final int ASSIGNED_TASK = 1;

    private static final int UNASSIGNED_HEADER = 2;
    private static final int UNASSIGNED_TASK = 3;
    private static final String TAG = TaskListAdapter.class.getSimpleName();

    private List<Task> mAssignedTaskList;
    private List<Task> mUnassignedTaskList;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case (ASSIGNED_HEADER):
            case (UNASSIGNED_HEADER):
                return new TaskListHeaderViewHolder(
                        LayoutInflater
                                .from(parent.getContext()).inflate(R.layout.listitem_header, parent, false));
            case (ASSIGNED_TASK):
            case (UNASSIGNED_TASK):
                return new TaskListItemViewHolder(
                        LayoutInflater
                                .from(parent.getContext()).inflate(R.layout.listitem_task, parent, false));
            default:
                throw new IllegalArgumentException("No such view type " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case (ASSIGNED_HEADER):
                ((TaskListHeaderViewHolder) holder).setContent(App.getInstance().getResources().getString(R.string.header_tasks_assigned));
                break;
            case (UNASSIGNED_HEADER):
                ((TaskListHeaderViewHolder) holder).setContent(App.getInstance().getResources().getString(R.string.header_tasks_unassigned));
                break;
            case (ASSIGNED_TASK):
                ((TaskListItemViewHolder) holder).setContent(mAssignedTaskList.get(position - 1).name);
                break;
            case (UNASSIGNED_TASK):
                ((TaskListItemViewHolder) holder).setContent(mUnassignedTaskList.get(position - 1 - getAssignedTaskListPartSize()).name);
                break;
            default:
                throw new IllegalArgumentException("No such view type " + viewType);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isAssignedTaskHeader(position)) {
            return ASSIGNED_HEADER;
        }
        if (isUnassignedTaskHeader(position)) {
            return UNASSIGNED_HEADER;
        }

        if (isAssignedTaskItem(position)) {
            return ASSIGNED_TASK;
        }

        if (isUnassignedTaskItem(position)) {
            return UNASSIGNED_TASK;
        }

        throw new IllegalArgumentException("No such type for position " + position);
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (mAssignedTaskList != null && !mAssignedTaskList.isEmpty()) {
            count += (1 + mAssignedTaskList.size());
        }
        if (mUnassignedTaskList != null && !mUnassignedTaskList.isEmpty()) {
            count += (1 + mUnassignedTaskList.size());
        }
        return count;
    }

    private boolean isAssignedTaskHeader(int pos) {
        return pos == 0 && !isAssignedTaskListEmpty();
    }

    private boolean isAssignedTaskItem(int pos) {
        if (isAssignedTaskListEmpty()) {
            return false;
        }
        return 0 < pos && pos < getAssignedTaskListPartSize();
    }

    private boolean isUnassignedTaskHeader(int pos) {
        return pos == (getAssignedTaskListPartSize());
    }

    private boolean isUnassignedTaskItem(int pos) {
        if (isUnassignedTaskListEmpty()) {
            return false;
        }
        return getUnassignedTaskListPartSize() < pos && pos < getAssignedTaskListPartSize() + getUnassignedTaskListPartSize();
    }

    private int getAssignedTaskListPartSize() {
        if (isAssignedTaskListEmpty()) {
            return 0;
        }
        return 1 + mAssignedTaskList.size();
    }

    private int getUnassignedTaskListPartSize() {
        if (isUnassignedTaskListEmpty()) {
            return 0;
        }
        return 1 + mUnassignedTaskList.size();
    }

    private boolean isAssignedTaskListEmpty() {
        return mAssignedTaskList == null || mAssignedTaskList.isEmpty();
    }

    private boolean isUnassignedTaskListEmpty() {
        return mUnassignedTaskList == null || mUnassignedTaskList.isEmpty();
    }

    public void setTaskList(List<Task> assignedTasks, List<Task> unassignedTasks) {
        mAssignedTaskList = assignedTasks;
        mUnassignedTaskList = unassignedTasks;
        Log.v(TAG, "Assigned part size (1 + assigned tasks count) = " + getAssignedTaskListPartSize());
        Log.v(TAG, "Unassigned part size (1 + unassigned tasks count) = " + getUnassignedTaskListPartSize());
        notifyDataSetChanged();
    }
}