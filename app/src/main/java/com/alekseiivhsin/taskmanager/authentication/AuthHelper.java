package com.alekseiivhsin.taskmanager.authentication;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import com.alekseiivhsin.taskmanager.R;

/**
 * Created on 07/12/2015.
 */
public class AuthHelper {

    public static final String USER_RIGHTS = "taskmanager.authentication.USER_RIGHTS";

    private final AccountManager mAccountManager;

    private final String accountType;
    private final String authTokenType;


    public static AuthHelper get(Context context) {
        return new AuthHelper(context);
    }

    public AuthHelper(Context context) {
        mAccountManager = AccountManager.get(context);
        accountType = context.getString(R.string.accountType);
        authTokenType = context.getString(R.string.authTokenType);
    }

    public Account[] getAccounts() {
        return mAccountManager.getAccountsByType(accountType);
    }

    @Deprecated
    public void addAccountExplicitly(String login, String password, int userRights, String authToken, String authTokenType) {
        final Account account = new Account(login, accountType);
        Bundle userData = new Bundle();
        userData.putString(USER_RIGHTS, String.valueOf(userRights));
        userData.putString(AccountManager.KEY_AUTHTOKEN, authToken);
        mAccountManager.addAccountExplicitly(account, password, userData);
        mAccountManager.setAuthToken(account, authTokenType, authToken);
    }

    @Deprecated
    public void addAccount(Activity activity) {
        mAccountManager.addAccount(accountType, authTokenType, null, null, activity, null, null);
    }

    public int getUserRights(Account account) {
        String stringRights = mAccountManager.getUserData(account, USER_RIGHTS);
        if (TextUtils.isEmpty(stringRights)) {
            return UserRights.NONE;
        }

        return Integer.parseInt(stringRights);
    }

    public boolean hasAccountRights(Account account, int rightsMask) {
        int accountRight = getUserRights(account);
        return (accountRight & rightsMask) != UserRights.NONE;
    }

    public String getAuthToken(Account account) {
        return mAccountManager.getUserData(account, AccountManager.KEY_AUTHTOKEN);
    }

    public void removeAccounts(Activity activity) {
        Account[] accounts = getAccounts();
        for (Account account : accounts) {
            if (Build.VERSION.SDK_INT < 22) {
                mAccountManager.removeAccount(account, null, null);
            } else {
                mAccountManager.removeAccount(account, activity, null, null);
            }
        }
    }

    public boolean isNeedAuthenticateUser(){
        return getAccounts().length == 0;
    }
}
