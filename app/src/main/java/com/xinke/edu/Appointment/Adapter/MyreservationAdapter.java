package com.xinke.edu.Appointment.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinke.edu.Appointment.R;
import com.xinke.edu.Appointment.ReservationActivity;
import com.xinke.edu.Appointment.entity.MyReservation;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * 我的预约一级菜单
 */

public class MyreservationAdapter extends BaseQuickAdapter<MyReservation, BaseViewHolder> {

    /*获取布局的id*/
    RelativeLayout MakeLayout;

    private Context context;

    public static final int RESERVATION_ACTIVITY_REQUEST_CODE = 1;


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public MyreservationAdapter(@Nullable List<MyReservation> data, Context context) {
        super(R.layout.itme_myreservation, data);

        this.context = context;

        // 初始化时对数据进行排序
        sortDataByCreationTime();


    }

    private void sortDataByCreationTime() {
        Collections.sort(getData(), new Comparator<MyReservation>() {
            @Override
            public int compare(MyReservation o1, MyReservation o2) {
                // 根据creationTime进行降序排序
                return o2.getCreationTime().compareTo(o1.getCreationTime());
            }
        });
        setNewData(getData()); // 刷新列表
    }

    @Override
    protected void convert(BaseViewHolder helper, MyReservation item) {


        /*申请的原因*/
        helper.setText(R.id.purpose, item.getPurpose() + "的教室申请");


        /*申请教室的时间*/
        helper.setText(R.id.time, item.getTime() + " " + "第" + item.getPeriod() + "节");
        /*首次申请教室的时间*/
        helper.setText(R.id.creationTime, item.getCreationTime());


        /*审核状态*/
        String statusString = getStatusString(item.getStatus());
        /*取消原因*/
        String cancelReason = item.getCancelReason();
        if (cancelReason != null) {
            SpannableString spannableStatus = new SpannableString(statusString +"  "+"原因:"+ " (" + cancelReason + ")");

            if (statusString.equals("已取消") || statusString.equals("已驳回")) {
                spannableStatus.setSpan(new ForegroundColorSpan(Color.RED), 0, spannableStatus.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            helper.setText(R.id.status, spannableStatus);
        } else {
            SpannableString yStatus = new SpannableString(statusString);
            if (statusString.equals("待教室管理员审核")) {
                yStatus.setSpan(new ForegroundColorSpan(Color.BLUE), 0, yStatus.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else if (statusString.equals("已通过")) {
                yStatus.setSpan(new ForegroundColorSpan(Color.GREEN), 0, yStatus.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            helper.setText(R.id.status, yStatus);
        }


        /*新建activity查看预约详情*/
        MakeLayout = helper.getView(R.id.MakeLayout);
        /*点击事件*/
        MakeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取当前项在适配器中的位置
                int position = helper.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    // 获取点击位置的数据
                    MyReservation clickedMyReservation = getItem(position);

                    // 创建一个新的Intent
                    Intent intent = new Intent(context, ReservationActivity.class);

                    // 通过Intent传递预约信息
                    //教室
                    intent.putExtra("classroomId", clickedMyReservation.getClassroomId());
                    //预约教室的目的或活动的简短描述
                    intent.putExtra("purpose", clickedMyReservation.getPurpose());
                    //记录预约创建的时间
                    intent.putExtra("creationTime", clickedMyReservation.getCreationTime());
                    //用户的真实姓名
                    intent.putExtra("fullName", clickedMyReservation.getFullName());
                    //预计参与预约活动的人数
                    intent.putExtra("numberParticipants", clickedMyReservation.getNumberParticipants());
                    //时间段
                    intent.putExtra("period", clickedMyReservation.getPeriod());
                    //预约的状态
                    intent.putExtra("status", clickedMyReservation.getStatus());
                    //预约的具体时间
                    intent.putExtra("time", clickedMyReservation.getTime());
                    //用户的id
                    intent.putExtra("reservationId", clickedMyReservation.getReservationId());

                    Log.d("reservationId",clickedMyReservation.getReservationId()+"");

                    // 启动ReservationActivity
                    context.startActivity(intent);

                }
            }
        });


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

}
