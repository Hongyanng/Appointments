package com.xinke.edu.Appointment.Adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinke.edu.Appointment.R;
import com.xinke.edu.Appointment.entity.Counselor;

import java.util.List;

public class CounselorAdapter extends BaseQuickAdapter <Counselor, BaseViewHolder> {
    public CounselorAdapter(@Nullable List<Counselor> data) {
        super(R.layout.item_counselor_info, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Counselor item) {
        helper.setText(R.id.fullName,item.getFullName());
        helper.setText(R.id.classroomId,item.getClassroomId());
        helper.setText(R.id.creationTime,item.getCreationTime());
        helper.setText(R.id.time,item.getTime());
        helper.setText(R.id.period,item.getPeriod());
        helper.setText(R.id.numberParticipants,item.getNumberParticipants()+"");
        helper.setText(R.id.purpose,item.getPurpose());
        helper.setText(R.id.status,item.getStatus()+"");
    }
}
