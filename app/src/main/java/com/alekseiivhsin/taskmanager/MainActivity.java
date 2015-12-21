package com.alekseiivhsin.taskmanager;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.alekseiivhsin.taskmanager.authentication.AuthHelper;
import com.alekseiivhsin.taskmanager.fragments.SignInFragment;
import com.alekseiivhsin.taskmanager.fragments.TaskListFragment;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SignInFragment.SignInCallbacks {
    private static final String TASK_LIST_TAG = "taskmanager.fragments.TASK_LIST_TAG";
    public static final String SIGN_IN_TAG = "taskmanager.fragments.SIGN_IN_TAG";

    @Inject
    AuthHelper mAuthHelper;

    @Bind(R.id.navigation_drawer)
    NavigationView mNavigationView;

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        ((App) getApplication()).getObjectGraph().inject(this);

        initializeDrawer();

        if (savedInstanceState == null) {
//            if (mAuthHelper.isNeedAuthenticateUser()) {
//                showSignIn();
//            } else {
//                showTasksList();
//            }
        }

    }

    @Override
    protected void onDestroy() {
        mDrawerLayout.setDrawerListener(null);
        super.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                showSignIn();
                mDrawerLayout.closeDrawers();
                return true;
            default:
                return false;
        }
    }

    public void showSignIn() {
        mAuthHelper.removeAccounts(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, SignInFragment.newInstance(true), SIGN_IN_TAG)
                .addToBackStack(SIGN_IN_TAG)
                .commit();
    }

    public void showTasksList() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new TaskListFragment(), TASK_LIST_TAG)
                .commit();
    }

    @Override
    public void onSignedIn(int result) {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack(SIGN_IN_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            showTasksList();
        }
    }

    private void initializeDrawer() {
        mNavigationView.setNavigationItemSelectedListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                InputMethodManager inputMethodManager = (InputMethodManager) MainActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(), 0);
            }
        };

        mDrawerLayout.setDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

}
