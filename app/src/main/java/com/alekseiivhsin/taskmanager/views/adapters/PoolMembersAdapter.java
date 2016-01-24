package com.alekseiivhsin.taskmanager.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alekseiivhsin.taskmanager.R;
import com.alekseiivhsin.taskmanager.models.PoolMember;
import com.alekseiivhsin.taskmanager.models.Task;
import com.alekseiivhsin.taskmanager.views.viewholders.PoolMemberListItemViewHolder;
import com.alekseiivhsin.taskmanager.views.viewholders.TaskListItemViewHolder;

import java.util.List;

/**
 * Created on 14/12/2015.
 */
public class PoolMembersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PoolMember> mPoolMemberList;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PoolMemberListItemViewHolder(
                LayoutInflater
                        .from(parent.getContext()).inflate(R.layout.listitem_pool_member, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((PoolMemberListItemViewHolder) holder).setContent(mPoolMemberList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mPoolMemberList != null) {
            return mPoolMemberList.size();
        }
        return 0;
    }

    public void setList(List<PoolMember> poolMemberList) {
        mPoolMemberList = poolMemberList;
        notifyDataSetChanged();
    }
}