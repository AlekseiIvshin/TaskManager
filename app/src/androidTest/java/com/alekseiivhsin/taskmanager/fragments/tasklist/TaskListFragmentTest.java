package com.alekseiivhsin.taskmanager.fragments.tasklist;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;

import com.alekseiivhsin.taskmanager.App;
import com.alekseiivhsin.taskmanager.R;
import com.alekseiivhsin.taskmanager.SpicedActivity;
import com.alekseiivhsin.taskmanager.authentication.AuthHelper;
import com.alekseiivhsin.taskmanager.authentication.UserRights;
import com.alekseiivhsin.taskmanager.fragments.BaseSpicedInjectedFragmentTest;
import com.alekseiivhsin.taskmanager.helper.TaskBuilder;
import com.alekseiivhsin.taskmanager.ioc.AuthModule;
import com.alekseiivhsin.taskmanager.ioc.Graph;
import com.alekseiivhsin.taskmanager.ioc.MockedGraph;
import com.alekseiivhsin.taskmanager.ioc.StubNetworkModule;
import com.alekseiivhsin.taskmanager.models.Task;
import com.alekseiivhsin.taskmanager.network.responses.PoolTaskListResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.octo.android.robospice.SpiceManager;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.mockwebserver.MockResponse;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created on 21/12/2015.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class TaskListFragmentTest extends BaseSpicedInjectedFragmentTest {

    @Rule
    public ActivityTestRule<SpicedActivity> activityTestRule = new ActivityTestRule<>(SpicedActivity.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private AuthHelper mockAuthHelper;

    @Test
    public void onLoad_shouldLoadTaskList() throws IOException {
        // Given
        PoolTaskListResponse poolTaskListResponse = generateTasksList(3, 3);

        enqueueResponse(poolTaskListResponse);

        when(mockAuthHelper.getAuthToken()).thenReturn("STUB_AUTH_TOKEN");
        when(mockAuthHelper.hasAccountRights(anyInt())).thenReturn(true);

        // When
        activityTestRule.getActivity().showTasksList();

        // Then
        onView(withId(R.id.fragment_container)).check(matches(isDisplayed()));
        for (Task task : poolTaskListResponse.assignedTasks) {
            onView(withText(task.name)).check(matches(isDisplayed()));
        }

        for (Task task : poolTaskListResponse.unassignedTasks) {
            onView(withText(task.name)).check(matches(isDisplayed()));
        }
    }

    @Test
    public void onLoad_shouldHideAddingTaskWhenLoggedAsMember() throws IOException {
        // Given
        enqueueResponse(generateTasksList(2, 2));

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
        enqueueResponse(generateTasksList(2,2));

        when(mockAuthHelper.hasAccountRights(UserRights.CAN_CREATE_TASK)).thenReturn(true);
        when(mockAuthHelper.getAuthToken()).thenReturn("STUB_AUTH_TOKEN");

        // When
        activityTestRule.getActivity().showTasksList();

        // Then
        onView(withId(R.id.add_new_task)).check(matches(isDisplayed()));
    }

    @Test
    public void onLoad_showAssignedHeaderWhenAssignedTaskListIsNotEmpty() throws IOException {
        // Given
        PoolTaskListResponse poolTaskListResponse = generateTasksList(3, 0);

        enqueueResponse(poolTaskListResponse);

        when(mockAuthHelper.getAuthToken()).thenReturn("STUB_AUTH_TOKEN");
        when(mockAuthHelper.hasAccountRights(anyInt())).thenReturn(true);

        // When
        activityTestRule.getActivity().showTasksList();

        // Then
        onView(withText(R.string.header_tasks_assigned)).check(matches(isDisplayed()));
    }

    @Test
    public void onLoad_hideAssignedHeaderWhenAssignedTaskListIsEmpty() throws IOException {
        // Given
        PoolTaskListResponse poolTaskListResponse = generateTasksList(0, 3);

        enqueueResponse(poolTaskListResponse);

        when(mockAuthHelper.getAuthToken()).thenReturn("STUB_AUTH_TOKEN");
        when(mockAuthHelper.hasAccountRights(anyInt())).thenReturn(true);

        // When
        activityTestRule.getActivity().showTasksList();

        // Then
        onView(withText(R.string.header_tasks_assigned)).check(doesNotExist());
    }

    @Test
    public void onLoad_showUnassignedHeaderWhenAssignedTaskListIsNotEmpty() throws IOException {
        // Given
        PoolTaskListResponse poolTaskListResponse = generateTasksList(0, 3);

        enqueueResponse(poolTaskListResponse);

        when(mockAuthHelper.getAuthToken()).thenReturn("STUB_AUTH_TOKEN");
        when(mockAuthHelper.hasAccountRights(anyInt())).thenReturn(true);

        // When
        activityTestRule.getActivity().showTasksList();

        // Then
        onView(withText(R.string.header_tasks_unassigned)).check(matches(isDisplayed()));
    }

    @Test
    public void onLoad_hideUnassignedHeaderWhenAssignedTaskListIsEmpty() throws IOException {
        // Given
        PoolTaskListResponse poolTaskListResponse = generateTasksList(3, 0);

        enqueueResponse(poolTaskListResponse);

        when(mockAuthHelper.getAuthToken()).thenReturn("STUB_AUTH_TOKEN");
        when(mockAuthHelper.hasAccountRights(anyInt())).thenReturn(true);

        // When
        activityTestRule.getActivity().showTasksList();

        // Then
        onView(withText(R.string.header_tasks_unassigned)).check(doesNotExist());
    }

    @Override
    protected SpiceManager getSpiceManager() {
        return activityTestRule.getActivity().spiceManager;
    }

    @Override
    protected void onInitObjectGraph() {
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

    private void enqueueResponse(PoolTaskListResponse poolTaskListResponse) throws IOException {
        StringWriter stringWriter = new StringWriter();

        MAPPER.writeValue(stringWriter, poolTaskListResponse);

        server.enqueue(new MockResponse().setBody(stringWriter.toString()));
        Log.v(TaskListFragmentTest.class.getSimpleName(), "Enqueued response: " + stringWriter.toString());
    }

    private PoolTaskListResponse generateTasksList(int assignedCount, int unassignedCount) {
        PoolTaskListResponse poolTaskListResponse = new PoolTaskListResponse();
        poolTaskListResponse.assignedTasks = new ArrayList<>();
        poolTaskListResponse.unassignedTasks = new ArrayList<>();

        for (int i = 0; i < assignedCount; i++) {
            poolTaskListResponse.assignedTasks.add(TaskBuilder.newTask().setName("Task " + i).build());
        }

        for (int i = assignedCount; i < unassignedCount + assignedCount; i++) {
            poolTaskListResponse.unassignedTasks.add(TaskBuilder.newTask().setName("Task " + i).build());
        }

        return poolTaskListResponse;
    }
}
