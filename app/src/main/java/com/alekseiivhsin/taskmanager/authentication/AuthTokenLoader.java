package com.alekseiivhsin.taskmanager.authentication;

import android.accounts.AccountManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.alekseiivhsin.taskmanager.App;
import com.alekseiivhsin.taskmanager.network.requests.SignInRequestBody;
import com.alekseiivhsin.taskmanager.network.responses.SignInResponse;
import com.alekseiivhsin.taskmanager.network.services.AuthApiService;

import java.io.IOException;

import javax.inject.Inject;

/**
 * Created on 25/11/2015.
 */
public class AuthTokenLoader extends AsyncTaskLoader<Intent> {
    public static final String EXTRA_PASSWORD = "com.alivshin.taskmanager.extras.EXTRA_PASSWORD";

    private final String mUserName;
    private final String mPassword;
    private final String mAccountType;

    @Inject
    AuthApiService mAuthApiService;

    public static Bundle buildRequestBundle(String username, String password, String accountType) {
        final Bundle res = new Bundle();
        res.putString(AccountManager.KEY_ACCOUNT_NAME, username);
        res.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
        res.putString(EXTRA_PASSWORD, password);
        return res;
    }

    public AuthTokenLoader(Context context, Bundle args) {
        super(context);
        ((App)getContext().getApplicationContext()).getObjectGraph().inject(this);
        mUserName = args.getString(AccountManager.KEY_ACCOUNT_NAME);
        mPassword = args.getString(EXTRA_PASSWORD);
        mAccountType = args.getString(AccountManager.KEY_ACCOUNT_TYPE);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Intent loadInBackground() {
        SignInResponse signInResponse;
        try {
            signInResponse = mAuthApiService.signIn(new SignInRequestBody(mUserName, mPassword, mAccountType)).execute().body();
        } catch (IOException e) {
            return new Intent();
        }

        final Intent res = new Intent();
        res.putExtra(AccountManager.KEY_ACCOUNT_NAME, mUserName);
        res.putExtra(AccountManager.KEY_ACCOUNT_TYPE, mAccountType);
        res.putExtra(AccountManager.KEY_AUTHTOKEN, signInResponse.authToken);
        res.putExtra(AuthHelper.USER_RIGHTS, signInResponse.userRights);
        res.putExtra(EXTRA_PASSWORD, mPassword);
        return res;
    }
}
