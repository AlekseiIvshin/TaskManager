package com.alekseiivhsin.taskmanager.fragments.taskdetails;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.alekseiivhsin.taskmanager.App;
import com.alekseiivhsin.taskmanager.R;
import com.alekseiivhsin.taskmanager.SpicedActivity;
import com.alekseiivhsin.taskmanager.authentication.AuthHelper;
import com.alekseiivhsin.taskmanager.fragments.BaseSpicedInjectedFragmentTest;
import com.alekseiivhsin.taskmanager.ioc.AuthModule;
import com.alekseiivhsin.taskmanager.ioc.Graph;
import com.alekseiivhsin.taskmanager.ioc.MockedGraph;
import com.alekseiivhsin.taskmanager.ioc.StubNetworkModule;
import com.alekseiivhsin.taskmanager.models.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.octo.android.robospice.SpiceManager;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.mockwebserver.MockResponse;

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
        Task task = new Task("Task 0");
        StringWriter stringWriter = new StringWriter();
        MAPPER.writeValue(stringWriter, task);
        server.enqueue(new MockResponse().setBody(stringWriter.toString()));

        // When
        activityTestRule.getActivity().showTasksDetails(0);

        // Then
        onView(withId(R.id.task_name)).check(matches(isDisplayed()));
    }
}
