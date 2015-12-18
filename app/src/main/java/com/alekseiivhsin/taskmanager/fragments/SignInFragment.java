package com.alekseiivhsin.taskmanager.fragments;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.alekseiivhsin.taskmanager.R;
import com.alekseiivhsin.taskmanager.loaders.AuthTokenLoader;
import com.alekseiivhsin.taskmanager.authentication.UserRights;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInFragment extends Fragment implements LoaderManager.LoaderCallbacks<Intent> {

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
    private String mAccountType;

    Handler mSignInHandler = new Handler(Looper.getMainLooper());
    private SignInCallbacks mCallbacks;

    public static SignInFragment newInstance(boolean addNewAccount) {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
        args.putBoolean(EXTRA_IS_ADDING_NEW_ACCOUNT, addNewAccount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDetach() {
        mCallbacks = null;
        super.onDetach();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SignInCallbacks) {
            mCallbacks = (SignInCallbacks) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAccountManager = AccountManager.get(getActivity());
        mAuthTokenType = getString(R.string.authTokenType);
        mAccountType = getString(R.string.accountType);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_in, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @OnClick(R.id.submit)
    public void submit() {
        String loginName = mLogin.getText().toString();
        String password = mPassword.getText().toString();


        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);

        if (validateInputFields()) {
            getLoaderManager().initLoader(0, AuthTokenLoader.buildRequestBundle(loginName, password, mAccountType), this);
        }
    }

    @Override
    public Loader<Intent> onCreateLoader(int id, Bundle args) {
        return new AuthTokenLoader(getActivity(), args);
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

        // TODO: add other validations as login mask, login length and etc
        return true;
    }

    public boolean validatePassword() {
        String password = mPassword.getText().toString();
        if (password.isEmpty()) {
            mPasswordInputLayout.setError(getString(R.string.error_password_empty));
            return false;
        }

        // TODO: add other validations as login mask, login length and etc
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
        if (getArguments() != null && getArguments().getBoolean(EXTRA_IS_ADDING_NEW_ACCOUNT, false)) {
            String authToken = responseData.getStringExtra(AccountManager.KEY_AUTHTOKEN);

            Bundle userData = new Bundle();
            userData.putString(UserRights.USER_RIGHTS, String.valueOf(responseData.getIntExtra(UserRights.USER_RIGHTS, UserRights.NONE)));
            userData.putString(AccountManager.KEY_AUTHTOKEN, authToken);
            mAccountManager.addAccountExplicitly(account, password, userData);
            mAccountManager.setAuthToken(account, mAuthTokenType, authToken);
        } else {
            mAccountManager.setPassword(account, password);
        }

        mSignInHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mCallbacks != null) {
                    mCallbacks.onSignedIn(Activity.RESULT_OK);
                }
            }
        });
    }

    public interface SignInCallbacks {
        void onSignedIn(int result);
    }
}
