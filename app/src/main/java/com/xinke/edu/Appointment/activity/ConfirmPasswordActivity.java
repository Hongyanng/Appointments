package com.xinke.edu.Appointment.activity;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xinke.edu.Appointment.R;

import butterknife.ButterKnife;

/**
 * 重置我的密码活动
 * @author xzy
 * */
public class ResetMyPasswordActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_password);
        ButterKnife.bind(this);
    }
}
