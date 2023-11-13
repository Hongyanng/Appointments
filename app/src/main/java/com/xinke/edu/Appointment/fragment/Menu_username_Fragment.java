package com.xinke.edu.Appointment.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xinke.edu.Appointment.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Menu_username_Fragment extends Fragment {


    public Menu_username_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        /*个人信息的碎片*/

        return inflater.inflate(R.layout.menu_username_fragment, container, false);
    }

}
