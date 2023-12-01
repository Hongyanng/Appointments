package com.xinke.edu.Appointment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xinke.edu.Appointment.Adapter.MyreservationAdapter;
import com.xinke.edu.Appointment.entity.MyReservation;
import com.xinke.edu.Appointment.entity.Result;
import com.xinke.edu.Appointment.net.RetrofitApi;
import com.xinke.edu.Appointment.token.SPUtils;
import com.xinke.edu.Appointment.token.TokenHeaderInterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyreservationActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    String settoken;

    /*加载动画*/
    ProgressDialog progressDialog;

    MyReservation myReservation;

    MyreservationAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myreservation);
        ButterKnife.bind(this); // 绑定视图

        /*获取token*/
        settoken = (String) SPUtils.get(this, "token", "");

        /*初始化视图*/
        /*把布局划分成左右两边的代码，后面的2就是分成几份*/
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new MyreservationAdapter(new ArrayList<>(), MyreservationActivity.this);

        /*把实例化的帮助类类传入布局*/
        recyclerView.setAdapter(adapter);

        /*实例化实体类*/
        myReservation = new MyReservation();
        myReservation.setToken(settoken);

        GetMyreservationData(settoken);


    }


    /*返回按钮点击事件*/
    @OnClick(R.id.back)
    public void GetBack() {
        finish();
    }


    //后端返回数据的方法
    public void GetMyreservationData(String token) {
        /*获取网络*/
        Retrofit retrofit = new Retrofit
                .Builder()
                .client(getClient().build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(RetrofitApi.BaseUrl)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        /*实例化*/
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);


        retrofitApi.myReservation(token)
                .observeOn(Schedulers.io())
                .timeout(10, TimeUnit.SECONDS)// 设置超时时间为10秒
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Result<List<MyReservation>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        // 显示加载动画
                        progressDialog = ProgressDialog.show(MyreservationActivity.this, "请稍候", "正在疯狂查询中...", true, false);

                    }

                    @Override
                    public void onNext(@NonNull Result<List<MyReservation>> listResult) {
                        // 处理服务器返回的数据
                        if (listResult.getCode() == 200) {
                            List<MyReservation> myReservations = listResult.getData();
                            // 更新适配器的数据集合
                            adapter.replaceData(listResult.getData());

                        } else {
                            /*输出错误信息*/
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        // 发生错误时的操作
                        if (e instanceof TimeoutException) {
                            // 请求超时
                            progressDialog.dismiss();
                            Toast.makeText(MyreservationActivity.this, "服务器请求超时,请检查网络连接！", Toast.LENGTH_SHORT).show();
                        } else {
                            // 其他服务器错误
                            Log.e("onError", e.toString());
                            progressDialog.dismiss();
                            Toast.makeText(MyreservationActivity.this, "服务器错误", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onComplete() {
                        progressDialog.dismiss();

                    }
                });
    }


    /*okhttp拦截器*/
    private OkHttpClient.Builder getClient() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(15, TimeUnit.SECONDS);
        httpClientBuilder.addNetworkInterceptor(new TokenHeaderInterceptor(this));
        return httpClientBuilder;
    }
}
