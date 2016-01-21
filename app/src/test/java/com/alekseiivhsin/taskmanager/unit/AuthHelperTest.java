package com.alekseiivhsin.taskmanager.unit;

import android.accounts.AccountManager;

import com.alekseiivhsin.taskmanager.App;
import com.alekseiivhsin.taskmanager.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

/**
 * Created by Aleksei Ivshin
 * on 21.01.2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml",
        sdk = 19,
        application = App.class,
        constants = BuildConfig.class)
public class AuthHelperTest {

    AccountManager mAccountManager;

    @Before
    public void setUp() {
        mAccountManager = AccountManager.get(RuntimeEnvironment.application);
    }

    @Test
    public void onAddingAccount_shouldSaveDataRight(){
        throw new IllegalStateException("Not implemented!");
    }
}
