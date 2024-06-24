package com.xinke.edu.Appointment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.xinke.edu.Appointment.Adapter.MyTabFragmentAdapter;
import com.xinke.edu.Appointment.R;

import java.util.ArrayList;
import java.util.List;

public class ExamineActivity extends AppCompatActivity {

    ViewPager viewpager;
    TabLayout tabLayout;
    List<String> title=new ArrayList<>();
    List<Fragment> fragments=new ArrayList<>();
    int position=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.examine_activity);

        viewpager=findViewById(R.id.viewpager1);
        tabLayout=findViewById(R.id.tablayout1);

        fragments.add(new AuditedFragment());
        fragments.add(new UnauditedFragment());
        for (int i=0;i<=1;i++){
//            fragments.add(new PageFragment());
//            title.add("标题"+ i);
            tabLayout.addTab(tabLayout.newTab());
            if (i==0){
                title.add("待审核");
                position=0; // 第一个界面
            }else if (i==1){
                title.add("已审核");
                position=1; // 第二个界面
            }
        }

        MyTabFragmentAdapter adapter=new MyTabFragmentAdapter(getSupportFragmentManager(),fragments,title);
        viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);

        /*返回按钮点击事件*/
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
