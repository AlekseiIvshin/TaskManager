package com.alekseiivhsin.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alekseiivhsin.taskmanager.authentication.AuthHelper;
import com.alekseiivhsin.taskmanager.authentication.LoginActivity;
import com.alekseiivhsin.taskmanager.fragments.TaskListFragment;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    private static final String TASK_LIST_TAG = "taskmanager.fragments.TASK_LIST_TAG";

    private static final int REQUEST_LOGIN = 1;

    @Inject
    AuthHelper mAuthHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((App) getApplication()).getObjectGraph().inject(this);

        if (savedInstanceState == null) {
            if (mAuthHelper.getAccounts().length == 0) {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivityForResult(intent, REQUEST_LOGIN);
            } else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new TaskListFragment(), TASK_LIST_TAG)
//                        .addToBackStack(null)
                        .commit();
            }
        }
    }
}
