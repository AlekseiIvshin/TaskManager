package com.alekseiivhsin.taskmanager.fragments;

import android.accounts.Account;
import android.support.test.runner.AndroidJUnit4;

import com.alekseiivhsin.taskmanager.R;
import com.alekseiivhsin.taskmanager.authentication.AuthHelper;
import com.alekseiivhsin.taskmanager.authentication.UserRights;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Created on 14/12/2015.
 */
@RunWith(AndroidJUnit4.class)
public class TaskListLeadFragmentTest extends BaseTaskListFragmentTest {

    Account[] stubAccountArray = {new Account(STUB_LOGIN,STUB_TYPE)};

    @Override
    public void onAddAccount() {
        when(mMockAuthModule.mockAuthHelper.getAccounts()).thenReturn(stubAccountArray);
        when(mMockAuthModule.mockAuthHelper.getUserRights(any(Account.class))).thenReturn(POOL_LEAD_RIGHTS);
        when(mMockAuthModule.mockAuthHelper.hasAccountRights(any(Account.class), UserRights.CAN_CREATE_TASK)).thenReturn(true);
    }

    @Test
    public void onLoad_shouldEnableAddsNewTaskWhenUserIsPoolLead() {
        onView(withId(R.id.add_new_task)).check(matches(isDisplayed()));
    }

}
