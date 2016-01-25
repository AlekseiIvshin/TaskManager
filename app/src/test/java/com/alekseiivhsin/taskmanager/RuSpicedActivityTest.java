package com.alekseiivhsin.taskmanager;

import android.support.design.widget.NavigationView;
import android.view.MenuItem;

import com.alekseiivhsin.taskmanager.shadows.MyRobolectricRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Aleksei Ivshin
 * on 25.01.2016.
 */
@RunWith(MyRobolectricRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml",
        sdk = 19,
        application = App.class,
        constants = BuildConfig.class,
        qualifiers = "ru")
public class RuSpicedActivityTest {

    SpicedActivity activity;

    @Before
    public void setUp() {
        activity = Robolectric.setupActivity(SpicedActivity.class);
    }

    @Test
    public void onLoad_shouldHasRightNavigationTitles() {
        // Given
        NavigationView navigationView = (NavigationView) activity.findViewById(R.id.navigation_drawer);

        MenuItem taskListPoolItem = navigationView.getMenu().getItem(0);
        MenuItem taskListUserItem = navigationView.getMenu().getItem(1);
        MenuItem poolMembersItem = navigationView.getMenu().getItem(2);
        MenuItem logoutItem = navigationView.getMenu().getItem(3);

        // Then
        assertThat(taskListPoolItem.getTitle().toString(), is(equalTo("Задачи группы")));
        assertThat(taskListUserItem.getTitle().toString(), is(equalTo("Мои задачи")));
        assertThat(poolMembersItem.getTitle().toString(), is(equalTo("Группа")));
        assertThat(logoutItem.getTitle().toString(), is(equalTo("Выход")));
    }
}
