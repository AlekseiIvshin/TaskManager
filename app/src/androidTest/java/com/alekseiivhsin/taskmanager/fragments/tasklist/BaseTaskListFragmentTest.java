package com.alekseiivhsin.taskmanager.fragments.tasklist;

import android.accounts.Account;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.app.AppCompatActivity;

import com.alekseiivhsin.taskmanager.App;
import com.alekseiivhsin.taskmanager.MainActivity;
import com.alekseiivhsin.taskmanager.R;
import com.alekseiivhsin.taskmanager.authentication.UserRights;
import com.alekseiivhsin.taskmanager.fragments.BaseAlreadyLoggedTest;
import com.alekseiivhsin.taskmanager.fragments.TaskListFragment;
import com.alekseiivhsin.taskmanager.ioc.MockAuthModule;
import com.alekseiivhsin.taskmanager.ioc.MockedGraph;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.when;

/**
 * Created on 14/12/2015.
 */
@RunWith(AndroidJUnit4.class)
public abstract class BaseTaskListFragmentTest extends BaseAlreadyLoggedTest {

    protected int POOL_LEAD_RIGHTS = UserRights.CAN_VIEW_TASK | UserRights.CAN_UPDATE_TASK | UserRights.CAN_CREATE_TASK | UserRights.CAN_CLOSE_TASK;
    protected int POOL_MEMBER_RIGHTS = UserRights.CAN_VIEW_TASK | UserRights.CAN_UPDATE_TASK;

    public static final String TASK_LIST_TAG = "taskmanager.fragments.TASK_LIST_TAG";

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
                .replace(R.id.fragment_container, new TaskListFragment(), TASK_LIST_TAG)
                .addToBackStack(null)
                .commit();
    }

    /**
     * Will call before adding fragment info activity.
     */
    public abstract void onAddAccount();
}
