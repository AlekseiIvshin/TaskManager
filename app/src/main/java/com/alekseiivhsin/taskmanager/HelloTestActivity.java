package com.alekseiivhsin.taskmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created on 28/01/2016.
 */
public class HelloTestActivity extends AppCompatActivity {

    @Bind(R.id.header_text)
    TextView mHelloText;

    @Bind(R.id.click_me)
    Button mClickMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_test);
        ButterKnife.bind(this);
    }
}
