package com.alekseiivhsin.taskmanager.fragments;

import android.support.test.espresso.Espresso;
import android.util.Log;

import com.alekseiivhsin.taskmanager.idlingresources.RobospiceIdlingResource;
import com.octo.android.robospice.SpiceManager;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import java.io.IOException;

/**
 * Created by Aleksei Ivshin
 * on 17.01.2016.
 */
public abstract class BaseSpicedInjectedFragmentTest {

    protected static MockWebServer server;

    private RobospiceIdlingResource robospiceIdlingResource;

    @BeforeClass
    public static void init() throws IOException {
        server = new MockWebServer();
        server.start();
    }

    @Before
    public void setUp() {
        onInitObjectGraph();

        robospiceIdlingResource = new RobospiceIdlingResource(getSpiceManager());

        Espresso.registerIdlingResources(robospiceIdlingResource);
    }

    @After
    public void tearDown() {
        Espresso.unregisterIdlingResources(robospiceIdlingResource);
    }

    @AfterClass
    public static void finish() throws IOException {
        server.shutdown();
    }

    protected abstract SpiceManager getSpiceManager();

    protected abstract void onInitObjectGraph();
}
