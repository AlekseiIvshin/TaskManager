package com.alekseiivhsin.taskmanager.functional.fragments;


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
import com.alekseiivhsin.taskmanager.network.requests.PoolMembersRequest;
import com.alekseiivhsin.taskmanager.network.responses.PoolMembersResponse;
import com.alekseiivhsin.taskmanager.views.adapters.PoolMembersAdapter;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.Collections;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PoolMembersFragment extends SpicedFragment {

    private static final String TAG = PoolMembersFragment.class.getSimpleName();

    @Bind(R.id.list_pool)
    RecyclerView mPoolMembersList;

    PoolMembersAdapter mPoolMembersAdapter;

    @Inject
    AuthHelper mAuthHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((App) getActivity().getApplication()).getObjectGraph().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pool_members_list, container, false);
        ButterKnife.bind(this, rootView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        mPoolMembersAdapter = new PoolMembersAdapter();
        mPoolMembersList.setLayoutManager(layoutManager);
        mPoolMembersList.setAdapter(mPoolMembersAdapter);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        PoolMembersRequest request = new PoolMembersRequest(mAuthHelper.getAuthToken());
        spiceManager.execute(request, new RequestListener<PoolMembersResponse>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
                Log.v(TAG, "Error while load tasks ", spiceException);
                mPoolMembersAdapter.setList(Collections.EMPTY_LIST);
            }

            @Override
            public void onRequestSuccess(PoolMembersResponse poolMembersResponse) {
                mPoolMembersAdapter.setList(poolMembersResponse.poolMemberList);
            }
        });

    }

}
