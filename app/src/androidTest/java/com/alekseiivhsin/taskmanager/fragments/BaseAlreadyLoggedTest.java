package com.alekseiivhsin.taskmanager.fragments;

import android.accounts.Account;
import android.support.test.runner.AndroidJUnit4;

import com.alekseiivhsin.taskmanager.App;
import com.alekseiivhsin.taskmanager.ioc.MockAuthModule;
import com.alekseiivhsin.taskmanager.ioc.MockedGraph;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.when;

/**
 * Created on 17/12/2015.
 */
@RunWith(AndroidJUnit4.class)
@Ignore
public class BaseAlreadyLoggedTest {

    public static final String STUB_LOGIN = "STUB_LOGIN";
    public static final String STUB_PASSWORD = "STUB_PASSWORD";
    public static final String STUB_AUTH_TOKEN = "STUB_AUTH_TOKEN";
    public static final String STUB_TYPE = "STUB_TYPE";

    protected static Account stubAccount = new Account(STUB_LOGIN,STUB_TYPE);
    protected static Account[] stubAccountArray = {stubAccount};

    @BeforeClass
    public static void init() {
        MockAuthModule mockAuthModule = new MockAuthModule(App.getInstance());
        when(mockAuthModule.mockAuthHelper.getAccounts()).thenReturn(stubAccountArray);
        App.getInstance()
                .setObjectGraph(MockedGraph.MockedInitializer.init(mockAuthModule));
    }
}
