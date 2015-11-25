package com.alekseiivhsin.taskmanager.authentication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alekseiivhsin.taskmanager.R;

public class LoginActivity extends AppCompatActivity {

    public static final String EXTRA_TOKEN_TYPE = "com.alivshin.taskmanager.extras.EXTRA_TOKEN_TYPE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
