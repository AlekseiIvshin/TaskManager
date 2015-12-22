package com.alekseiivhsin.taskmanager.fragments.taskdetails;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.alekseiivhsin.taskmanager.App;
import com.alekseiivhsin.taskmanager.MainActivity;
import com.alekseiivhsin.taskmanager.R;
import com.alekseiivhsin.taskmanager.authentication.AuthHelper;
import com.alekseiivhsin.taskmanager.authentication.UserRights;
import com.alekseiivhsin.taskmanager.fragments.BaseAlreadyLoggedTest;
import com.alekseiivhsin.taskmanager.fragments.TaskDetailsFragment;
import com.alekseiivhsin.taskmanager.ioc.AuthModule;
import com.alekseiivhsin.taskmanager.ioc.Graph;
import com.alekseiivhsin.taskmanager.ioc.MockAuthModule;
import com.alekseiivhsin.taskmanager.ioc.MockedGraph;
import com.alekseiivhsin.taskmanager.ioc.StubNetworkModule;
import com.alekseiivhsin.taskmanager.models.Task;
import com.alekseiivhsin.taskmanager.network.responses.SignInResponse;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.StringWriter;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created on 14/12/2015.
 */
@RunWith(AndroidJUnit4.class)
@Ignore
public  class TaskDetailsFragmentTest {

    private static final String TASK_DETAILS_TAG = "TASK_DETAILS_TAG";

    protected AppCompatActivity mActivity;

    private static MockWebServer server;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static  AuthHelper mockAuthHelper;

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    @BeforeClass
    public static void init() throws IOException {
        // https://github.com/square/okhttp/tree/master/mockwebserver
        server = new MockWebServer();
        server.start();

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

    @Test
    public void onLoad_shouldLoadData() throws IOException {
        // Given
        Task task = new Task("Task 0");
        StringWriter stringWriter = new StringWriter();
        MAPPER.writeValue(stringWriter, task);
        server.enqueue(new MockResponse().setBody(stringWriter.toString()).addHeader("Content-Type", "application/json; charset=utf-8"));

        // When
        activityTestRule.getActivity().showTasksDetails(0);

        // Then
        onView(withId(R.id.task_name)).check(matches(isDisplayed()));
    }

    @AfterClass
    public static void close() throws IOException {
        server.shutdown();
    }
}
