package com.alekseiivhsin.taskmanager.fragments.tasklist;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.alekseiivhsin.taskmanager.App;
import com.alekseiivhsin.taskmanager.MainActivity;
import com.alekseiivhsin.taskmanager.R;
import com.alekseiivhsin.taskmanager.authentication.AuthHelper;
import com.alekseiivhsin.taskmanager.fragments.SpicedFragment;
import com.alekseiivhsin.taskmanager.idlingresources.RobospiceIdlingResource;
import com.alekseiivhsin.taskmanager.ioc.AuthModule;
import com.alekseiivhsin.taskmanager.ioc.Graph;
import com.alekseiivhsin.taskmanager.ioc.MockedGraph;
import com.alekseiivhsin.taskmanager.ioc.StubNetworkModule;
import com.alekseiivhsin.taskmanager.models.Task;
import com.alekseiivhsin.taskmanager.network.responses.TaskListResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.octo.android.robospice.SpiceManager;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created on 21/12/2015.
 */
@RunWith(AndroidJUnit4.class)
public class TaskListFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static MockWebServer server;

    AuthHelper mockAuthHelper;

    RobospiceIdlingResource robospiceIdlingResource;

    @BeforeClass
    public static void init() throws IOException {
        server = new MockWebServer();
        server.start();
    }

    @Before
    public void setUp() {

        HttpUrl baseUrl = server.url("");

        StubNetworkModule stubNetworkModule = new StubNetworkModule();
        stubNetworkModule.stubbedApiBaseUrl = baseUrl.url().toString();

        mockAuthHelper = mock(AuthHelper.class);
        AuthModule authModule = mock(AuthModule.class);
        when(authModule.provideAuthHelper()).thenReturn(mockAuthHelper);

        Graph mockedGraph = MockedGraph.MockGraphBuilder.begin()
                .setStubNetworkModule(stubNetworkModule)
                .setStubAuthModule(authModule)
                .build();
        App.getInstance()
                .setObjectGraph(mockedGraph);

    }

    @After
    public void tearDown(){
        if(robospiceIdlingResource!=null){
            Espresso.unregisterIdlingResources(robospiceIdlingResource);
            robospiceIdlingResource = null;
        }
    }

    @AfterClass
    public static void finish() throws IOException {
        server.shutdown();
    }


    @Test
    public void onLoad_shouldLoadTaskList() throws IOException {
        // Given
        TaskListResponse taskListResponse = new TaskListResponse();
        taskListResponse.taskList = Arrays.asList(new Task("Task 1"), new Task("Task 2"), new Task("Task 3"));
        StringWriter stringWriter = new StringWriter();

        MAPPER.writeValue(stringWriter, taskListResponse);

        server.enqueue(new MockResponse().addHeader("Content-Type", "application/json; charset=utf-8").setBody(stringWriter.toString()));

        when(mockAuthHelper.getAuthToken()).thenReturn("STUB_AUTH_TOKEN");
        when(mockAuthHelper.hasAccountRights(anyInt())).thenReturn(true);

        // When
        activityTestRule.getActivity().showTasksList();
        onView(withId(R.id.fragment_container)).check(matches(isDisplayed()));
        robospiceIdlingResource = new RobospiceIdlingResource(getSpiceManager());
        Espresso.registerIdlingResources(robospiceIdlingResource);

        // Then
        onView(withId(R.id.list_tasks)).check(matches(withChild(withText("Task 1"))));
    }

    private SpiceManager getSpiceManager(){
        Log.v("ZAAAD", "Fragments count " + activityTestRule.getActivity().getSupportFragmentManager());
        SpicedFragment spicedFragment = (SpicedFragment) activityTestRule.getActivity().getSupportFragmentManager()
                .findFragmentByTag(MainActivity.TAG_TASK_LIST);

        return spicedFragment.spiceManager;

    }

}
