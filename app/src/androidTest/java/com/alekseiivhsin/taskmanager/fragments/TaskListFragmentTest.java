package com.alekseiivhsin.taskmanager.fragments;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.alekseiivhsin.taskmanager.App;
import com.alekseiivhsin.taskmanager.MainActivity;
import com.alekseiivhsin.taskmanager.R;
import com.alekseiivhsin.taskmanager.ioc.MockTaskListModule;
import com.alekseiivhsin.taskmanager.ioc.MockedGraph;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created on 14/12/2015.
 */
@RunWith(AndroidJUnit4.class)
public class TaskListFragmentTest {
    private static final String TASK_LIST_TAG = "taskmanager.fragments.TASK_LIST_TAG";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    AppCompatActivity mActivity;

    MockTaskListModule mMockTaskListModule;

    @BeforeClass
    public static void init() {
        App.getInstance()
                .setObjectGraph(MockedGraph.MockedInitializer.init(new MockTaskListModule()));
    }

    @Before
    public void setUp() {
        mMockTaskListModule = new MockTaskListModule();
        ((App) mActivityTestRule.getActivity().getApplication())
                .setObjectGraph(MockedGraph.MockedInitializer.init(mMockTaskListModule));
        mActivity = mActivityTestRule.getActivity();
        mActivity
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new TaskListFragment(), TASK_LIST_TAG)
                .addToBackStack(null)
                .commit();
    }

    @After
    public void tearDown() {
        Fragment fragment = mActivity.getSupportFragmentManager().findFragmentByTag(TASK_LIST_TAG);
        mActivity
                .getSupportFragmentManager()
                .beginTransaction()
                .remove(fragment)
                .commit();
    }

    @Test
    public void onLoad_shouldLoadWithoutErrors() {
        // Given
        junit.framework.Assert.assertTrue(true);
    }

}
