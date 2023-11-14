package com.xinke.edu.Appointment.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xinke.edu.Appointment.Adapter.CarouselPagerAdapter;
import com.xinke.edu.Appointment.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Student_Fragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;

    private ViewPager viewPager;


    public Student_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.student_fragment, container, false);
        viewPager = view.findViewById(R.id.viewPager);

        // 创建轮播图的图片资源数组
        int[] carouselImages = {R.mipmap.result_log, R.mipmap.result_log, R.mipmap.result_log};

        // 创建 FragmentPagerAdapter 并设置给 ViewPager
        CarouselPagerAdapter adapter = new CarouselPagerAdapter(getChildFragmentManager(), carouselImages);
        viewPager.setAdapter(adapter);


        // 设置下拉刷新监听器
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                    // 刷新完成后停止刷新动画
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        } else {
            Log.e("student_fragment", "这个布局是空的");
        }
        return view;

    }

}
