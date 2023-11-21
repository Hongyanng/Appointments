package com.xinke.edu.Appointment.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinke.edu.Appointment.LoginActivity;
import com.xinke.edu.Appointment.R;
import com.xinke.edu.Appointment.Student_Menu_Activity;
import com.xinke.edu.Appointment.entity.Classrooms;
import com.xinke.edu.Appointment.entity.Reservetion;
import com.xinke.edu.Appointment.entity.Result;
import com.xinke.edu.Appointment.net.RetrofitApi;
import com.xinke.edu.Appointment.token.SPUtils;
import com.xinke.edu.Appointment.token.TokenHeaderInterceptor;

import butterknife.OnClick;

import java.util.List;
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

public class ClassGouAdapter extends BaseQuickAdapter<Classrooms, BaseViewHolder> {


    /*预约的人数*/
    int purpose;

    /*预约的描述*/
    String peopleCount;

    /*预约的用户id*/
    int userId;

    /*预约的时间*/
    String timeStr;

    /*预约的节数*/
    String periodStr;

    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    // 接口定义
    public interface OnItemClickListener {
        void onItemClick(Classrooms item);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    public ClassGouAdapter(@Nullable List<Classrooms> data) {
        super(R.layout.itme_vido, data);

    }


    @Override
    protected void convert(BaseViewHolder helper, Classrooms item) {


        /*获取保存的数据*/
        userId = (int) SPUtils.get(getContext(), "userId", 0);
        timeStr = (String) SPUtils.get(getContext(), "timeStr", "");
        periodStr = (String) SPUtils.get(getContext(), "periodStr", "");


        // 修改每个布局里面的文字
        /*{"id":1,"classroomId":"C101","classroomType":"亿纬产业学院","capacity":null,"seatingArrangement":"","building":"C","buildingName":"明志楼","floor":1}*/
        helper.setText(R.id.classroomId, item.getClassroomId());
        helper.setText(R.id.classroomType, item.getClassroomType());
        helper.setText(R.id.capacity, String.valueOf(item.getCapacity()));
        helper.setText(R.id.seatingArrangement, item.getSeatingArrangement());
        helper.setText(R.id.building, item.getBuilding());
        helper.setText(R.id.buildingName, item.getBuildingName());
        helper.setText(R.id.floor, String.valueOf(item.getFloor()));


        // 获取预约按钮的View
        Button reserveButton = helper.getView(R.id.reserveButton);

        // 设置按钮点击事件
        reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取当前项在适配器中的位置
                int position = helper.getAdapterPosition();
                // 判断位置是否有效
                if (position != RecyclerView.NO_POSITION) {
                    // 获取点击位置的数据
                    Classrooms clickedClassroom = getItem(position);

                    // 创建一个AlertDialog.Builder对象
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    // 设置对话框标题
                    builder.setTitle("请输入预约信息");

                    // 添加一个布局容器，将两个EditText放在其中
                    LinearLayout layout = new LinearLayout(getContext());
                    layout.setOrientation(LinearLayout.VERTICAL);

                    // 添加EditText组件到对话框
                    final EditText etPeopleCount = new EditText(getContext());
                    etPeopleCount.setHint("预计参与人数");
                    layout.addView(etPeopleCount);

                    //预约的目的
                    final EditText etPurpose = new EditText(getContext());
                    etPurpose.setHint("预约目的或活动描述");
                    layout.addView(etPurpose);

                    // 设置对话框按钮
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 获取用户输入的数据
                            String purposeStr = etPeopleCount.getText().toString();
                            peopleCount = etPurpose.getText().toString();
                            // 检查输入是否为空
                            if (!TextUtils.isEmpty(purposeStr)) {
                                try {
                                    purpose = Integer.parseInt(purposeStr);

                                } catch (NumberFormatException e) {
                                    // 处理转换异常
                                    e.printStackTrace();
                                }
                            } else {
                                Toast.makeText(getContext(), "不能为空！", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            Log.d("peopleCount", purpose + "");
                            Log.d("purposeText", peopleCount);

                            /*实例化对象*/
                            Reservetion reservetion = new Reservetion();

                            reservetion.setClassroomId(clickedClassroom.getClassroomId());
                            reservetion.setNumberParticipants(purpose);
                            Log.d("purpose", purpose + "");
                            reservetion.setPeriod(periodStr);
                            reservetion.setPurpose(peopleCount);
                            reservetion.setTime(timeStr);

                            reservetion.setUserId(userId);
                            Log.d("userId", userId + "");
                            reservetion.setBuilding(clickedClassroom.getBuilding());
                            reservetion.setFloor(clickedClassroom.getFloor());

                            /*调用方法给后端传值*/
                            GetClassroom(reservetion);


                            // 如果需要，可以通过回调通知监听器
                            if (onReserveClickListener != null) {
                                onReserveClickListener.onReserveClick(clickedClassroom);
                            }
                        }
                    });

                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 用户点击取消按钮的处理
                            dialog.dismiss();
                        }
                    });

                    // 将布局容器设置为对话框的视图
                    builder.setView(layout);

                    // 创建并显示对话框
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });


    }

    // 添加一个接口用于回调点击事件
    public interface OnReserveClickListener {
        void onReserveClick(Classrooms clickedClassroom);
    }

    private OnReserveClickListener onReserveClickListener;

    // 提供设置点击事件的方法
    public void setOnReserveClickListener(OnReserveClickListener listener) {
        this.onReserveClickListener = listener;
    }


    //后端回传数据的方法
    public void GetClassroom(Reservetion reservetion) {

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

        retrofitApi.reservation(reservetion)
                .observeOn(Schedulers.io())
                .timeout(10, TimeUnit.SECONDS)// 设置超时时间为10秒
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Result>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Result result) {


                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }


    /*okhttp拦截器*/
    private OkHttpClient.Builder getClient() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(15, TimeUnit.SECONDS);
        httpClientBuilder.addNetworkInterceptor(new TokenHeaderInterceptor(getContext()));
        return httpClientBuilder;
    }

}


