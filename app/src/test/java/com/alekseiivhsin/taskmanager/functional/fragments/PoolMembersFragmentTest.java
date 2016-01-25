package com.alekseiivhsin.taskmanager.functional.fragments;

import android.support.v7.widget.RecyclerView;

import com.alekseiivhsin.taskmanager.App;
import com.alekseiivhsin.taskmanager.BuildConfig;
import com.alekseiivhsin.taskmanager.R;
import com.alekseiivhsin.taskmanager.authentication.AuthHelper;
import com.alekseiivhsin.taskmanager.authentication.UserRights;
import com.alekseiivhsin.taskmanager.shadows.MyRobolectricRunner;
import com.alekseiivhsin.taskmanager.shadows.MyShadowAccountManager;

import org.hamcrest.CoreMatchers;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import static com.alekseiivhsin.taskmanager.functional.fragments.ShadowPoolSpiceManager.POOL_MEMBER_COUNT;
import static com.alekseiivhsin.taskmanager.shadows.MyShadowAccountManager.STUB_SUCCESS_ACCOUNT_NAME;
import static com.alekseiivhsin.taskmanager.shadows.MyShadowAccountManager.STUB_SUCCESS_AUTH_TOKEN;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;

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

    AuthHelper mAuthHelper;

    PoolMembersFragment fragment;

    @Before
    public void setUp() {
        fragment = new PoolMembersFragment();
        mAuthHelper = AuthHelper.get(RuntimeEnvironment.application);
    }

    @Test
    public void onLoad_shouldLoadPoolMemberList() {
        // Given
        mAuthHelper.addAccount(mAuthHelper.createNewAccount(STUB_SUCCESS_ACCOUNT_NAME),
                STUB_ACCOUNT_PASSWORD, STUB_SUCCESS_AUTH_TOKEN, UserRights.NONE);

        // When
        SupportFragmentTestUtil.startFragment(fragment);

        // Then
        RecyclerView poolList = (RecyclerView) fragment.getView().findViewById(R.id.list_pool);
        assertNotNull(poolList);
        Assert.assertThat(poolList.getAdapter().getItemCount(), is(equalTo(POOL_MEMBER_COUNT)));
    }
}
