package com.alekseiivhsin.taskmanager.fragments.tasklist;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.alekseiivhsin.taskmanager.App;
import com.alekseiivhsin.taskmanager.MainActivity;
import com.alekseiivhsin.taskmanager.R;
import com.alekseiivhsin.taskmanager.fragments.TaskListFragment;
import com.alekseiivhsin.taskmanager.ioc.Graph;
import com.alekseiivhsin.taskmanager.ioc.MockedGraph;
import com.alekseiivhsin.taskmanager.ioc.StubNetworkModule;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

/**
 * Created on 21/12/2015.
 */
@RunWith(AndroidJUnit4.class)
public class TaskListFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    private static MockWebServer server;

    @BeforeClass
    public static void init() throws IOException {
        server = new MockWebServer();
        server.start();

        HttpUrl baseUrl = server.url("");

        StubNetworkModule stubNetworkModule = new StubNetworkModule();
        stubNetworkModule.stubbedApiBaseUrl = baseUrl.url().toString();

        Graph mockedGraph = MockedGraph.MockGraphBuilder.begin()
                .setStubNetworkModule(stubNetworkModule)
                .build();
        App.getInstance()
                .setObjectGraph(mockedGraph);
    }

    @Before
    public void setUp() {
        activityTestRule.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new TaskListFragment())
                .commit();
    }

    @Test
    public void onLoad_shouldLoadTaskList() {

    }

    @AfterClass
    public static void finish() throws IOException {
        server.shutdown();
    }
}
