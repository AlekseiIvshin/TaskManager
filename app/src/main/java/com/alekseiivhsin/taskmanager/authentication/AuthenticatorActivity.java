package com.alekseiivhsin.taskmanager.authentication;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import com.alekseiivhsin.taskmanager.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthenticatorActivity extends AccountAuthenticatorActivity implements LoaderManager.LoaderCallbacks<Intent> {

    public static final String EXTRA_ACCOUNT_TYPE = "EXTRA_ACCOUNT_TYPE";
    public static final String EXTRA_AUTH_TYPE = "EXTRA_AUTH_TYPE";
    public static final String EXTRA_IS_ADDING_NEW_ACCOUNT = "EXTRA_IS_ADDING_NEW_ACCOUNT";


    private AccountManager mAccountManager;

    @Bind(R.id.input_login)
    EditText mLogin;

    @Bind(R.id.input_layout_login)
    TextInputLayout mLoginInputLayout;

    @Bind(R.id.input_password)
    EditText mPassword;

    @Bind(R.id.input_layout_password)
    TextInputLayout mPasswordInputLayout;

    private String mAuthTokenType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticator);

        ButterKnife.bind(this);
        mAccountManager = AccountManager.get(this);
        mAuthTokenType = getString(R.string.authTokenType);
    }

    @OnClick(R.id.sign_in)
    public void submit() {
        String loginName = mLogin.getText().toString();
        String password = mPassword.getText().toString();

        if (validateInputFields()) {
            getLoaderManager().initLoader(0, AuthTokenLoader.buildRequestBundle(loginName, password, getString(R.string.accountType)), this);
        }
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

    public boolean validateInputFields() {
        boolean isValid = validateLogin();
        isValid = isValid & validatePassword();
        return isValid;
    }

    public boolean validateLogin() {
        String loginName = mLogin.getText().toString();
        if (loginName.isEmpty()) {
            mLoginInputLayout.setError(getString(R.string.error_login_empty));
            return false;
        }

        // TODO: add other validations as signIn mask, signIn length and etc
        return true;
    }

    public boolean validatePassword() {
        String password = mPassword.getText().toString();
        if (password.isEmpty()) {
            mPasswordInputLayout.setError(getString(R.string.error_password_empty));
            return false;
        }

        // TODO: add other validations as signIn mask, signIn length and etc
        return true;
    }

    /**
     * Sends the result or a Constants.ERROR_CODE_CANCELED error if a result isn't present.
     */
    public void finishLogin(Intent responseData) {
        String accountName = responseData.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String password = responseData.getStringExtra(AuthTokenLoader.EXTRA_PASSWORD);
        String accountType = responseData.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE);

        final Account account = new Account(accountName, accountType);
        if (getIntent().getBooleanExtra(EXTRA_IS_ADDING_NEW_ACCOUNT, false)) {
            String authToken = responseData.getStringExtra(AccountManager.KEY_AUTHTOKEN);

            Bundle userData = new Bundle();
            userData.putString(UserRights.USER_RIGHTS, String.valueOf(responseData.getIntExtra(UserRights.USER_RIGHTS, UserRights.NONE)));
            userData.putString(AccountManager.KEY_AUTHTOKEN, authToken);
            mAccountManager.addAccountExplicitly(account, password, userData);
            mAccountManager.setAuthToken(account, mAuthTokenType, authToken);
        } else {
            mAccountManager.setPassword(account, password);
        }

        setAccountAuthenticatorResult(responseData.getExtras());

        setResult(RESULT_OK, responseData);
        finish();
    }
}
