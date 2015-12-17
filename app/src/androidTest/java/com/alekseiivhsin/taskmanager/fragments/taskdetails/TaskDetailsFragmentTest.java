package com.alekseiivhsin.taskmanager.fragments.taskdetails;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.app.AppCompatActivity;

import com.alekseiivhsin.taskmanager.App;
import com.alekseiivhsin.taskmanager.MainActivity;
import com.alekseiivhsin.taskmanager.R;
import com.alekseiivhsin.taskmanager.fragments.BaseAlreadyLoggedTest;
import com.alekseiivhsin.taskmanager.fragments.TaskListFragment;
import com.alekseiivhsin.taskmanager.ioc.MockAuthModule;
import com.alekseiivhsin.taskmanager.ioc.MockedGraph;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * Created on 14/12/2015.
 */
@RunWith(AndroidJUnit4.class)
public abstract class TaskDetailsFragmentTest extends BaseAlreadyLoggedTest {

    private static final String TASK_DETAILS_TAG = "TASK_DETAILS_TAG";

    protected AppCompatActivity mActivity;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    protected MockAuthModule mMockAuthModule;

    @Before
    public void setUp() {
        mMockAuthModule = new MockAuthModule(mActivityTestRule.getActivity().getApplication());
        ((App) mActivityTestRule.getActivity().getApplication())
                .setObjectGraph(MockedGraph.MockedInitializer.init(mMockAuthModule));
        mActivity = mActivityTestRule.getActivity();
        onAddAccount();
        mActivity
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new TaskListFragment(), TASK_DETAILS_TAG)
                .addToBackStack(null)
                .commit();
    }

    /**
     * Will call before adding fragment info activity.
     */
    public abstract void onAddAccount();
}
