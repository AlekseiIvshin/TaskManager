package com.alekseiivhsin.taskmanager.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alekseiivhsin.taskmanager.App;
import com.alekseiivhsin.taskmanager.R;
import com.alekseiivhsin.taskmanager.authentication.AuthHelper;
import com.alekseiivhsin.taskmanager.network.requests.PoolMemberListRequest;
import com.alekseiivhsin.taskmanager.network.responses.PoolMemberListResponse;
import com.alekseiivhsin.taskmanager.views.adapters.PoolMemberListAdapter;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.Collections;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PoolMembersListFragment extends SpicedFragment {

    private static final String TAG = PoolMembersListFragment.class.getSimpleName();

    @Bind(R.id.list_pool)
    RecyclerView mPoolMembersList;

    PoolMemberListAdapter mPoolMemberListAdapter;

    @Inject
    AuthHelper mAuthHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getObjectGraphInstance().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pool_members_list, container, false);
        ButterKnife.bind(this, rootView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        mPoolMemberListAdapter = new PoolMemberListAdapter();
        mPoolMembersList.setLayoutManager(layoutManager);
        mPoolMembersList.setAdapter(mPoolMemberListAdapter);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        PoolMemberListRequest request = new PoolMemberListRequest(mAuthHelper.getAuthToken());
        spiceManager.execute(request, new RequestListener<PoolMemberListResponse>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
                Log.v(TAG, "Error while load tasks ", spiceException);
                mPoolMemberListAdapter.setList(Collections.EMPTY_LIST);
            }

            @Override
            public void onRequestSuccess(PoolMemberListResponse poolMemberListResponse) {
                mPoolMemberListAdapter.setList(poolMemberListResponse.poolMemberList);
            }
        });

    }

}
