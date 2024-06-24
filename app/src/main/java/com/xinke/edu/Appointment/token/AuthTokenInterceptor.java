package com.xinke.edu.Appointment.token;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import com.release.alert.Alert;
import com.xinke.edu.Appointment.LoginActivity;

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
        Response response = chain.proceed(originalRequest);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String token = sharedPreferences.getString("token", "");

        // 检查是否为401状态码
        if (response.code() == 401) {
            //返回到登录页面
            redirectToLoginPage();
        }
        // 在使用完响应后关闭
        response.close();

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

    private void redirectToLoginPage() {
        // 在主线程中执行 UI 操作
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (context instanceof LoginActivity) {
                    // 如果当前上下文已经是 LoginActivity，直接启动登录页面
                    startLoginActivity();
                } else {
                    // 否则，创建一个 AlertDialog.Builder
                    new Alert(context)
                            .builder()
                            .setMsg("仿ios无标题弹窗")
                            .setPositiveButton(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startLoginActivity();
                                }
                            })
                            .show();
                }
            }
        });
    }

    // 启动登录页面的方法
    private void startLoginActivity() {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

}

