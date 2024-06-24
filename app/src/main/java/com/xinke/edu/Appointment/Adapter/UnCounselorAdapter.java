package com.xinke.edu.Appointment.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
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
import com.xinke.edu.Appointment.ExamineActivity;
import com.xinke.edu.Appointment.R;
import com.xinke.edu.Appointment.entity.Classrooms;
import com.xinke.edu.Appointment.entity.Counselor;
import com.xinke.edu.Appointment.entity.MyReservation;
import com.xinke.edu.Appointment.entity.Result;
import com.xinke.edu.Appointment.net.RetrofitApi;
import com.xinke.edu.Appointment.token.SPUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class UnCounselorAdapter extends BaseQuickAdapter <Counselor, BaseViewHolder> {

    public UnCounselorAdapter(@Nullable List<Counselor> data) {
        super(R.layout.item_uncounselor_info, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Counselor item) {
        helper.setText(R.id.fullName, item.getFullName());
        helper.setText(R.id.classroomId, item.getClassroomId());
        helper.setText(R.id.creationTime, item.getCreationTime());
        helper.setText(R.id.time, item.getTime());
        helper.setText(R.id.period, item.getPeriod());
        helper.setText(R.id.numberParticipants, item.getNumberParticipants() + "");
        helper.setText(R.id.purpose, item.getPurpose());

        /*审核状态*/
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


    }

    private String getStatusString ( int status){
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

