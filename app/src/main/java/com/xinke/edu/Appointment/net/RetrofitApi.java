package com.xinke.edu.Appointment.net;

import com.xinke.edu.Appointment.entity.Classrooms;
import com.xinke.edu.Appointment.entity.Reservetion;
import com.xinke.edu.Appointment.entity.Result;
import com.xinke.edu.Appointment.entity.User;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitApi {
    String BaseUrl = "http://47.120.33.193:8081";

    /**
     * 登录接口
     */
    @POST("user/login")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<Result<User>> login(@Body User user);

    /**
     * 注册接口
     */
    @POST("user/register")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<Result> register(@Body User user);


    /**
     * 查询教室的接口
     */
    @POST("user/searchAvailableClassrooms")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<Result<List<Classrooms>>> queryclassroom(@Body Classrooms classrooms);

    /**
     * 预约教室的接口
     */
    @POST("user/reservation")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<Result> reservation(@Body Reservetion reservation);


}
