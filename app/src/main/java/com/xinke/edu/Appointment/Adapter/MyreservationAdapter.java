package com.xinke.edu.Appointment.Adapter;

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
import com.xinke.edu.Appointment.entity.Classrooms;
import com.xinke.edu.Appointment.entity.MyReservation;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MyreservationAdapter extends BaseQuickAdapter<MyReservation, BaseViewHolder> {

    /*获取布局的id*/
    RelativeLayout MakeLayout;

    public MyreservationAdapter(@Nullable List<MyReservation> data) {
        super(R.layout.itme_myreservation, data);
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
        helper.setText(R.id.purpose, item.getPurpose() + " 的教室申请");
        /*申请教室的时间*/
        helper.setText(R.id.time, item.getTime());
        /*首次申请教室的时间*/
        helper.setText(R.id.creationTime, item.getCreationTime());


        /*申请教室的状态*/
        String statusString = getStatusString(item.getStatus());
        String cancelReason = item.getCancelReason();
        if (cancelReason != null) {
            SpannableString spannableStatus = new SpannableString(statusString + " (" + cancelReason + ")");
            if (statusString.equals("已取消") || statusString.equals("已驳回")) {
                spannableStatus.setSpan(new ForegroundColorSpan(Color.RED), 0, spannableStatus.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            helper.setText(R.id.status, spannableStatus);
        } else {
            helper.setText(R.id.status, statusString);
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
                    Log.d("clickedMyReservation",clickedMyReservation.getClassroomId());
                }
            }
        });


    }


    private String getStatusString(int status) {
        String statusString = "";
        switch (status) {
            case 0:
                statusString = "待审核";
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
        Log.d("statusString", statusString);
        return statusString;

    }

}
