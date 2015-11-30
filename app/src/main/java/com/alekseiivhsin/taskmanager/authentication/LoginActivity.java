package com.alekseiivhsin.taskmanager.authentication;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.alekseiivhsin.taskmanager.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Intent> {
    private static final String TAG = LoginActivity.class.getSimpleName();

    public static final String EXTRA_TOKEN_TYPE = "com.alivshin.taskmanager.extras.EXTRA_TOKEN_TYPE";

    private String mAuthTokenType;

    private static final int AUTH_LOADER_ID = 0;

    @Bind(R.id.input_login)
    EditText mLogin;

    @Bind(R.id.input_password)
    EditText mPassword;

    private AuthenticatorDelegate mAuthenticatorDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuthTokenType = getString(R.string.accountType);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuthenticatorDelegate = new AuthenticatorDelegate();
        mAuthenticatorDelegate.loadResponse();
    }

    @OnClick(R.id.login)
    public void login() {
        String loginName = mLogin.getText().toString();
        String password = mPassword.getText().toString();

        getSupportLoaderManager().initLoader(AUTH_LOADER_ID, AuthTokenLoader.buildRequestBundle(loginName, password), this);
    }


    public final void setAccountAuthenticatorResult(Bundle result) {
        if (mAuthenticatorDelegate != null) {
            mAuthenticatorDelegate.setAccountAuthenticatorResult(result);
        }
    }

    /**
     * Sends the result or a Constants.ERROR_CODE_CANCELED error if a result isn't present.
     */
    public void finishLogin(Intent responseData) {
        String login = responseData.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String password = responseData.getStringExtra(AuthTokenLoader.EXTRA_PASSWORD);
        final Account account = new Account(login, mAuthTokenType);
        String authToken = responseData.getStringExtra(AccountManager.KEY_AUTHTOKEN);
        AccountManager accountManager = AccountManager.get(this);
        accountManager.addAccountExplicitly(account, password, null);
        accountManager.setAuthToken(account, mAuthTokenType, authToken);
        setResult(RESULT_OK,responseData);
        finish();
    }

    @Override
    public Loader<Intent> onCreateLoader(int id, Bundle args) {
        return new AuthTokenLoader(this, args);
    }

    @Override
    public void onLoadFinished(Loader<Intent> loader, Intent data) {
        finishLogin(data);
    }

    @Override
    public void onLoaderReset(Loader<Intent> loader) {

    }

    private class AuthenticatorDelegate {
        private AccountAuthenticatorResponse mAccountAuthenticatorResponse = null;
        private Bundle mResultBundle = null;

        public final void loadResponse() {
            mAccountAuthenticatorResponse =
                    getIntent().getParcelableExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE);
            if (mAccountAuthenticatorResponse != null) {
                mAccountAuthenticatorResponse.onRequestContinued();
            }
        }

        public final void setAccountAuthenticatorResult(Bundle result) {
            mResultBundle = result;
        }

        public final void putResultsToAuthenticator() {
            if (mAccountAuthenticatorResponse != null) {
                // send the result bundle back if set, otherwise send an error.
                if (mResultBundle != null) {
                    mAccountAuthenticatorResponse.onResult(mResultBundle);
                } else {
                    mAccountAuthenticatorResponse.onError(AccountManager.ERROR_CODE_CANCELED,
                            "canceled");
                }
                mAccountAuthenticatorResponse = null;
            }
        }
    }
}
