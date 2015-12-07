package com.alekseiivhsin.taskmanager.authentication;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

/**
 * Created on 07/12/2015.
 */
public class AuthHelper {

    public static final String USER_TYPE = "com.alekseiivhsin.taskmanager.authentication.USER_TYPE";

    private final AccountManager mAccountManager;


    public static AuthHelper get(Context context){
        return new AuthHelper(context);
    }

    public AuthHelper(Context context) {
        mAccountManager = AccountManager.get(context);
    }

    public boolean isUserType(Account account, String type) {
        return mAccountManager.getUserData(account, USER_TYPE).equals(type);
    }

    public void addAccount(String login, String password, String userType, String authToken, String accountType){
        final Account account = new Account(login, accountType);
        mAccountManager.addAccountExplicitly(account, password, null);
        mAccountManager.setAuthToken(account, authToken, authToken);
        mAccountManager.setUserData(account, USER_TYPE, userType);
    }
}
