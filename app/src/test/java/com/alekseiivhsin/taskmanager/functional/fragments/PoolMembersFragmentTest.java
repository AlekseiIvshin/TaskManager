package com.alekseiivhsin.taskmanager.functional.fragments;

import android.support.v7.widget.RecyclerView;

import com.alekseiivhsin.taskmanager.App;
import com.alekseiivhsin.taskmanager.BuildConfig;
import com.alekseiivhsin.taskmanager.R;
import com.alekseiivhsin.taskmanager.SpicedActivity;
import com.alekseiivhsin.taskmanager.authentication.AuthHelper;
import com.alekseiivhsin.taskmanager.authentication.UserRights;
import com.alekseiivhsin.taskmanager.shadows.MyRobolectricRunner;
import com.alekseiivhsin.taskmanager.shadows.MyShadowAccountManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLooper;

import static com.alekseiivhsin.taskmanager.SpicedActivity.TAG_POOL_MEMBERS;
import static com.alekseiivhsin.taskmanager.functional.fragments.ShadowPoolSpiceManager.POOL_MEMBER_COUNT;
import static com.alekseiivhsin.taskmanager.shadows.MyShadowAccountManager.STUB_SUCCESS_ACCOUNT_NAME;
import static com.alekseiivhsin.taskmanager.shadows.MyShadowAccountManager.STUB_SUCCESS_AUTH_TOKEN;
import static java.lang.String.format;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Aleksei Ivshin
 * on 24.01.2016.
 */
@RunWith(MyRobolectricRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml",
        sdk = 19,
        application = App.class,
        constants = BuildConfig.class,
        shadows = {ShadowPoolSpiceManager.class, MyShadowAccountManager.class})
public class PoolMembersFragmentTest {

    private final String STUB_ACCOUNT_PASSWORD = "STUB_ACCOUNT_PASSWORD";
    private final String STUB_AUTH_TOKEN = "STUB_AUTH_TOKEN";

    private SpicedActivity mActivity;

    AuthHelper mAuthHelper;

    @Before
    public void setUp() {
        mActivity = Robolectric.setupActivity(SpicedActivity.class);
        mAuthHelper = AuthHelper.get(RuntimeEnvironment.application);
    }

    @Test
    public void onLoad_shouldLoadPoolMemberList() {
        // Given
        mAuthHelper.addAccount(mAuthHelper.createNewAccount(STUB_SUCCESS_ACCOUNT_NAME),
                STUB_ACCOUNT_PASSWORD, STUB_SUCCESS_AUTH_TOKEN, UserRights.NONE);

        // When
        mActivity.replaceFragment(new PoolMembersFragment(), TAG_POOL_MEMBERS);
        ShadowLooper.runUiThreadTasks();

        // Then
        RecyclerView poolList = (RecyclerView) mActivity.findViewById(R.id.list_pool);
        assertNotNull(poolList);
        assertTrue(format("Pool list should contains at least %d, but contains %d",
                        POOL_MEMBER_COUNT,
                        poolList.getChildCount()),
                poolList.getChildCount() >= POOL_MEMBER_COUNT);
    }
}
