package com.alekseiivhsin.taskmanager.ioc;


import android.database.Cursor;
import android.support.v4.content.Loader;

import dagger.Module;
import dagger.Provides;

/**
 * Created on 14/12/2015.
 */
@Module
public class TaskListModule {

    @Provides
    public Loader<Cursor> provideCursorLoader(){
        throw new UnsupportedOperationException("Not implemented!");
    }
}
