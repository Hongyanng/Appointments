package com.xinke.edu.Appointment.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.xinke.edu.Appointment.BlankFragment1;
import com.xinke.edu.Appointment.BlankFragment2;

public class OrdersPagerAdapter extends FragmentStateAdapter {

    public OrdersPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new BlankFragment1();
            case 1:
                return new BlankFragment2();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
