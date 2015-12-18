package com.alekseiivhsin.taskmanager.ioc;

import android.app.Application;

import com.alekseiivhsin.taskmanager.authentication.AuthHelper;
import com.alekseiivhsin.taskmanager.authentication.UserRights;
import com.alekseiivhsin.taskmanager.network.model.SignInResponse;
import com.alekseiivhsin.taskmanager.network.AuthApiService;

import dagger.Module;
import dagger.Provides;
import retrofit.Retrofit;
import retrofit.http.Query;

/**
 * Created by Aleksei Ivshin
 * on 20.09.2015.
 */
@Module
public class AuthModule {

    private final Application mApp;

    public AuthModule(Application application) {
        mApp = application;
    }

    @Provides
    public AuthApiService provideAuthService(Retrofit retrofit) {
//        return retrofit.create(AuthService.class);

        return Stub.getStubService();
    }

    @Provides
    public AuthHelper provideAuthHelper() {
        return AuthHelper.get(mApp.getApplicationContext());
    }

    private static class Stub {
        protected static int POOL_LEAD_RIGHTS = UserRights.CAN_VIEW_TASK | UserRights.CAN_UPDATE_TASK | UserRights.CAN_CREATE_TASK | UserRights.CAN_CLOSE_TASK;
        protected static int POOL_MEMBER_RIGHTS = UserRights.CAN_VIEW_TASK | UserRights.CAN_UPDATE_TASK;

        public static AuthApiService getStubService() {
            return new AuthApiService() {
                @Override
                public SignInResponse login(@Query("username") String username, @Query("password") String password, @Query("accountType") String accountType) {
                    SignInResponse response = new SignInResponse();
                    response.authToken = String.valueOf(username.hashCode() + accountType.hashCode());
                    response.userRights = getRights(username);
                    return response;
                }
            };
        }

        public static int getRights(String userName) {
            if ("lead".equalsIgnoreCase(userName)) {
                return POOL_LEAD_RIGHTS;
            } else {
                return POOL_MEMBER_RIGHTS;
            }
        }
    }

}
