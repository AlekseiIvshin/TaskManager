package com.alekseiivhsin.taskmanager.views.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alekseiivhsin.taskmanager.R;
import com.alekseiivhsin.taskmanager.models.PoolMember;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created on 14/12/2015.
 */
public class PoolMemberListItemViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.name)
    TextView mName;

    public PoolMemberListItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setContent(PoolMember poolMember) {
        mName.setText(String.format("%s %s", poolMember.firstName, poolMember.lastName));
    }
}

