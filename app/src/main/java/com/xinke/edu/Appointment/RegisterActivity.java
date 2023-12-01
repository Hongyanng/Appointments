package com.xinke.edu.Appointment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.xinke.edu.Appointment.entity.Result;
import com.xinke.edu.Appointment.entity.User;
import com.xinke.edu.Appointment.net.RetrofitApi;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;

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

import android.text.Editable;
import android.text.TextWatcher;


/**
 * 注册页面
 */
public class RegisterActivity extends AppCompatActivity {
    /*注解绑定*/


    /*获取输入的用户名id*/
    @BindView(R.id.et_username)
    EditText usernameStr;


    /*获取输入的密码paws*/
    @BindView(R.id.et_password)
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

    /*获取输入的辅导员姓名*/
    @BindView(R.id.instructor)
    EditText instructorStr;


    /*获取单选框*/
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;


    /*获取身份*/
    //教师1
    @BindView(R.id.rb_teacher)
    RadioButton MyRadiorbTeacher;

    //学生2
    @BindView(R.id.rb_student)
    RadioButton MyRadioStudent;

    /*辅导员3*/
    @BindView(R.id.rb_instructor)
    RadioButton MyInstructor;

    /*获取性别*/
    @BindView(R.id.radio_male)
    RadioButton Myradio_male;
    @BindView(R.id.radio_female)
    RadioButton Myradio_female;

    //初始化老师学生身份
    int authenticationStatus;

    //初始化用户性别
    String gender;

    /*姓名*/

    String fullname;

    /*学号*/
    String username;

    //密码
    String password;

    //手机号码
    String phone;

    //辅导员姓名
    String instructor;

    // 创建正则表达式用于验证电子邮件格式
    private static final Pattern EMAIL_PATTERN = Patterns.EMAIL_ADDRESS;


    String email;

    /*加载动画*/
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this); // 绑定视图



        /*隐藏对应的视图*/
        // 设置RadioGroup的监听器
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // 根据选择的身份来隐藏或显示相应的组件
                switch (checkedId) {
                    case R.id.rb_teacher:
                        hideComponentsForTeacher();
                        break;

                    case R.id.rb_student:
                        showAllComponents();
                        break;

                    case R.id.rb_instructor:
                        hideComponentsForInstructor();
                        break;
                }
            }
        });


        //邮箱地址监听事件
        emailStr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String email = s.toString();
                if (!isValidEmail(email)) {
                    emailStr.setError("电子邮件地址格式错误");
                }
            }
        });

        //姓名不能为纯数字监听事件
        fullnameStr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String name = s.toString();
                if (isNumeric(name)) {
                    fullnameStr.setError("姓名不允许为纯数字");
                }
            }
        });


    }

    //关闭按钮
    @OnClick(R.id.off)
    public void OFF() {
        finish();
    }


    /*注册事件*/
    @OnClick(R.id.btn_register)
    public void register() {
        if (!validateInputs()) {
            return;
        }
        fullname = fullnameStr.getText().toString();
        username = usernameStr.getText().toString();
        password = passwordStr.getText().toString();
        email = emailStr.getText().toString();
        phone = phoneStr.getText().toString();
        instructor = instructorStr.getText().toString();

        //发送请求
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(RetrofitApi.BaseUrl)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        //实例化对象接受服务器的信息
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);

        User user = new User();
        user.setFullName(fullname);
        user.setUserName(username);
        user.setPassword(password);
        user.setEmailAddress(email);
        user.setPhoneNumber(phone);
        user.setAuthenticationStatus(authenticationStatus);
        user.setGender(gender);
        user.setInstructor(instructor);
        Log.d("data",fullname+"  "+username+"  "+password+"  "+email+"  "+phone+"  "+authenticationStatus+"  "+gender+"  "+instructor);


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
                        if (result.getCode() == Result.FAIL) {
                            Toast.makeText(RegisterActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            //注册成功后弹出一个选择框，询问用户是否返回登录页面
                            showSuccessDialog();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        // 发生错误时的操作
                        if (e instanceof TimeoutException) {
                            // 请求超时
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "服务器请求超时", Toast.LENGTH_SHORT).show();
                        } else {
                            // 其他服务器错误
                            Log.e("onError", e.toString());
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "服务器错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onComplete() {
                        // 完成时的操作
                    }
                });
    }


    //判断是否为空
    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }


    //约束用户
    private boolean validateInputs() {


        if (MyRadiorbTeacher.isChecked()) {
            authenticationStatus = 1;
        } else if (MyRadioStudent.isChecked()) {
            authenticationStatus = 2;
        } else if (MyInstructor.isChecked()) {
            authenticationStatus = 3;
        } else {
            Toast.makeText(RegisterActivity.this, "请先选中用户身份", Toast.LENGTH_SHORT).show();
            return false;
        }

        // 检查学号是否为空
        if (usernameStr.getText().toString().isEmpty()) {
            usernameStr.setError("学号不能为空");
            return false;
        }
        // 检查密码是否为空
        if (passwordStr.getText().toString().isEmpty()) {
            passwordStr.setError("密码不能为空");
            return false;
        }


        if (Myradio_male.isChecked()) {
            gender = "男";
        } else if (Myradio_female.isChecked()) {
            gender = "女";
        } else {
            Toast.makeText(RegisterActivity.this, "请先选中用户性别", Toast.LENGTH_SHORT).show();
            return false;
        }


        // 检查姓名是否为空
        if (fullnameStr.getText().toString().isEmpty()) {
            fullnameStr.setError("姓名不能为空");
            return false;
        }


        if (!isValidEmail(emailStr.getText().toString())) {
            emailStr.setError("电子邮件地址格式错误");
            return false;
        }


        // 检查手机号是否为空
        if (phoneStr.getText().toString().isEmpty()) {
            phoneStr.setError("手机号码不能为空");
            return false;
        }


        return true;
    }

    // 判断是否为纯数字的方法
    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        // 使用正则表达式检查是否包含数字
        return str.matches(".*\\d.*");
    }


    //
    // 显示注册成功对话框
    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("注册成功");
        builder.setMessage("注册成功！是否要返回到登录页面？");

        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    //教师身份
    private void hideComponentsForTeacher() {
        findViewById(R.id.fdy_layout).setVisibility(View.GONE);
        findViewById(R.id.fdytxt).setVisibility(View.GONE);
    }

    //辅导员身份
    private void hideComponentsForInstructor() {
        findViewById(R.id.fdy_layout).setVisibility(View.GONE);
        findViewById(R.id.fdytxt).setVisibility(View.GONE);
    }

    //学生身份
    private void showAllComponents() {
        findViewById(R.id.fdytxt).setVisibility(View.VISIBLE);
        findViewById(R.id.fdy_layout).setVisibility(View.VISIBLE);
    }


}
