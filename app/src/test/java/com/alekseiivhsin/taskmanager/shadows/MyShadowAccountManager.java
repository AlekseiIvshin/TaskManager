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

    public static final String STUB_ACCOUNT_NAME = "STUB_ACCOUNT_NAME";

    public static final String STUB_AUTH_TOKEN = "STUB_AUTH_TOKEN";

    @Override
    @Implementation
    public Account[] getAccountsByType(String type) {
        return new Account[]{new Account(STUB_ACCOUNT_NAME, type)};
    }

    @Override
    @Implementation
    public String getUserData(Account account, String key) {
        switch (key) {
            case AccountManager.KEY_AUTHTOKEN:
                return STUB_AUTH_TOKEN;
            default:
                throw new IllegalArgumentException("No such user data: " + key);
        }
    }
}
