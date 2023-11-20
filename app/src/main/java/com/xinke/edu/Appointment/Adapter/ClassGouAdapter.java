package com.xinke.edu.Appointment.Adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinke.edu.Appointment.R;
import com.xinke.edu.Appointment.entity.Classrooms;

import java.util.List;

public class ClassGouAdapter extends BaseQuickAdapter<Classrooms, BaseViewHolder> {
    public ClassGouAdapter(@Nullable List<Classrooms> data) {
        super(R.layout.itme_vido, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Classrooms item) {
        //修改每个布局里面的文字
        //自己设置的id要对应上
        /*{"id":1,"classroomId":"C101","classroomType":"亿纬产业学院","capacity":null,"seatingArrangement":"","building":"C","buildingName":"明志楼","floor":1}*/
        helper.setText(R.id.classroomId, item.getClassroomId());
        helper.setText(R.id.classroomType, item.getClassroomType());
        helper.setText(R.id.capacity, String.valueOf(item.getCapacity()));
        helper.setText(R.id.seatingArrangement, item.getSeatingArrangement());
        helper.setText(R.id.building, item.getBuilding());
        helper.setText(R.id.buildingName, item.getBuildingName());
        helper.setText(R.id.floor, String.valueOf(item.getFloor()));
    }
}
