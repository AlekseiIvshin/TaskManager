package com.alekseiivhsin.taskmanager.ioc;

import android.database.Cursor;
import android.support.v4.content.Loader;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;

/**
 * Created on 14/12/2015.
 */
@Module
public class MockTaskListModule extends TaskListModule {

    public Loader<Cursor> mockCursorLoader = Mockito.mock(Loader.class);

    @Provides
    public Loader<Cursor> provideCursorLoader() {
        return mockCursorLoader;
    }
}
