package com.xinke.edu.Appointment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.xinke.edu.Appointment.entity.Result;
import com.xinke.edu.Appointment.entity.User;
import com.xinke.edu.Appointment.net.RetrofitApi;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * 注册页面的逻辑
 */
public class LoginActivity extends AppCompatActivity {
    /*注解绑定*/


    /*获取输入的用户名id*/
    @BindView(R.id.et_username)
    EditText usernameStr;


    /*获取输入的密码paws*/
    @BindView(R.id.et_pwd)
    EditText passwordStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this); // 绑定视图
    }


    /**
     * 登录按钮的逻辑
     */
    @OnClick(R.id.btn_login)
    public void Loign(View view) {
        String username = usernameStr.getText().toString();
        String password = passwordStr.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            // 输入的账号或密码为空，显示错误消息或采取其他措施
            Toast.makeText(this, "账号和密码不能为空", Toast.LENGTH_SHORT).show();
        } else {
            //发送请求
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(RetrofitApi.BaseUrl)
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
//实例化对象接受服务器的信息
            RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
//观察者模式用于判断是否服务器故障
            retrofitApi.login(username, password)
                    .subscribeOn(Schedulers.io())
                    .timeout(10, TimeUnit.SECONDS) // 设置超时时间为10秒
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Result<User>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull Result<User> userResult) {

                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        }
    }

}
