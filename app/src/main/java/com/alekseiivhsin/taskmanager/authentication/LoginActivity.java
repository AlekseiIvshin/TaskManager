package com.alekseiivhsin.taskmanager.authentication;

import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;

import com.alekseiivhsin.taskmanager.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    public static final String EXTRA_TOKEN_TYPE = "com.alivshin.taskmanager.extras.EXTRA_TOKEN_TYPE";

    @Bind(R.id.input_login)
    EditText mLogin;

    @Bind(R.id.input_password)
    EditText mPassword;

    private AuthenticatorDelegate mAuthenticatorDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
        Log.v(TAG, String.format("Try to sign in with params: login = '%s', password = '%s'", loginName, password));
        throw new UnsupportedOperationException("Not implemented yet!");
        // TODO: implement login. Might be used AuthTokenLoader.
//        setAccountAuthenticatorResult(buildResult(loginName,"stub auth token"));
    }

    public Bundle buildResult(String login, String authToken){
        Bundle result = new Bundle();
        result.putString(AccountManager.KEY_ACCOUNT_NAME, login);
        result.putString(AccountManager.KEY_ACCOUNT_TYPE, "com.alivshin.taskmanager");
        result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
        return result;
    }


    public final void setAccountAuthenticatorResult(Bundle result) {
        if (mAuthenticatorDelegate != null) {
            mAuthenticatorDelegate.setAccountAuthenticatorResult(result);
        }
    }

    /**
     * Sends the result or a Constants.ERROR_CODE_CANCELED error if a result isn't present.
     */
    public void finish() {
        if (mAuthenticatorDelegate != null) {
            mAuthenticatorDelegate.putResultsToAuthenticator();
        }
        super.finish();
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
