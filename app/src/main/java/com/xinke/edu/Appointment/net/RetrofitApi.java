package com.xinke.edu.Appointment.net;

import com.xinke.edu.Appointment.entity.Result;
import com.xinke.edu.Appointment.entity.User;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitApi {
    String BaseUrl = "http://192.168.30.125:8080/wechat/";

    @POST("user/login")
    @FormUrlEncoded
    Observable<Result<User>> login(@Field("user_name") String user_name, @Field("password") String password);
}
