package com.xinke.edu.Appointment.Adapter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class MyTabFragmentAdapter extends FragmentPagerAdapter {
    List<Fragment> fragments;
    List<String> titrls;
    public MyTabFragmentAdapter(@NonNull FragmentManager fm, List<Fragment> fragments, List<String> titrls) {
        super(fm);
        this.fragments=fragments;
        this.titrls=titrls;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titrls.get(position);
    }
}
