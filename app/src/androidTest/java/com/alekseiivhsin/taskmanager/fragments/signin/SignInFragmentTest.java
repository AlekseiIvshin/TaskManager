package com.alekseiivhsin.taskmanager.fragments.signin;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.alekseiivhsin.taskmanager.App;
import com.alekseiivhsin.taskmanager.MainActivity;
import com.alekseiivhsin.taskmanager.R;
import com.alekseiivhsin.taskmanager.authentication.UserRights;
import com.alekseiivhsin.taskmanager.ioc.AuthModule;
import com.alekseiivhsin.taskmanager.ioc.Graph;
import com.alekseiivhsin.taskmanager.ioc.MockedGraph;
import com.alekseiivhsin.taskmanager.ioc.NetworkModule;
import com.alekseiivhsin.taskmanager.network.AuthApiService;
import com.alekseiivhsin.taskmanager.network.model.SignInResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.StringWriter;

import retrofit.Retrofit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.allOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created on 18/12/2015.
 */
@RunWith(AndroidJUnit4.class)
public class SignInFragmentTest {

    public static final String TEST_LEAD_LOGIN = "lead";
    public static final String TEST_LEAD_PASSWORD = "leadPass";

    private static final String TAG = SignInFragmentTest.class.getSimpleName();

    private static MockWebServer server;
    private static NetworkModule mMockNetworkModule;
    private static AuthModule mMockAuthModule;
    private static AuthApiService mMockAuthApiService;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    @BeforeClass
    public static void init() throws IOException {
//        Log.v(TAG, "BeforeClass");
//        // https://github.com/square/okhttp/tree/master/mockwebserver
//        server = new MockWebServer();
//
//        server.start();
//
//        HttpUrl baseUrl = server.url("");
//        Log.v(TAG, "Create MockWebServer on " + baseUrl.url());
//
//        mMockNetworkModule = mock(NetworkModule.class);
//        when(mMockNetworkModule.provideRetrofit())
//                .thenReturn(new Retrofit.Builder()
//                        .baseUrl(baseUrl)
//                        .build());
//
//        mMockAuthModule = mock(AuthModule.class);
//        mMockAuthApiService = mock(AuthApiService.class);
//        when(mMockAuthModule).thenReturn(mMockAuthModule);
//
//        Graph mockedGraph = MockedGraph.MockGraphBuilder.begin()
//                .setMockNetworkModule(mMockNetworkModule)
//                .setMockAuthModule(mMockAuthModule)
//                .build();
//        App.getInstance()
//                .setObjectGraph(mockedGraph);
    }

    @Before
    public void setUp() {
        activityTestRule.getActivity().showSignIn();
    }

    @Test
    public void onLoad_checkOnAllNeedControllers() {
        onView(withId(R.id.input_login)).check(matches(isDisplayed()));
        onView(withId(R.id.input_password)).check(matches(isDisplayed()));
        onView(withId(R.id.sign_in)).check(matches(allOf(isDisplayed(), isEnabled())));
    }

    @Test
    public void onSubmit_checkNetworkCall() throws IOException, InterruptedException {
        // Given
        ViewInteraction signIn = onView(withId(R.id.sign_in));
        ViewInteraction loginInput = onView(withId(R.id.input_login));
        ViewInteraction passwordInput = onView(withId(R.id.input_password));

        SignInResponse successResponse = new SignInResponse();
        successResponse.userRights = UserRights.NONE;
        successResponse.authToken = "testAuthToken";
        StringWriter stringWriter = new StringWriter();

        MAPPER.writeValue(stringWriter, successResponse);

        server.enqueue(new MockResponse().setBody(stringWriter.toString()));

        // When
        loginInput.perform(typeText(TEST_LEAD_LOGIN));
        passwordInput.perform(typeText(TEST_LEAD_PASSWORD));
        signIn.perform(click());

        // Then
        RecordedRequest signInRecReq = server.takeRequest();
        assertEquals("Api path should be equals", "/api/signin", signInRecReq.getPath());

    }

    @AfterClass
    public static void close() throws IOException {
        server.shutdown();
    }
}
