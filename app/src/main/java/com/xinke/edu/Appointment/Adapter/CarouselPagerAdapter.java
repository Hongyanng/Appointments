package com.xinke.edu.Appointment.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.xinke.edu.Appointment.fragment.CarouselFragment;

/*首页的轮播图帮助类*/
public class CarouselPagerAdapter extends FragmentPagerAdapter {

    private int[] images;

    public CarouselPagerAdapter(FragmentManager fm, int[] images) {
        super(fm);
        this.images = images;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return CarouselFragment.newInstance(images[position]);
    }

    @Override
    public int getCount() {
        return images.length;
    }
}
