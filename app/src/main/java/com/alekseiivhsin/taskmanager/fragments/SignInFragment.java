package com.alekseiivhsin.taskmanager.fragments;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alekseiivhsin.taskmanager.R;
import com.alekseiivhsin.taskmanager.authentication.UserRights;
import com.alekseiivhsin.taskmanager.network.requests.SignInRequest;
import com.alekseiivhsin.taskmanager.network.responses.SignInResponse;
import com.alekseiivhsin.taskmanager.robospice.TaskSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInFragment extends SpicedFragment {

    public static final String EXTRA_IS_ADDING_NEW_ACCOUNT = "EXTRA_IS_ADDING_NEW_ACCOUNT";
    private static final String TAG = SignInFragment.class.getSimpleName();

    private AccountManager mAccountManager;

    @Bind(R.id.input_login)
    EditText mLogin;

    @Bind(R.id.input_layout_login)
    TextInputLayout mLoginInputLayout;

    @Bind(R.id.input_password)
    EditText mPassword;

    @Bind(R.id.input_layout_password)
    TextInputLayout mPasswordInputLayout;

    @Bind(R.id.sign_in)
    Button mSignIn;

    @Bind(R.id.error_message)
    TextView mErrorMessages;

    private String mAuthTokenType;
    private String mAccountType;

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

    @OnClick(R.id.sign_in)
    public void submit() {

        String userName = mLogin.getText().toString();
        String password = mPassword.getText().toString();

        boolean isUserNameValid = validateLogin(userName);
        boolean isPasswordValid = validatePassword(password);

        if (isUserNameValid && isPasswordValid) {
            onRequestStarted();

            SignInRequest request = new SignInRequest(userName, password, mAccountType);

            spiceManager.execute(request, new RequestListener<SignInResponse>() {
                @Override
                public void onRequestFailure(SpiceException spiceException) {
                    Log.v(TAG, "Failure on sign in: " + spiceException.getMessage());
                    showErrorMessage(spiceException.getMessage());
                    unlockInputFields();
                }

                @Override
                public void onRequestSuccess(SignInResponse signInResponse) {
                    if (signInResponse == null) {
                        onRequestFailure(new SpiceException("Empty response"));
                    } else {
                        Log.v(TAG, "Success on sign in: " + signInResponse.authToken);
                        finishLogin(signInResponse);
                    }
                }
            });
        }
    }

    public boolean validateLogin(String userName) {
        if (TextUtils.isEmpty(userName)) {
            mLoginInputLayout.setError(getString(R.string.error_login_empty));
            return false;
        }

        // TODO: add other validations as signIn mask, signIn length and etc
        return true;
    }

    public boolean validatePassword(String password) {
        if (TextUtils.isEmpty(password)) {
            mPasswordInputLayout.setError(getString(R.string.error_password_empty));
            return false;
        }

        // TODO: add other validations as signIn mask, signIn length and etc
        return true;
    }

    public void onRequestStarted() {
        mErrorMessages.setVisibility(View.GONE);
        mLoginInputLayout.setError("");
        mPasswordInputLayout.setError("");

        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);

        lockInputFields();
    }

    public void lockInputFields() {
        mLogin.setEnabled(false);
        mPassword.setEnabled(false);
        mSignIn.setEnabled(false);
    }

    public void unlockInputFields() {
        mLogin.setEnabled(true);
        mPassword.setEnabled(true);
        mSignIn.setEnabled(true);
    }


    /**
     * Sends the result or a Constants.ERROR_CODE_CANCELED error if a result isn't present.
     */
    public void finishLogin(SignInResponse signInResponse) {
        String accountName = mLogin.getText().toString();
        String password = mLogin.getText().toString();

        final Account account = new Account(accountName, mAccountType);
        if (getArguments() != null && getArguments().getBoolean(EXTRA_IS_ADDING_NEW_ACCOUNT, false)) {
            String authToken = signInResponse.authToken;

            Bundle userData = new Bundle();
            userData.putString(UserRights.USER_RIGHTS, String.valueOf(signInResponse.userRights));
            userData.putString(AccountManager.KEY_AUTHTOKEN, authToken);
            mAccountManager.addAccountExplicitly(account, password, userData);
            mAccountManager.setAuthToken(account, mAuthTokenType, authToken);
        } else {
            mAccountManager.setPassword(account, password);
        }

        unlockInputFields();
        mCallbacks.onSignedIn(Activity.RESULT_OK);
    }

    public void showErrorMessage(String message) {
        mErrorMessages.setVisibility(View.VISIBLE);
        mErrorMessages.setText(message);
    }

    public interface SignInCallbacks {
        void onSignedIn(int result);
    }
}
