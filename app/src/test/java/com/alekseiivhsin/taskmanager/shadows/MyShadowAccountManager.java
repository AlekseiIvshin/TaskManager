package com.alekseiivhsin.taskmanager.shadows;

import android.accounts.Account;
import android.accounts.AccountManager;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.shadows.ShadowAccountManager;

/**
 * Created by Aleksei Ivshin
 * on 24.01.2016.
 */
@Implements(AccountManager.class)
public class MyShadowAccountManager extends ShadowAccountManager {

    public static final String STUB_SUCCESS_ACCOUNT_NAME = "STUB_SUCCESS_ACCOUNT_NAME";
    public static final String STUB_FAILURE_ACCOUNT_NAME = "STUB_FAILURE_ACCOUNT_NAME";

    public static final String STUB_SUCCESS_AUTH_TOKEN = "STUB_SUCCESS_AUTH_TOKEN";
    public static final String STUB_FAILURE_AUTH_TOKEN = "STUB_FAILURE_AUTH_TOKEN";

    @Override
    @Implementation
    public String getUserData(Account account, String key) {
        switch (key) {
            case AccountManager.KEY_AUTHTOKEN:
                if (STUB_SUCCESS_ACCOUNT_NAME.equals(account.name)) {
                    return STUB_SUCCESS_AUTH_TOKEN;
                } else {
                    return STUB_FAILURE_AUTH_TOKEN;
                }
            default:
                throw new IllegalArgumentException("No such user data: " + key);
        }
    }
}
