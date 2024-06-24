package com.xinke.edu.Appointment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.release.alert.Alert;
import com.xinke.edu.Appointment.LoaringDialog.LoadingDialog;
import com.xinke.edu.Appointment.entity.Result;
import com.xinke.edu.Appointment.entity.User;
import com.xinke.edu.Appointment.net.RetrofitApi;
import com.xinke.edu.Appointment.token.SPUtils;

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

    private static final int REQUEST_PERMISSION = 1;
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

    /*获取邀请码*/
    @BindView(R.id.invitationCode)
    EditText invitationCodeStr;

    /*获取UID*/
    @BindView(R.id.uid)
    EditText uidStr;

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

    //邀请码
    String invitationCode;

    //UID
    String uid;

    //二维码局部变量
    Bitmap qrCodeBitmap;

    // 创建正则表达式用于验证电子邮件格式
    private static final Pattern EMAIL_PATTERN = Patterns.EMAIL_ADDRESS;

    /*二维码的url*/
    String UidUrl;

    String email;

    /**
     * uid布局
     */
    @BindView(R.id.uid_layout)
    LinearLayoutCompat uid_layout;

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
                } else if (name.contains(" ")) {
                    fullnameStr.setError("姓名不能包含空格");
                }
            }
        });


        // 学号输入框文本变化监听器
        usernameStr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String username = s.toString();
                if (username.contains(" ")) {
                    usernameStr.setError("学号不能包含空格");
                }
            }
        });
        // 密码输入框文本变化监听器
        passwordStr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = s.toString();
                if (password.contains(" ")) {
                    passwordStr.setError("密码不能包含空格");
                }
            }
        });


        // 手机号码文本变化监听器
        phoneStr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String phoneNumber = s.toString();
                if (!isValidPhoneNumber(phoneNumber)) {
                    phoneStr.setError("手机号码格式错误");
                }
            }
        });


        //设置uid监听器点击后,调用WxPush()方法
        uidStr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*获取网络*/
                Retrofit retrofit = new Retrofit
                        .Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(RetrofitApi.BaseUrl)
                        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                        .build();
                /*实例化*/
                RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
                retrofitApi.WxPusher()
                        .observeOn(Schedulers.io())
                        .timeout(10, TimeUnit.SECONDS) // 设置超时时间为10秒
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Result>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                // 显示加载动画
                                LoadingDialog.getInstance(RegisterActivity.this).show();
                            }

                            @Override
                            public void onNext(@NonNull Result result) {
                                if (result.getCode() == 200) {
                                    // 判断data字段是否为String类型
                                    if (result.getData() instanceof String) {
                                        // 进行强制类型转换
                                        UidUrl = (String) result.getData();
                                        //传入方法获取二维码
                                        WxPush(UidUrl);
                                    }
                                } else {
                                    new Alert(RegisterActivity.this)
                                            .builder()
                                            .setMsg(result.getMsg())
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
                                LoadingDialog.getInstance(RegisterActivity.this).hide();//隐藏动画
                            }

                            @Override
                            public void onComplete() {
                                LoadingDialog.getInstance(RegisterActivity.this).hide();//隐藏动画
                            }
                        });
            }
        });


    }

    //关闭按钮
    @OnClick(R.id.back)
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
        invitationCode = invitationCodeStr.getText().toString();
        uid = uidStr.getText().toString();

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
        user.setInvitationCode(invitationCode);
        user.setUid(uid);


        retrofitApi.register(user)
                .subscribeOn(Schedulers.io())
                .timeout(10, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Result>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        LoadingDialog.getInstance(RegisterActivity.this).show();//显示
                    }

                    @Override
                    public void onNext(@NonNull Result result) {
                        if (result.getCode() == Result.FAIL) {
                            new Alert(RegisterActivity.this)
                                    .builder()
                                    .setMsg(result.getMsg())
                                    .setPositiveButton(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                        }
                                    })
                                    .show();

                            return;
                        } else {
                            //注册成功
                            showSuccessDialog();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        // 发生错误时的操作
                        if (e instanceof TimeoutException) {
                            // 请求超时
                            LoadingDialog.getInstance(RegisterActivity.this).hide();//隐藏
                            new Alert(RegisterActivity.this)
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
                            LoadingDialog.getInstance(RegisterActivity.this).hide();//隐藏
                            new Alert(RegisterActivity.this)
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
                        LoadingDialog.getInstance(RegisterActivity.this).hide();//隐藏
                    }
                });
    }

    //获取推送的方法
    private void WxPush(String Uidstr) {
        // 创建一个AlertDialog.Builder对象
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 设置对话框标题
        builder.setTitle("请先获取UID");
        // 添加一个布局容器，将二维码放在其中
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        // 生成二维码
        Bitmap qrCodeBitmap;
        try {
            qrCodeBitmap = generateQRCodeBitmap(Uidstr);
        } catch (WriterException e) {
            e.printStackTrace();
            return;
        }

        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(qrCodeBitmap);

        // 将ImageView添加到布局容器中
        layout.addView(imageView);

        TextView tipsText = new TextView(this);
        tipsText.setText("长按二维码可保存图片到相册,使用微信扫码获取uid");
        //居中显示
        LinearLayout.LayoutParams tipsParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        tipsParams.gravity = Gravity.CENTER_HORIZONTAL; // 设置水平方向居中
        tipsText.setLayoutParams(tipsParams);
        layout.addView(tipsText);

        // 添加提示获取uid的提示
        TextView bottomText = new TextView(this);
        bottomText.setText("       关注成功后查询UID：右下角“我的”->“我的UID”");
        layout.addView(bottomText);

        // 添加长按监听器以保存图片到相册
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //先检查是否具备存储权限
                if (ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // 如果没有权限，则请求权限
                    ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
                } else {
                    saveImageToGallery(qrCodeBitmap);
                }
                return true;
            }
        });

        builder.setView(layout);

        // 添加确定按钮
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        // 创建对话框并显示
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    // 保存图片到相册
    private void saveImageToGallery(Bitmap bitmap) {
        String savedImageURL = MediaStore.Images.Media.insertImage(
                getContentResolver(),
                bitmap,
                "",
                ""
        );

        if (savedImageURL != null) {
            new Alert(this)
                    .builder()
                    .setMsg("二维码已成功保存到相册,请前往WeChet扫码获取uid")
                    .setPositiveButton(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .show();
        } else {
            new Alert(this)
                    .builder()
                    .setMsg("二维码保存失败!")
                    .setPositiveButton(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .show();
        }
    }


    private Bitmap generateQRCodeBitmap(String text) throws WriterException {
        QRCodeWriter writer = new QRCodeWriter();
        int size = 512; // pixels
        BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE, size, size);
        return toBitmap(matrix);
    }

    private static Bitmap toBitmap(BitMatrix matrix) {
        int height = matrix.getHeight();
        int width = matrix.getWidth();
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bmp.setPixel(x, y, matrix.get(x, y) ? Color.BLACK : Color.WHITE);
            }
        }
        return bmp;
    }


    //判断邮箱是否为空
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
            new Alert(RegisterActivity.this)
                    .builder()
                    .setMsg("请先选中用户身份!")
                    .setPositiveButton(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .show();
            return false;
        }

        // 检查学号是否为空
        if (usernameStr.getText().toString().isEmpty()) {
            usernameStr.setError("学号不能为空");
            return false;
        }
        // 如果学号包含空格，返回 false
        if (usernameStr.getText().toString().contains(" ")) {
            usernameStr.setError("学号不能包含空格");
            return false;
        }
        // 检查密码是否为空
        if (passwordStr.getText().toString().isEmpty()) {
            passwordStr.setError("密码不能为空");
            return false;
        }
        // 如果密码包含空格，返回 false
        if (passwordStr.getText().toString().contains(" ")) {
            passwordStr.setError("密码不能包含空格");
            return false;
        }


        if (Myradio_male.isChecked()) {
            gender = "男";
        } else if (Myradio_female.isChecked()) {
            gender = "女";
        } else {
            new Alert(this)
                    .builder()
                    .setMsg("请先选中用户性别!")
                    .setPositiveButton(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .show();
            return false;
        }


        // 检查姓名是否为空
        if (fullnameStr.getText().toString().isEmpty()) {
            fullnameStr.setError("姓名不能为空");
            return false;
        }
        // 如果姓名包含空格，返回 false
        if (fullnameStr.getText().toString().contains(" ")) {
            fullnameStr.setError("姓名不能包含空格");
            return false;
        }

        //电子邮件的格式
        if (!isValidEmail(emailStr.getText().toString())) {
            emailStr.setError("电子邮件地址格式错误");
            return false;
        }


        // 检查手机号是否为空
        if (phoneStr.getText().toString().isEmpty()) {
            phoneStr.setError("手机号码不能为空");
            return false;
        }

        // 如果手机格式验证失败，返回 false
        if (!isValidPhoneNumber(phoneStr.getText().toString())) {
            phoneStr.setError("手机号码格式错误");
            return false;
        }

        // 检查uid是否为空
        if (uidStr.getText().toString().isEmpty()) {
            uidStr.setError("uid不能为空,否则不能收到消息通知！");
            return false;
        }


        // 验证邀请码是否为空
        if (invitationCodeStr.getText().toString().isEmpty()) {
            invitationCodeStr.setError("邀请码不能为空");
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
        new Alert(RegisterActivity.this)
                .builder()
                .setTitle("系统通知")
                .setMsg("注册账号成功是否现在登录？")
                .setPositiveButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
    }


    //教师身份
    private void hideComponentsForTeacher() {
        findViewById(R.id.fdy_layout).setVisibility(View.GONE);
        findViewById(R.id.fdytxt).setVisibility(View.GONE);
        findViewById(R.id.yqm_layout).setVisibility(View.VISIBLE);
        findViewById(R.id.yqm_text).setVisibility(View.VISIBLE);
    }

    //辅导员身份
    private void hideComponentsForInstructor() {
        findViewById(R.id.fdy_layout).setVisibility(View.GONE);
        findViewById(R.id.fdytxt).setVisibility(View.GONE);
        findViewById(R.id.yqm_layout).setVisibility(View.VISIBLE);
        findViewById(R.id.yqm_text).setVisibility(View.VISIBLE);
    }

    //学生身份
    private void showAllComponents() {
        findViewById(R.id.fdytxt).setVisibility(View.VISIBLE);
        findViewById(R.id.fdy_layout).setVisibility(View.VISIBLE);

    }

    // 验证手机号码格式
    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{11}");

    }
}
