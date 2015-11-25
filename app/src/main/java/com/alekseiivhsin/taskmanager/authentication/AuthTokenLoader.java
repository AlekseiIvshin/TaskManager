package com.alekseiivhsin.taskmanager.authentication;

import android.content.AsyncTaskLoader;
import android.content.Context;

/**
 * Created on 25/11/2015.
 */
public class AuthTokenLoader extends AsyncTaskLoader<String> {

    public static final String signIn(Context context, String accountName, String password){
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    public AuthTokenLoader(Context context) {
        super(context);
    }

    @Override
    public String loadInBackground() {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

}
