package com.alekseiivhsin.taskmanager.authentication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AuthenticationService extends Service {

    private MyAuthenticator mAuthenticator;

    public AuthenticationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAuthenticator = new MyAuthenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }

}
