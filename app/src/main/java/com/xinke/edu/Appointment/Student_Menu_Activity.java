package com.xinke.edu.Appointment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.xinke.edu.Appointment.fragment.OrdersPagerAdapter;
import com.xinke.edu.Appointment.token.SPUtils;
import com.xinke.edu.Appointment.token.SharedPreferencesUtils;


public class Student_Menu_Activity extends AppCompatActivity {


    /*初始化下拉框*/


    NavController navController;

    View headerView;
    NavigationView navigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_student);

        /*获取布局*/
        navigationView = findViewById(R.id.NavigationView);

        /*底部的布局切换*/
        ViewPager2 viewPager2 = findViewById(R.id.viewPager);
        viewPager2.setAdapter(new OrdersPagerAdapter(this));
//        TabLayout tabLayout = findViewById(R.id.tabLayout);
//        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
//            @Override
//            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
//
//                switch (position) {
//                    case 0: {
//                        tab.setText("首页");
//                        tab.setIcon(R.drawable.ic_home);
//
//                        break;
//                    }
//                    case 1: {
//                        tab.setText("不是首页");
//                        tab.setIcon(R.drawable.ic_assignment);
//                        break;
//                    }
//                }
//            }
//        });
//        tabLayoutMediator.attach();


        /*侧滑栏菜单的点击事件*/
        navigationView = findViewById(R.id.NavigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.menu_username:
                        Toast.makeText(Student_Menu_Activity.this, "你点到我了", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_collection:

                        break;
                    case R.id.menu_inbox:

                        break;
                    case R.id.menu_notification:

                        break;
                    case R.id.menu_orders:

                        break;
                    case R.id.menu_setting:

                        break;
                    case R.id.menu_support:
                        break;

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


    public void showExitConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("确认退出");
        builder.setMessage("确定要退出吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //点击确定回到登录页面，并且释放token

                SPUtils.clear(Student_Menu_Activity.this);

                Intent intent = new Intent(Student_Menu_Activity.this, LoginActivity.class);
                startActivity(intent);

                //销毁当前页面
                finish();

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }


}

