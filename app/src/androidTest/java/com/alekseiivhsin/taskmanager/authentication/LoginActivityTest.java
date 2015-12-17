package com.alekseiivhsin.taskmanager.authentication;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.alekseiivhsin.taskmanager.App;
import com.alekseiivhsin.taskmanager.R;
import com.alekseiivhsin.taskmanager.ioc.MockAuthModule;
import com.alekseiivhsin.taskmanager.ioc.MockedGraph;
import com.alekseiivhsin.taskmanager.network.models.LoginResponse;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withInputType;
import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD;
import static org.hamcrest.core.AllOf.allOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created on 07/12/2015.
 */
@RunWith(AndroidJUnit4.class)
@Ignore
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    MockAuthModule mMockAuthModule;

    @Before
    public void setUp() {
        mMockAuthModule = new MockAuthModule(mActivityTestRule.getActivity().getApplication());
        ((App) mActivityTestRule.getActivity().getApplication())
                .setObjectGraph(MockedGraph.MockedInitializer.init(mMockAuthModule));
    }

    @Test
    public void layoutOnLoad_shouldContainsLoginAndPasswordInputs() {
        onView(withId(R.id.input_login))
                .check(matches(isDisplayed()));

        onView(withId(R.id.input_password))
                .check(matches(allOf(isDisplayed(), withInputType(TYPE_TEXT_VARIATION_PASSWORD | TYPE_CLASS_TEXT))));
    }

    @Test
    public void loginClick_shouldNotRequestAuthWhenPasswordIsEmpty() {
        // Given
        LoginResponse stubLoginResponse = new LoginResponse();
        stubLoginResponse.authToken = "STUB!";
        when(mMockAuthModule.mockAuthService.login(anyString(), anyString(), anyString())).thenReturn(stubLoginResponse);

        // When
        onView(withId(R.id.input_login)).perform(typeText("LoginWithEmptyPassword"));
        onView(withId(R.id.login)).perform(click());

        // Then
        verify(mMockAuthModule.mockAuthService, times(0)).login(anyString(), anyString(), anyString());
    }
}
