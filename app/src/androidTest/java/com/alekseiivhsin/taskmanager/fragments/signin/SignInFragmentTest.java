package com.alekseiivhsin.taskmanager.fragments.signin;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;

import com.alekseiivhsin.taskmanager.App;
import com.alekseiivhsin.taskmanager.SpicedActivity;
import com.alekseiivhsin.taskmanager.R;
import com.alekseiivhsin.taskmanager.authentication.UserRights;
import com.alekseiivhsin.taskmanager.ioc.Graph;
import com.alekseiivhsin.taskmanager.ioc.MockedGraph;
import com.alekseiivhsin.taskmanager.ioc.StubNetworkModule;
import com.alekseiivhsin.taskmanager.network.responses.SignInResponse;
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

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.Matchers.not;

/**
 * Created on 18/12/2015.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
@Ignore
public class SignInFragmentTest {

    public static final String TEST_LEAD_LOGIN = "lead";
    public static final String TEST_LEAD_PASSWORD = "leadPass";

    private static final String TAG = SignInFragmentTest.class.getSimpleName();

    private static MockWebServer server;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Rule
    public ActivityTestRule<SpicedActivity> activityTestRule
            = new ActivityTestRule<>(SpicedActivity.class);

    @BeforeClass
    public static void init() throws IOException {
        Log.v(TAG, "BeforeClass");
        // https://github.com/square/okhttp/tree/master/mockwebserver
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
        activityTestRule.getActivity().showSignIn();
    }

    @AfterClass
    public static void close() throws IOException {
        server.shutdown();
    }


    @Test
    public void onLoad_checkOnAllNeedControllers() {
        onView(withId(R.id.input_login)).check(matches(isDisplayed()));
        onView(withId(R.id.input_password)).check(matches(isDisplayed()));
        onView(withId(R.id.sign_in)).check(matches(allOf(isDisplayed(), isEnabled())));
        onView(withId(R.id.error_message)).check(matches(not(isDisplayed())));
    }

    @Test
    public void onSubmit_showErrorWhenPasswordIsEmpty() throws IOException, InterruptedException {
        // Given
        server.enqueue(new MockResponse().setResponseCode(400).setHeader("Cache-Control", "no-cache"));

        // When
        onView(withId(R.id.input_login)).perform(typeText(TEST_LEAD_LOGIN));
        closeSoftKeyboard();
        onView(withId(R.id.sign_in)).perform(click());

        // Then
        onView(
                anyOf(
                        withText("Password should not be empty"),
                        withHint("Password should not be empty")))
                .check(matches(
                        isDisplayed()));
    }


    @Test
    public void onSubmit_showErrorWhenLoginIsEmpty() throws IOException, InterruptedException {
        // Given
        server.enqueue(new MockResponse().setResponseCode(400).setHeader("Cache-Control", "no-cache"));

        // When
        onView(withId(R.id.input_password)).perform(typeText(TEST_LEAD_PASSWORD));
        closeSoftKeyboard();
        onView(withId(R.id.sign_in)).perform(click());

        // Then
        onView(
                anyOf(
                        withText("User name should not be empty"),
                        withHint("User name should not be empty")))
                .check(matches(
                        isDisplayed()));
    }

    @Test
    public void onSubmit_showErrorMessageWhenCannotSignIn() throws IOException, InterruptedException {
        // Given
        server.enqueue(new MockResponse().setResponseCode(400).setHeader("Cache-Control", "no-cache"));

        // When
        onView(withId(R.id.input_login)).perform(typeText(TEST_LEAD_LOGIN));
        closeSoftKeyboard();
        onView(withId(R.id.input_password)).perform(typeText(TEST_LEAD_PASSWORD));
        closeSoftKeyboard();
        onView(withId(R.id.sign_in)).perform(click());

        // Then
        onView(withId(R.id.error_message))
                .check(matches(isDisplayed()));
    }

    @Test
    @Ignore
    public void onSubmit_checkNetworkCall() throws IOException, InterruptedException {
        // Given
        SignInResponse successResponse = new SignInResponse();
        successResponse.userRights = UserRights.NONE;
        successResponse.authToken = "testAuthToken";
        StringWriter stringWriter = new StringWriter();

        MAPPER.writeValue(stringWriter, successResponse);

        server.enqueue(new MockResponse().setBody(stringWriter.toString()).addHeader("Content-Type", "application/json; charset=utf-8"));

        // When
        onView(withId(R.id.input_login)).perform(typeText(TEST_LEAD_LOGIN));
        onView(withId(R.id.input_password)).perform(typeText(TEST_LEAD_PASSWORD));
        onView(withId(R.id.sign_in)).perform(click());
    }
}
