package com.alekseiivhsin.taskmanager;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.alekseiivhsin.taskmanager.authentication.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private static final int AUTHENTICATION_REQUEST_ID = 1;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Account[] accounts = AccountManager.get(this).getAccountsByType(getString(R.string.accountType));
        if (accounts.length == 0) {
            Intent authIntent = new Intent(this, LoginActivity.class);
            startActivityForResult(authIntent, AUTHENTICATION_REQUEST_ID);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case AUTHENTICATION_REQUEST_ID:
                Log.v(TAG, "User logged in: " + data);
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
