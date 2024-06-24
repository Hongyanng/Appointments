package com.xinke.edu.Appointment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.release.alert.Alert;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.xinke.edu.Appointment.Adapter.MyreservationAdapter;
import com.xinke.edu.Appointment.LoaringDialog.LoadingDialog;
import com.xinke.edu.Appointment.entity.MyReservation;
import com.xinke.edu.Appointment.entity.Result;
import com.xinke.edu.Appointment.net.RetrofitApi;
import com.xinke.edu.Appointment.token.AuthTokenInterceptor;
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

    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    String settoken;


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

        GetMacerationData(settoken);


        // 设置下拉刷新监听器
        /*下拉刷新样式*/
        RefreshLayout refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                GetMacerationData(settoken);

            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });

    }


    @Override
    protected void onResume() {
        // 显示加载动画
        LoadingDialog.getInstance(MyreservationActivity.this).show();
        super.onResume();
        GetMacerationData(settoken);
    }

    /*返回按钮点击事件*/
    @OnClick(R.id.back)
    public void GetBack() {
        finish();
    }


    //后端返回数据的方法
    public void GetMacerationData(String token) {
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
                        LoadingDialog.getInstance(MyreservationActivity.this).show();//显示
                    }

                    @Override
                    public void onNext(@NonNull Result<List<MyReservation>> listResult) {
                        // 处理服务器返回的数据
                        if (listResult.getCode() == 200) {
                            List<MyReservation> myReservations = listResult.getData();
                            // 更新适配器的数据集合
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.replaceData(listResult.getData());

                                }
                            });

                        } else {
                            new Alert(MyreservationActivity.this)
                                    .builder()
                                    .setMsg(listResult.getMsg())
                                    .setPositiveButton(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                        }
                                    })
                                    .show();


                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        // 发生错误时的操作
                        if (e instanceof TimeoutException) {
                            // 请求超时
                            LoadingDialog.getInstance(MyreservationActivity.this).hide();//隐藏
                            new Alert(MyreservationActivity.this)
                                    .builder()
                                    .setMsg("请求过快,请稍后再试试吧~")
                                    .setPositiveButton(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                        }
                                    })
                                    .show();
                        } else {
                            // 其他服务器错误
                            Log.e("onError", e.toString());
                            new Alert(MyreservationActivity.this)
                                    .builder()
                                    .setMsg("服务器连接超时,请联系管理员!")
                                    .setPositiveButton(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                        }
                                    })
                                    .show();
                        }
                    }

                    @Override
                    public void onComplete() {
                        // 完成时的操作
                        LoadingDialog.getInstance(MyreservationActivity.this).hide();//隐藏
                    }
                });
    }


    /*okhttp拦截器*/
    private OkHttpClient.Builder getClient() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(15, TimeUnit.SECONDS);

        // 添加 token 拦截器
        httpClientBuilder.addNetworkInterceptor(new TokenHeaderInterceptor(this));

        // 添加状态码拦截器
        httpClientBuilder.addInterceptor(new AuthTokenInterceptor(this));

        return httpClientBuilder;
    }
}
