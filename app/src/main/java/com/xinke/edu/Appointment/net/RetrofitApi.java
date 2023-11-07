package com.xinke.edu.Appointment.net;

import com.xinke.edu.Appointment.entity.Result;
import com.xinke.edu.Appointment.entity.User;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitApi {
    String BaseUrl = "http://47.120.33.193:8081/";


    /**
     * 登录的请求接口
     */
    @POST("user/login")
    @Headers({"Content-Type: application/json","Accept: application/json"})
    Observable<Result> login(@Body User user);
}
