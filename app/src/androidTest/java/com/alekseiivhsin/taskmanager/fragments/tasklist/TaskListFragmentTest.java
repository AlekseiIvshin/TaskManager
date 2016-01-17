package com.alekseiivhsin.taskmanager.fragments.tasklist;

import android.accounts.Account;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.alekseiivhsin.taskmanager.App;
import com.alekseiivhsin.taskmanager.SpicedActivity;
import com.alekseiivhsin.taskmanager.R;
import com.alekseiivhsin.taskmanager.authentication.AuthHelper;
import com.alekseiivhsin.taskmanager.authentication.UserRights;
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
import static org.hamcrest.CoreMatchers.not;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created on 21/12/2015.
 */
@RunWith(AndroidJUnit4.class)
public class TaskListFragmentTest {

    public static final String TAG = TaskListFragmentTest.class.getSimpleName();

    @Rule
    public ActivityTestRule<SpicedActivity> activityTestRule = new ActivityTestRule<>(SpicedActivity.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final String MOCK_TASKLIST = "{\"tasks\":[{\"name\":\"Task 1\"},{\"name\":\"Task 2\"},{\"name\":\"Task 3\"}]}";

    protected int POOL_LEAD_RIGHTS = UserRights.CAN_VIEW_TASK | UserRights.CAN_UPDATE_TASK | UserRights.CAN_CREATE_TASK | UserRights.CAN_CLOSE_TASK;
    protected int POOL_MEMBER_RIGHTS = UserRights.CAN_VIEW_TASK | UserRights.CAN_UPDATE_TASK;

    private static MockWebServer server;

    private AuthHelper mockAuthHelper;

    private RobospiceIdlingResource robospiceIdlingResource;


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


        robospiceIdlingResource = new RobospiceIdlingResource(getSpiceManager());
        Espresso.registerIdlingResources(robospiceIdlingResource);

    }

    @After
    public void tearDown(){
        Espresso.unregisterIdlingResources(robospiceIdlingResource);
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

        server.enqueue(new MockResponse().setBody(stringWriter.toString()));

        when(mockAuthHelper.getAuthToken()).thenReturn("STUB_AUTH_TOKEN");
        when(mockAuthHelper.hasAccountRights(anyInt())).thenReturn(true);

        // When
        activityTestRule.getActivity().showTasksList();

        // Then
        onView(withId(R.id.fragment_container)).check(matches(isDisplayed()));
        for(Task task: taskListResponse.taskList){
            onView(withText(task.name)).check(matches(isDisplayed()));
        }
    }

    @Test
    public void onLoad_shouldHideAddingTaskWhenLoggedAsMember() throws IOException {
        // Given
        server.enqueue(new MockResponse().setBody(MOCK_TASKLIST));

        when(mockAuthHelper.hasAccountRights(UserRights.CAN_CREATE_TASK)).thenReturn(false);
        when(mockAuthHelper.getAuthToken()).thenReturn("STUB_AUTH_TOKEN");

        // When
        activityTestRule.getActivity().showTasksList();

        // Then
        onView(withId(R.id.add_new_task)).check(matches(not(isDisplayed())));
    }

    @Test
    public void onLoad_shouldShowAddingTaskWhenLoggedAsLead() throws IOException {
        // Given
        server.enqueue(new MockResponse().setBody(MOCK_TASKLIST));

        when(mockAuthHelper.hasAccountRights(UserRights.CAN_CREATE_TASK)).thenReturn(true);
        when(mockAuthHelper.getAuthToken()).thenReturn("STUB_AUTH_TOKEN");

        // When
        activityTestRule.getActivity().showTasksList();

        // Then
        onView(withId(R.id.add_new_task)).check(matches(isDisplayed()));
    }

    private SpiceManager getSpiceManager(){
        return activityTestRule.getActivity().spiceManager;
    }

}
