package com.alekseiivhsin.taskmanager.fragments.taskdetails;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.alekseiivhsin.taskmanager.App;
import com.alekseiivhsin.taskmanager.R;
import com.alekseiivhsin.taskmanager.SpicedActivity;
import com.alekseiivhsin.taskmanager.authentication.AuthHelper;
import com.alekseiivhsin.taskmanager.fragments.BaseSpicedInjectedFragmentTest;
import com.alekseiivhsin.taskmanager.helper.TaskBuilder;
import com.alekseiivhsin.taskmanager.ioc.AuthModule;
import com.alekseiivhsin.taskmanager.ioc.Graph;
import com.alekseiivhsin.taskmanager.ioc.MockedGraph;
import com.alekseiivhsin.taskmanager.ioc.StubNetworkModule;
import com.alekseiivhsin.taskmanager.models.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.octo.android.robospice.SpiceManager;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.mockwebserver.MockResponse;

import org.joda.time.DateTime;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.StringWriter;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created on 14/12/2015.
 */
@RunWith(AndroidJUnit4.class)
public class TaskDetailsFragmentTest extends BaseSpicedInjectedFragmentTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static AuthHelper mockAuthHelper;

    @Rule
    public ActivityTestRule<SpicedActivity> activityTestRule
            = new ActivityTestRule<>(SpicedActivity.class);

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

    @Test
    public void onLoad_shouldLoadData() throws IOException {
        // Given
        DateTime stubDeadlineTime = new DateTime();
        Task task = TaskBuilder.newTask()
                .setName("Task 0")
                .setDescription("Description")
                .setDeadline(stubDeadlineTime)
                .setStatus(0)
                .setPriority(0)
                .build();
        
        enqueueResponse(task);

        // When
        activityTestRule.getActivity().showTasksDetails(0);

        // Then

        // Checks task name
        onView(withId(R.id.task_name))
                .check(matches(
                        allOf(
                                isDisplayed(),
                                withText("Task 0"))));

        // Checks task description
        onView(withId(R.id.task_description))
                .check(matches(
                        allOf(
                                isDisplayed(),
                                withText("Description"))));

        // Checks task status
        onView(withId(R.id.task_status))
                .check(matches(
                        allOf(
                                isDisplayed(),
                                withText("New"))));

        // Checks task priority
        onView(withId(R.id.task_priority))
                .check(matches(
                        allOf(
                                isDisplayed(),
                                withText("Low"))));

        // Checks task deadline
        onView(withId(R.id.task_deadline))
                .check(matches(
                        allOf(
                                isDisplayed(),
                                withText(stubDeadlineTime.toString("MM/dd/yyyy HH:mm:ss")))));
    }

    @Test
    public void onLoad_shouldShowEditWhenLoggedAsLead() throws IOException {
        // Given
        Task task = TaskBuilder.newTask().setName("Task 0").build();
        enqueueResponse(task);

        when(mockAuthHelper.hasAccountRights(anyInt())).thenReturn(true);

        // When
        activityTestRule.getActivity().showTasksDetails(0);

        // Then
        onView(withId(R.id.edit_task)).check(matches(isDisplayed()));
    }

    @Test
    public void onLoad_shouldHideEditWhenLoggedAsMember() throws IOException {
        // Given
        Task task = TaskBuilder.newTask().setName("Task 0").build();
        enqueueResponse(task);

        when(mockAuthHelper.hasAccountRights(anyInt())).thenReturn(false);

        // When
        activityTestRule.getActivity().showTasksDetails(0);

        // Then
        onView(withId(R.id.edit_task)).check(matches(not(isDisplayed())));
    }

    private void enqueueResponse(Task task) throws IOException {
        StringWriter stringWriter = new StringWriter();
        MAPPER.writeValue(stringWriter, task);
        server.enqueue(new MockResponse().setBody(stringWriter.toString()));
    }
}
