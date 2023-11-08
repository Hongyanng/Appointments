package com.xinke.edu.Appointment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.xinke.edu.Appointment.entity.Result;
import com.xinke.edu.Appointment.entity.User;
import com.xinke.edu.Appointment.net.RetrofitApi;

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
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * 登录页面的逻辑
 */
public class LoginActivity extends AppCompatActivity {
    /*注解绑定*/


    /*获取输入的用户名id*/
    @BindView(R.id.et_username)
    EditText usernameStr;


    /*获取输入的密码paws*/
    @BindView(R.id.et_pwd)
    EditText passwordStr;

    /*显示加载动画*/
    ProgressDialog progressDialog;


    /*获取身份*/
    //教师1
    @BindView(R.id.rb_teacher)
    RadioButton MyRadiorbTeacher;

    //学生2
    @BindView(R.id.rb_student)
    RadioButton MyRadioStudent;


    //初始化老师学生身份
    int authenticationStatus;

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


        if (MyRadiorbTeacher.isChecked()) {
            authenticationStatus = 1;
        } else if (MyRadioStudent.isChecked()) {
            authenticationStatus = 2;
        } else {
            Toast.makeText(LoginActivity.this, "请先选中用户身份", Toast.LENGTH_SHORT).show();
            return;
        }

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

            User user = new User();
            user.setUserName(username);
            user.setPassword(password);
            user.setAuthenticationStatus(authenticationStatus);


            //观察者模式用于判断是否服务器故障
            retrofitApi.login(user)
                    .subscribeOn(Schedulers.io())
                    .timeout(10, TimeUnit.SECONDS) // 设置超时时间为10秒
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Result>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            // 显示加载动画
                            progressDialog = ProgressDialog.show(LoginActivity.this, "请稍候", "正在登录...", true, false);

                        }

                        @Override
                        public void onNext(@NonNull Result result) {
                            /*是否成功请求*/
                            if (result.getCode() == Result.FAIL) {
                                //错误提示
                                Toast.makeText(LoginActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                Toast.makeText(LoginActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            // 发生错误时的操作
                            if (e instanceof TimeoutException) {
                                // 请求超时
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "服务器请求超时", Toast.LENGTH_SHORT).show();
                            } else {
                                // 其他服务器错误
                                Log.e("onError", e.toString());
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "服务器错误", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onComplete() {
                            // 完成时的操作
                            progressDialog.dismiss();
                        }
                    });

        }
    }


    /**
     * 跳转注册
     */
    @OnClick(R.id.register)
    public void Register(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }


}
