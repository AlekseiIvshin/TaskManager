package com.alekseiivhsin.taskmanager.authentication;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.alekseiivhsin.taskmanager.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withInputType;
import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created on 07/12/2015.
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void layoutOnLoad_shouldContainsLoginAndPasswordInputs() {
        onView(withId(R.id.input_login))
                .check(matches(isDisplayed()));

        onView(withId(R.id.input_password))
                .check(matches(allOf(isDisplayed(), withInputType(TYPE_TEXT_VARIATION_PASSWORD | TYPE_CLASS_TEXT))));
    }
}
