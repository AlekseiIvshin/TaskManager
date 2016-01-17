package com.alekseiivhsin.taskmanager.views.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alekseiivhsin.taskmanager.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Aleksei Ivshin
 * on 17.01.2016.
 */
public class TaskListHeaderViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.header_text)
    TextView mHeaderText;

    public TaskListHeaderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setContent(String headerText) {
        mHeaderText.setText(headerText);
    }
}
