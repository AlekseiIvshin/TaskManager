package com.alekseiivhsin.taskmanager.authentication;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.login)
    public void login() {
        String loginName = mLogin.getText().toString();
        String password = mPassword.getText().toString();
        Log.v(TAG, String.format("Try to sign in with params: login = '%s', password = '%s'", loginName, password));
        // TODO: implement login. Might be used AuthTokenLoader.
        throw new UnsupportedOperationException("Not implemented yet!");
    }
}
