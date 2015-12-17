package com.alekseiivhsin.taskmanager.authentication;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
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

    public static AuthHelper get(Context context) {
        return new AuthHelper(context);
    }

    public AuthHelper(Context context) {
        mAccountManager = AccountManager.get(context);
        accountType = context.getString(R.string.accountType);
    }

    public Account[] getAccounts() {
        return mAccountManager.getAccountsByType(accountType);
    }

    public void addAccount(String login, String password, int userRights, String authToken) {
        final Account account = new Account(login, accountType);
        Bundle userData = new Bundle();
        userData.putString(USER_RIGHTS, String.valueOf(userRights));
        userData.putString(AccountManager.KEY_AUTHTOKEN, authToken);
        mAccountManager.addAccountExplicitly(account, password, userData);
        mAccountManager.setAuthToken(account, authToken, authToken);
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
}
