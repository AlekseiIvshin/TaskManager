package com.alekseiivhsin.taskmanager.authentication;

import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Created on 25/11/2015.
 */
public class AuthTokenLoader extends AsyncTaskLoader<Intent> {
    public static final String EXTRA_PASSWORD = "com.alivshin.taskmanager.extras.EXTRA_PASSWORD";

    private final String mLogin;
    private final String mPassword;
    private final String mAccountType;

    public static Bundle buildRequestBundle(String login, String password, String accountType) {
        final Bundle res = new Bundle();
        res.putString(AccountManager.KEY_ACCOUNT_NAME, login);
        res.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
        res.putString(EXTRA_PASSWORD, password);
        return res;
    }

    public AuthTokenLoader(Context context, Bundle args) {
        super(context);
        mLogin = args.getString(AccountManager.KEY_ACCOUNT_NAME);
        mPassword = args.getString(EXTRA_PASSWORD);
        mAccountType = args.getString(AccountManager.KEY_ACCOUNT_TYPE);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Intent loadInBackground() {
        final Intent res = new Intent();
        res.putExtra(AccountManager.KEY_ACCOUNT_NAME, mLogin);
        res.putExtra(AccountManager.KEY_ACCOUNT_TYPE, mAccountType);
        res.putExtra(AccountManager.KEY_AUTHTOKEN, stubGetAuthToken(mLogin));
        res.putExtra(AuthHelper.USER_TYPE, stubUserTypeByLogin(mLogin));
        res.putExtra(EXTRA_PASSWORD, mPassword);
        return res;
    }


    public static String stubUserTypeByLogin(String login){
        if("LEAD".equalsIgnoreCase(login)){
            return UserTypes.POOL_LEAD;
        }
        return UserTypes.POOL_MEMBER;
    }

    public static String stubGetAuthToken(String login){
        return login;
    }
}
