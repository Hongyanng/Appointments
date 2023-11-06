package com.xinke.edu.Appointment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xinke.edu.Appointment.entity.Result;
import com.xinke.edu.Appointment.entity.User;
import com.xinke.edu.Appointment.net.RetrofitApi;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 *注册页面
 */
public class RegisterActivity extends AppCompatActivity {
    /*注解绑定*/


    /*获取输入的用户名id*/
    @BindView(R.id.et_username)
    EditText usernameStr;


    /*获取输入的密码paws*/
    @BindView(R.id.et_pwd)
    EditText passwordStr;

    /*获取输入的姓名*/
    @BindView(R.id.et_full_name)
    EditText fullnameStr;

    /*获取输入的邮箱地址*/
    @BindView(R.id.email_address)
    EditText emailStr;

    /*获取输入的手机号码*/
    @BindView(R.id.phone_number)
    EditText phoneStr;


    /*获取辅导员*/
    @BindView(R.id.teacher)
    Spinner teacher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this); // 绑定视图
    }

    @OnClick(R.id.btn_register)
    public void register(){
        String fullname = fullnameStr.getText().toString();
        String username = usernameStr.getText().toString();
        String password = passwordStr.getText().toString();
        String email = emailStr.getText().toString();
        String phone = phoneStr.getText().toString();

        if (username.isEmpty() && password.isEmpty()) {
            // 输入的账号或密码为空，显示错误消息或采取其他措施
            Toast.makeText(this, "账号和密码不能为空", Toast.LENGTH_SHORT).show();
        }else {
            //发送请求
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(RetrofitApi.BaseUrl)
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
            //实例化对象接受服务器的信息
            RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);

            User user = new User();
            user.setFull_name(fullname);
            user.setUser_name(username);
            user.setPassword(password);
            user.setEmail_address(email);
            user.setPhone_number(phone);



            retrofitApi.register(user)
                    .subscribeOn(Schedulers.io())
                    .timeout(10, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Result>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull Result result) {
                            if (result.getCode() == Result.FAIL){
                                Toast.makeText(RegisterActivity.this,result.getMsg(),Toast.LENGTH_SHORT).show();
                                return;
                            }else {
                                Toast.makeText(RegisterActivity.this,result.getMsg(),Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }
}
