package com.xinke.edu.Appointment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.xinke.edu.Appointment.entity.Result;
import com.xinke.edu.Appointment.entity.User;
import com.xinke.edu.Appointment.net.RetrofitApi;
import com.xinke.edu.Appointment.token.AuthTokenInterceptor;
import com.xinke.edu.Appointment.token.SPUtils;
import com.xinke.edu.Appointment.token.SharedPreferencesUtils;

import java.io.IOException;
import java.util.Date;
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

    /*获取单选框*/
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;

    //初始化老师学生身份
    int authenticationStatus;


    // 在SharedPreferences中为token声明一个常量键
    private static final String PREF_KEY_TOKEN = "token";


    User user;

    ImageView memorySelector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this); // 绑定视图

        // 获取记住密码的状态，默认为false
        boolean rememberPassword = (Boolean) SharedPreferencesUtils.getParam(LoginActivity.this, "UserId", false);

        // 设置记住密码的选择器状态
        memorySelector = findViewById(R.id.rgb);
        memorySelector.setSelected(rememberPassword);

        // 设置记住密码选择器的点击事件
        memorySelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isSelected = !memorySelector.isSelected();
                memorySelector.setSelected(isSelected);

                // 保存记住密码的状态
                SharedPreferencesUtils.setParam(LoginActivity.this, "UserId", isSelected);
            }
        });

        // 如果记住密码状态为true，则自动填充用户名和密码
        if (rememberPassword) {
            // 获取到原来记住的值
            String savedUsername = (String) SharedPreferencesUtils.getParam(LoginActivity.this, "user", "");
            String savedPassword = (String) SharedPreferencesUtils.getParam(LoginActivity.this, "pass", "");
            usernameStr.setText(savedUsername);
            passwordStr.setText(savedPassword);
        }



    }

    private void unshowCounselor() {
        findViewById(R.id.menu_notification).setVisibility(View.GONE);
    }


    private void showCounselor() {
        findViewById(R.id.menu_notification).setVisibility(View.VISIBLE);
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

            /*token拦截器*/
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new AuthTokenInterceptor(this))
                    .build();


            //发送请求
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(RetrofitApi.BaseUrl)
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
            //实例化对象接受服务器的信息
            RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);

            user = new User();
            user.setUserName(username);
            user.setPassword(password);
            user.setAuthenticationStatus(authenticationStatus);


            //观察者模式用于判断是否服务器故障
            retrofitApi.login(user)
                    .observeOn(Schedulers.io())
                    .timeout(10, TimeUnit.SECONDS) // 设置超时时间为10秒
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Result<User>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            // 显示加载动画
                            progressDialog = ProgressDialog.show(LoginActivity.this, "请稍候", "正在登录...", true, false);

                        }

                        @Override
                        public void onNext(@NonNull Result<User> result) {
                            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                                    // 根据选择的身份来隐藏或显示相应的组件
                                    switch (checkedId) {
                                        case R.id.rb_student:
                                            // 如果选择了学生身份，隐藏我的审核
                                            unshowCounselor();
                                            break;

                                        case R.id.rb_teacher:
                                            // 如果选择了教师身份，显示我的审核
                                            showCounselor();
                                            break;

                                    }
                                }
                            });
                            /*是否成功请求*/
                            if (result.getCode() == Result.FAIL) {
                                // 错误提示
                                Toast.makeText(LoginActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                // 检查服务器返回的数据是否为 null
                                if (result.getData() != null) {
                                    // 获取 token
                                    String token = result.getData().getToken();
                                    Log.d("token", "   " + token);
                                    // 获取 userName
                                    String userName = result.getData().getUserName();
                                    // 获取 fullName
                                    String fullName = result.getData().getFullName();



                                    /*拿到用户的信息后用工具类存起来*/
                                    int userId = result.getData().getUserId();

                                    SPUtils.put(LoginActivity.this, "token", token);
                                    SPUtils.put(LoginActivity.this, "userName", userName);
                                    SPUtils.put(LoginActivity.this, "fullName", fullName);
                                    SPUtils.put(LoginActivity.this, "userId", userId);

                                    // 判断是否选中记住密码的选择器
                                    ImageView memorySelector = findViewById(R.id.rgb);
                                    boolean rememberPassword = memorySelector.isSelected();
                                    // 如果记住密码选中了，则保存用户名和密码到系统存储中
                                    if (rememberPassword) {
                                        SharedPreferencesUtils.setParam(LoginActivity.this, "user", username);
                                        SharedPreferencesUtils.setParam(LoginActivity.this, "pass", password);
                                        /*登录成功后服务器返回token*/
                                        Intent intent = new Intent(LoginActivity.this, Student_Menu_Activity.class);
                                        startActivity(intent);

                                        // 立即销毁当前的Activity
                                        finish();
                                    } else {
                                        /*不记住直接登录不做保存操作*/
                                        Intent intent = new Intent(LoginActivity.this, Student_Menu_Activity.class);
                                        startActivity(intent);
                                        // 立即销毁当前的Activity
                                        finish();
                                    }
                                } else {
                                    // 处理 data 为 null 的情况
                                    Toast.makeText(LoginActivity.this, "服务器返回的数据为空", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            // 发生错误时的操作
                            if (e instanceof TimeoutException) {
                                // 请求超时
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "服务器请求超时,请检查网络连接！", Toast.LENGTH_SHORT).show();
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
