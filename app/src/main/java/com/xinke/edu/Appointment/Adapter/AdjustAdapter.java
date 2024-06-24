package com.xinke.edu.Appointment.Adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinke.edu.Appointment.R;
import com.xinke.edu.Appointment.entity.Course;

import java.util.List;
/**
 * 老师查询课程的适配器
 */
public class AdjustAdapter extends BaseQuickAdapter<Course, BaseViewHolder> {

    public AdjustAdapter(@Nullable List<Course> data) {
        super(R.layout.item_adjust_info, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Course item) {
        helper.setText(R.id.courseId,item.getCourseid());
        helper.setText(R.id.classroomId,item.getClassroomid());
        helper.setText(R.id.courseName,item.getCoursename());
        helper.setText(R.id.period,item.getPeriod());
        helper.setText(R.id.semester,item.getSemester());
        helper.setText(R.id.teacherName,item.getTeachername());
        helper.setText(R.id.time,item.getTime());
    }
}
