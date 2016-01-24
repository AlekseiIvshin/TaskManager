package com.alekseiivhsin.taskmanager.unit;

import android.accounts.Account;

import com.alekseiivhsin.taskmanager.App;
import com.alekseiivhsin.taskmanager.BuildConfig;
import com.alekseiivhsin.taskmanager.R;
import com.alekseiivhsin.taskmanager.authentication.AuthHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Random;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

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

    private final String STUB_ACCOUNT_NAME = "STUB_ACCOUNT_NAME";
    private final String STUB_ACCOUNT_PASSWORD = "STUB_ACCOUNT_PASSWORD";
    private final String STUB_AUTH_TOKEN = "STUB_AUTH_TOKEN";

    AuthHelper authHelper;

    @Before
    public void setUp() {
        authHelper = AuthHelper.get(RuntimeEnvironment.application);
    }

    @Test
    public void onGetAccount_shouldReturnNullWhenThereIsNotAccount() {
        assertNull("Account should be null", authHelper.getCurrentAccount());
    }

    @Test
    public void onCreateAccount_shouldSetDefaultValues() {
        // When
        Account account = authHelper.createNewAccount(STUB_ACCOUNT_NAME);
        String accountType = RuntimeEnvironment.application.getResources().getString(R.string.accountType);

        // Then
        assertEquals("Account name should be equals", STUB_ACCOUNT_NAME, account.name);
        assertEquals("Account type should be equals", accountType, account.type);
    }

    @Test
    public void onAddingAccount_shouldAddCountWithRightData() {
        // Given
        assertNull("Account should be null", authHelper.getCurrentAccount());
        Account addedAccount = authHelper.createNewAccount(STUB_ACCOUNT_NAME);
        int stubUserRights = generateRandomRights();

        // When
        authHelper.addAccount(addedAccount, STUB_ACCOUNT_PASSWORD, STUB_AUTH_TOKEN, stubUserRights);

        // Then
        Account receivedAccount = authHelper.getCurrentAccount();
        assertEquals(addedAccount.name, receivedAccount.name);
        assertEquals(addedAccount.type, receivedAccount.type);
        assertEquals(STUB_AUTH_TOKEN, authHelper.getAuthToken());
        assertEquals(stubUserRights, authHelper.getUserRights());
    }

    private int generateRandomRights() {
        return new Random().nextInt() * 9;
    }
}
