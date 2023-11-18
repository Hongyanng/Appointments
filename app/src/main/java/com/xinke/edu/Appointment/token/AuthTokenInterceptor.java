package com.xinke.edu.Appointment.token;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthTokenInterceptor implements Interceptor {

    private Context context;

    public AuthTokenInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String token = sharedPreferences.getString("token", "");
        Log.i("token",token);

        // 如果token为空，则不修改请求
        if (token.isEmpty()) {
            return chain.proceed(originalRequest);
        }

        // 在原请求的基础上，新建一个请求，并添加token
        Request newRequest = originalRequest.newBuilder()
                .header("token", "Bearer " + token)
                .build();

        return chain.proceed(newRequest);
    }
}

