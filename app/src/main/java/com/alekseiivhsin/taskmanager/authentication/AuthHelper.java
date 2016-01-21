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

    public final String accountType;
    public final String authTokenType;


    public static AuthHelper get(Context context) {
        return new AuthHelper(context);
    }

    public AuthHelper(Context context) {
        mAccountManager = AccountManager.get(context);
        accountType = context.getString(R.string.accountType);
        authTokenType = context.getString(R.string.authTokenType);
    }

    private Account[] getAccounts() {
        return mAccountManager.getAccountsByType(accountType);
    }

    public void addAccount(Account account, String password, String authToken, int userRights) {
        Bundle userData = new Bundle();
        userData.putString(UserRights.USER_RIGHTS, String.valueOf(userRights));
        userData.putString(AccountManager.KEY_AUTHTOKEN, authToken);
        mAccountManager.addAccountExplicitly(account, password, userData);
        mAccountManager.setAuthToken(account, authTokenType, authToken);
    }

    public void setPassword(Account account, String password) {
        mAccountManager.setPassword(account, password);
    }

    public Account createNewAccount(String accountName) {
        return new Account(accountName, accountType);
    }

    public int getUserRights(Account account) {
        String stringRights = mAccountManager.getUserData(account, USER_RIGHTS);
        if (TextUtils.isEmpty(stringRights)) {
            return UserRights.NONE;
        }

        return Integer.parseInt(stringRights);
    }

    public boolean hasAccountRights(int rightsMask) {
        Account currentAccount = getCurrentAccount();
        if (currentAccount == null) {
            return false;
        }
        int accountRight = getUserRights(currentAccount);
        return (accountRight & rightsMask) != UserRights.NONE;
    }

    // TODO: STUB!
    public Account getCurrentAccount() {
        Account[] accounts = getAccounts();
        if (accounts.length == 0) {
            return null;
        }
        return accounts[0];
    }

    public String getAuthToken(Account account) {
        return mAccountManager.getUserData(account, AccountManager.KEY_AUTHTOKEN);
    }

    public String getAuthToken() {
        Account account = getCurrentAccount();
        return getAuthToken(account);
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

    public boolean isNeedAuthenticateUser() {
        return getAccounts().length == 0;
    }
}
