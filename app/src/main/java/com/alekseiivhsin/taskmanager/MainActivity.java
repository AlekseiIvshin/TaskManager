package com.alekseiivhsin.taskmanager;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.alekseiivhsin.taskmanager.authentication.AuthHelper;
import com.alekseiivhsin.taskmanager.fragments.SignInFragment;
import com.alekseiivhsin.taskmanager.fragments.TaskListFragment;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TASK_LIST_TAG = "taskmanager.fragments.TASK_LIST_TAG";

    private static final int REQUEST_SIGN_IN = 1;

    @Inject
    AuthHelper mAuthHelper;

    @Bind(R.id.navigation_drawer)
    NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        ((App) getApplication()).getObjectGraph().inject(this);

        mNavigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            if (mAuthHelper.getAccounts().length == 0) {
                showSignIn();
            } else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new TaskListFragment(), TASK_LIST_TAG)
//                        .addToBackStack(null)
                        .commit();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                showSignIn();
                return true;
            default:
                return false;
        }
    }

    protected void showSignIn() {
        mAuthHelper.removeAccounts(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, SignInFragment.newInstance(true), SignInFragment.SIGN_IN_TAG)
                .addToBackStack(SignInFragment.SIGN_IN_TAG)
                .commit();
    }

}
