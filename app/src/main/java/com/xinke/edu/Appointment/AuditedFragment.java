package com.xinke.edu.Appointment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xinke.edu.Appointment.Adapter.CounselorAdapter;
import com.xinke.edu.Appointment.R;
import com.xinke.edu.Appointment.entity.Counselor;
import com.xinke.edu.Appointment.entity.Result;
import com.xinke.edu.Appointment.net.RetrofitApi;
import com.xinke.edu.Appointment.token.SPUtils;
import com.xinke.edu.Appointment.token.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuditedFragment extends Fragment {

    /*初始化视图*/
    RecyclerView recyclerView;

    CounselorAdapter counselorAdapter;

    /*token*/
    String token;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_audited,null,false);

        /*获取token*/
        token = (String) SPUtils.get(getContext(), "token", "");


        /*实例化适配器*/
        recyclerView = view.findViewById(R.id.recyclerview);

        counselorAdapter = new CounselorAdapter(new ArrayList<>());

        recyclerView.setAdapter(counselorAdapter);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        getData();

        return view;
    }

    public void getData(){
        /*获取网络*/
        Retrofit retrofit = new Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(RetrofitApi.BaseUrl)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        /*实例化*/
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);


        retrofitApi.getCounselor(token)
                .observeOn(Schedulers.io())
                .timeout(10, TimeUnit.SECONDS) // 设置超时时间为10秒
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Result<List<Counselor>>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull Result<List<Counselor>> result) {
                        // 更新适配器的数据集合
                        counselorAdapter.replaceData(result.getData());
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
