package com.xinke.edu.Appointment;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;
import com.xinke.edu.Appointment.token.SharedPreferencesUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class Student_Menu_Activity extends AppCompatActivity {


    NavController navController;

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
        navController = Navigation.findNavController(this, R.id.navhost_fragment);
        NavigationUI.setupWithNavController(navigationView, navController);

        /*设置用户的用户名*/
        String savedfullName = (String) SharedPreferencesUtils.getParam(this, "fullName", "");

        // 获取 Header View 布局文件
        View headerView = navigationView.getHeaderView(0);

        //修改用户的姓名
        TextView userName = headerView.findViewById(R.id.userName);
        userName.setText("欢迎您:" + savedfullName);

        //点击头像返回到首页
        ImageView imageProfile = headerView.findViewById(R.id.imageProfile);
        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取碎片的实例
                navController = Navigation.findNavController(Student_Menu_Activity.this, R.id.navhost_fragment);

                // 跳转到首页的Fragment
                navController.navigate(R.id.student_Fragment);

                // 关闭侧滑栏
                drawerLayout.closeDrawer(GravityCompat.START);

            }
        });


    }


}

