package com.xinke.edu.Appointment.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.xinke.edu.Appointment.R;

/*首页的轮播图*/
public class CarouselFragment extends Fragment {
    private int imageResource;

    public CarouselFragment() {
    }

    public static CarouselFragment newInstance(int imageResource) {
        CarouselFragment fragment = new CarouselFragment();
        Bundle args = new Bundle();
        args.putInt("imageResource", imageResource);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageResource = getArguments().getInt("imageResource");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carousel, container, false);
        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageResource(imageResource);
        return view;
    }
}
