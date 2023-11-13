package com.xinke.edu.Appointment;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.OnClick;

public class Student_Menu_Activity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_student);
        /*获取布局*/
        final DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);

        /*点击事件加载侧滑栏*/
        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        /*获取布局*/
        final NavigationView navigationView = findViewById(R.id.NavigationView);
        /*设置彩色图标*/
        navigationView.setItemIconTintList(null);

        /*加载碎片*/
        NavController navController = Navigation.findNavController(this, R.id.navhost_fragment);
        NavigationUI.setupWithNavController(navigationView, navController);


    }


}

