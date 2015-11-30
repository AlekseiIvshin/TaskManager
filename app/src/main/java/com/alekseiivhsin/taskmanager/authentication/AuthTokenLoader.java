package com.alekseiivhsin.taskmanager.authentication;

import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import com.alekseiivhsin.taskmanager.R;

/**
 * Created on 25/11/2015.
 */
public class AuthTokenLoader extends AsyncTaskLoader<Intent> {
    public static final String EXTRA_PASSWORD = "com.alivshin.taskmanager.extras.EXTRA_PASSWORD";

    private final String mLogin;
    private final String mPassword;

    public static Bundle buildRequestBundle(String login, String password){
        final Bundle res = new Bundle();
        res.putString(AccountManager.KEY_ACCOUNT_NAME, login);
        res.putString(EXTRA_PASSWORD, password);
        return res;
    }

    public AuthTokenLoader(Context context, Bundle args) {
        super(context);
        mLogin = args.getString(AccountManager.KEY_ACCOUNT_NAME);
        mPassword = args.getString(EXTRA_PASSWORD);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Intent loadInBackground() {
        String authtoken = "MOCK";
        final Intent res = new Intent();
        res.putExtra(AccountManager.KEY_ACCOUNT_NAME, mLogin);
        res.putExtra(AccountManager.KEY_ACCOUNT_TYPE, getContext().getString(R.string.accountType));
        res.putExtra(AccountManager.KEY_AUTHTOKEN, authtoken);
        res.putExtra(EXTRA_PASSWORD, mPassword);
        return res;
    }

}
