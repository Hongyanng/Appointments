package com.xinke.edu.Appointment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.release.alert.Alert;
import com.roughike.bottombar.BottomBar;

import com.xinke.edu.Appointment.activity.ClassadjustmentActivities;
import com.xinke.edu.Appointment.activity.SettingActivity;
import com.xinke.edu.Appointment.fragment.OrdersPagerAdapter;
import com.xinke.edu.Appointment.token.SPUtils;


public class Student_Menu_Activity extends AppCompatActivity {


    // 定义一个变量，来标识是否退出
    private Boolean isExit = false;


    /*初始化下拉框*/
    NavController navController;

    View headerView;
    NavigationView navigationView;

    /*当前登录用户的标识符号*/
    int authenticationStatus;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_student);

        /*初始化*/
        /*获取标识符号*/
        authenticationStatus = (int) SPUtils.get(Student_Menu_Activity.this, "authenticationStatus", 0);



        /*获取布局*/
        navigationView = findViewById(R.id.NavigationView);

        // 获取菜单项的引用
        // 辅导员审核
        MenuItem notificat = navigationView.getMenu().findItem(R.id.menu_notification);
        // 教师调课
        MenuItem adjust = navigationView.getMenu().findItem(R.id.menu_collection);

        // 根据 authenticationStatus 设置菜单项的可见性
        if (authenticationStatus == 3||authenticationStatus==1) {
            //显示
            notificat.setVisible(true);
            adjust.setVisible(true);
        }

        else {
            //不显示
            notificat.setVisible(false);
            adjust.setVisible(false);
        }


        /*底部的布局切换*/
        ViewPager2 viewPager2 = findViewById(R.id.viewPager);
        viewPager2.setAdapter(new OrdersPagerAdapter(this));


        /*侧滑栏菜单的点击事件*/
        navigationView = findViewById(R.id.NavigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.menu_username:

                        break;
                        //申请调课
                    case R.id.menu_collection:
                        Intent inten4 = new Intent(Student_Menu_Activity.this, ClassadjustmentActivities.class);
                        startActivity(inten4);
                        break;
/*                    case R.id.menu_inbox:

                        break;*/
                    case R.id.menu_notification:
                        Intent intent = new Intent(Student_Menu_Activity.this, ExamineActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_orders:
                        Intent intent1 = new Intent(Student_Menu_Activity.this, MyreservationActivity.class);
                        startActivity(intent1);
                        break;
                        /*设置页面*/
                    case R.id.menu_setting:
                        Intent intent2 = new Intent(Student_Menu_Activity.this, SettingActivity.class);
                        startActivity(intent2);
                        break;
/*                    case R.id.menu_support:
                        break;*/

                    case R.id.menu_exit:
                        showExitConfirmationDialog();
                        break;
                }
                return true;
            }

        });


        /*获取布局*/
        final DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);

        /*点击加载侧滑栏*/
        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        /*设置彩色图标*/
        navigationView.setItemIconTintList(null);


        // 获取 Header View 布局文件
        headerView = navigationView.getHeaderView(0);

        /*设置用户的用户名*/
        String savedfullName = (String) SPUtils.get(Student_Menu_Activity.this, "fullName", "");

        //修改用户的姓名
        TextView userName = headerView.findViewById(R.id.userName);
        userName.setText("欢迎您:" + savedfullName);




    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit(){
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(),"再按一次返回键退出程序",Toast.LENGTH_SHORT).show();
            //利用handler延迟发送更改状态信息，3000==3
            mHandler.sendEmptyMessageDelayed(0, 3000);
        } else {
            //在后台运行程序，不退出程序，只返回桌面 wang 9/28
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        }
    }

    Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }

    };




    public void showExitConfirmationDialog() {
        new Alert(Student_Menu_Activity.this)
                .builder()
                .setTitle("系统通知")
                .setMsg("是否注销当前用户?")
                .setPositiveButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击确定回到登录页面，并且释放token
                        SPUtils.clear(Student_Menu_Activity.this);

                        Intent intent = new Intent(Student_Menu_Activity.this, LoginActivity.class);
                        startActivity(intent);

                        //销毁当前页面
                        finish();
                    }
                })
                .setNegativeButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();
    }




}

