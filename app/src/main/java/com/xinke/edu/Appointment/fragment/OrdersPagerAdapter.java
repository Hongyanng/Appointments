package com.xinke.edu.Appointment.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.xinke.edu.Appointment.BlankFragment1;
import com.xinke.edu.Appointment.BlankFragment2;

public class OrdersPagerAdapter extends FragmentStateAdapter {

    private BlankFragment1 mBlankFragment1;


    public OrdersPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        // 在调用createFragment()方法之前，保存BlankFragment1的实例
        mBlankFragment1 = new BlankFragment1();

    }



    /*加载布局*/
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new BlankFragment1();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
