package com.xinke.edu.Appointment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.release.alert.Alert;
import com.xinke.edu.Appointment.LoaringDialog.LoadingDialog;
import com.xinke.edu.Appointment.entity.Result;
import com.xinke.edu.Appointment.net.RetrofitApi;
import com.xinke.edu.Appointment.token.AuthTokenInterceptor;
import com.xinke.edu.Appointment.token.SPUtils;
import com.xinke.edu.Appointment.token.TokenHeaderInterceptor;

import java.util.concurrent.TimeUnit;

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
 * 我的预约二级菜单
 */

public class ReservationActivity extends AppCompatActivity {

    /*全局变量*/
    Intent intent;
    /*用户的姓名*/
    @BindView(R.id.fullName)
    TextView fullName;
    /*提交预约的时间*/
    @BindView(R.id.creationTime)
    TextView creationTime;
    /*预约的状态*/
    @BindView(R.id.status)
    TextView status;
    /*教室门牌号*/
    @BindView(R.id.classroomId)
    TextView classroomId;
    /*预约的时间*/
    @BindView(R.id.time)
    TextView time;
    /*预计参与预约活动的人数*/
    @BindView(R.id.numberParticipants)
    TextView numberParticipants;
    /*预约的节数*/
    @BindView(R.id.period)
    TextView period;
    /*申请理由*/
    @BindView(R.id.purpose)
    TextView purpose;

    String settoken;

    ProgressDialog progressDialog;

    int reservationIdStr;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        ButterKnife.bind(this); // 绑定视图

        /*获取token*/
        settoken = (String) SPUtils.get(ReservationActivity.this, "token", "");
        /*初始化数据*/
        Init();


    }


    /*系统初始化*/
    public void Init() {

        //将传递过来的值赋值
        intent = getIntent();

        // 使用getStringExtra()方法获取String类型的值
        String classroomIdStr = intent.getStringExtra("classroomId");
        String purposeStr = intent.getStringExtra("purpose");
        String creationTimeStr = intent.getStringExtra("creationTime");
        String fullNameStr = intent.getStringExtra("fullName");
        int numberParticipantsStr = intent.getIntExtra("numberParticipants", 0);
        String periodStr = intent.getStringExtra("period");
        int statusStr = intent.getIntExtra("status", 0);
        String timeStr = intent.getStringExtra("time");
        reservationIdStr = intent.getIntExtra("reservationId", 0);


        //将获取到的数据赋值给获取到的TextView
        classroomId.setText(classroomIdStr);
        purpose.setText(purposeStr);
        creationTime.setText(creationTimeStr);
        fullName.setText(fullNameStr);
        numberParticipants.setText(String.valueOf(numberParticipantsStr));
        period.setText(periodStr);
        String input = getStatusString(statusStr);
        status.setText(input);
        time.setText(timeStr);
        GetQRode();
    }


    /*判断当前状态的方法*/
    private String getStatusString(int status) {
        String statusString = "";
        switch (status) {
            case 0:
                statusString = "待教室管理员审核";
                break;
            case 1:
                statusString = "已通过";
                break;
            case 2:
                statusString = "等待辅导员审核";
                break;
            case 3:
                statusString = "已取消";
                break;
            case 4:
                statusString = "已驳回";
                break;
        }
        return statusString;

    }
    /*返回按钮*/
    @OnClick(R.id.back)
    public void Back() {
        finish();
    }


    /*取消预约的按钮*/
    @OnClick(R.id.reservation_button)
    public void CancelReservation() {
        // 判断当前状态是否为已取消、已通过或已驳回
        if (status.getText().toString().equals("已取消") ||
                status.getText().toString().equals("已通过") ||
                status.getText().toString().equals("已驳回")) {
            // 如果是，则不允许执行取消预约操作
            new Alert(ReservationActivity.this)
                    .builder()
                    .setMsg("当前状态不允许取消预约！")
                    .setPositiveButton(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    })
                    .show();
        } else {
            new Alert(ReservationActivity.this)
                    .builder()
                    .setTitle("系统通知")
                    .setMsg("是否确认取消预约?")
                    .setPositiveButton(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            NoReservation(reservationIdStr);
                        }
                    })
                    .setNegativeButton(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    }).show();
        }
    }


    /*调用取消预约的接口*/
    public void NoReservation(int reservationId) {
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
        retrofitApi.NoReservation(reservationId)
                .observeOn(Schedulers.io())
                .timeout(10, TimeUnit.SECONDS) // 设置超时时间为10秒
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Result>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        // 显示加载动画
                        LoadingDialog.getInstance(ReservationActivity.this).show();
                    }

                    @Override
                    public void onNext(@NonNull Result result) {
                        if (result.getCode() == 200) {
                            new Alert(ReservationActivity.this)
                                    .builder()
                                    .setMsg("取消预约成功！")
                                    .setPositiveButton(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                        }
                                    })
                                    .show();

                            /*重新加载二维码*/
                            status.setText("已取消");
                            GetQRode();
                            return;
                        } else {
                            Log.e("ServerError", "Code: " + result.getCode() + ", Message: " + result.getMsg());
                            new Alert(ReservationActivity.this)
                                    .builder()
                                    .setMsg("取消预约失败,原因为:"+result.getMsg())
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
                        e.printStackTrace();
                        LoadingDialog.getInstance(ReservationActivity.this).hide();//隐藏动画
                    }

                    @Override
                    public void onComplete() {
                        LoadingDialog.getInstance(ReservationActivity.this).hide();//隐藏动画
                    }
                });


    }

    /*okhttp拦截器*/
    private OkHttpClient.Builder getClient() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(15, TimeUnit.SECONDS);

        // 添加 token 拦截器
        httpClientBuilder.addNetworkInterceptor(new TokenHeaderInterceptor(ReservationActivity.this));

        return httpClientBuilder;
    }


    /*加载二维码*/
    public void GetQRode() {
        ImageView qrCodeImageView = findViewById(R.id.qrCodeImageView);

        try {
            Bitmap qrCodeBitmap;
            int backgroundColor;

            //背景色
            switch (status.getText().toString()) {
                case "已取消":
                    backgroundColor = Color.RED; // 红色背景
                    break;
                case "已通过":
                    backgroundColor = Color.GREEN; // 绿色背景
                    break;
                case "等待辅导员审核":
                    backgroundColor = Color.YELLOW; // 黄色背景
                    break;
                case "已驳回":
                    backgroundColor = Color.LTGRAY; // 灰色背景
                    break;
                default:
                    backgroundColor = Color.WHITE; // 默认白色背景
                    break;
            }

            qrCodeBitmap = generateQRCodeBitmapWithColor("https://www.guit.edu.cn/", backgroundColor);
            qrCodeImageView.setImageBitmap(qrCodeBitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }


    private Bitmap generateQRCodeBitmapWithColor(String text, int backgroundColor) throws
            WriterException {
        QRCodeWriter writer = new QRCodeWriter();
        int size = 512; // pixels
        BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE, size, size);
        return toBitmapWithColor(matrix, backgroundColor);
    }


    private static Bitmap toBitmapWithColor(BitMatrix matrix, int backgroundColor) {
        int height = matrix.getHeight();
        int width = matrix.getWidth();
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bmp.setPixel(x, y, matrix.get(x, y) ? Color.BLACK : backgroundColor);
            }
        }
        return bmp;
    }


}