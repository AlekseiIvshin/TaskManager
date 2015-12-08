package com.alekseiivhsin.taskmanager.authentication;

import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import com.alekseiivhsin.taskmanager.App;
import com.alekseiivhsin.taskmanager.model.LoginResult;
import com.alekseiivhsin.taskmanager.network.AuthService;

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
    AuthService mAuthService;

    public static Bundle buildRequestBundle(String username, String password, String accountType) {
        final Bundle res = new Bundle();
        res.putString(AccountManager.KEY_ACCOUNT_NAME, username);
        res.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
        res.putString(EXTRA_PASSWORD, password);
        return res;
    }

    public AuthTokenLoader(Context context, Bundle args) {
        super(context);
        mUserName = args.getString(AccountManager.KEY_ACCOUNT_NAME);
        mPassword = args.getString(EXTRA_PASSWORD);
        mAccountType = args.getString(AccountManager.KEY_ACCOUNT_TYPE);
        ((App)getContext().getApplicationContext()).getObjectGraph().inject(this);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Intent loadInBackground() {
        LoginResult loginResult = mAuthService.login(mUserName, mPassword, mAccountType);

        final Intent res = new Intent();
        res.putExtra(AccountManager.KEY_ACCOUNT_NAME, mUserName);
        res.putExtra(AccountManager.KEY_ACCOUNT_TYPE, mAccountType);
        res.putExtra(AccountManager.KEY_AUTHTOKEN, loginResult.authToken);
        res.putExtra(AuthHelper.USER_TYPE, loginResult.userType);
        res.putExtra(EXTRA_PASSWORD, mPassword);
        return res;
    }
}
