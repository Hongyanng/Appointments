package com.xinke.edu.Appointment;


import android.app.DatePickerDialog;
import android.content.res.Resources;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment1 extends Fragment {

    /*初始化下拉框*/
    private Spinner spinnerBuilding;
    private Spinner spinnerFloor;
    private Spinner spinnerPeriod;



    /*时间选择器*/
    private Button btnPickDate;
    private Calendar selectedDate;

    /*给后端传值类型*/
    String buildingStr;
    int floorStr;
    String periodStr;
    String timeStr;


    public BlankFragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank_fragment1, null, false);

        //获取下拉框的id
        spinnerBuilding = view.findViewById(R.id.spinnerBuilding);
        spinnerFloor = view.findViewById(R.id.spinnerFloor);
        spinnerPeriod = view.findViewById(R.id.spinnerPeriod);


        // 初始化 Spinner
        initSpinners();


        /*时间选择器的方法*/
        btnPickDate = view.findViewById(R.id.btnPickDate);

        selectedDate = Calendar.getInstance();

        // 设置按钮点击事件，弹出日期选择器
        btnPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });




        return view;

    }

    /*下拉选择器的方法*/
    private void initSpinners() {
        // 设置教学楼名称 Spinner
        ArrayAdapter<CharSequence> buildingAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.building_names, android.R.layout.simple_spinner_item);
        buildingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBuilding.setAdapter(buildingAdapter);

        // 设置楼层 Spinner
        ArrayAdapter<CharSequence> floorAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.floor_numbers, android.R.layout.simple_spinner_item);
        floorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFloor.setAdapter(floorAdapter);


        // 设置节数 Spinner
        ArrayAdapter<CharSequence> periodAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.period_numbers, android.R.layout.simple_spinner_item);
        periodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPeriod.setAdapter(periodAdapter);


        // 设置选择教学楼 Spinner 的选项选择事件
        spinnerBuilding.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedBuilding = parent.getItemAtPosition(position).toString();
                //把获取到的用变量集成
                buildingStr=selectedBuilding;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // 设置选择教室楼层 Spinner 的选项选择事件
        spinnerFloor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 获取选中的楼层
                String selectedFloorString = parent.getItemAtPosition(position).toString();
                int selectedFloor = Integer.parseInt(selectedFloorString);
                floorStr = selectedFloor;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // 设置选择预约节数 Spinner 的选项选择事件
        spinnerFloor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedPeriod = parent.getItemAtPosition(position).toString();
                Log.i("selectedPeriod",selectedPeriod);
                Resources res = getResources();
                int[] periodNumbers = res.getIntArray(R.array.period_numbers);
                Log.i("periodNumbers",periodNumbers.toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });




    }


    /*时间选择器的方法*/
    private void showDatePickerDialog()
    {
        // 获取当前日期的年月日
        int year = selectedDate.get(Calendar.YEAR);
        int month = selectedDate.get(Calendar.MONTH);
        int day = selectedDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // 更新选定的日期
                        selectedDate.set(Calendar.YEAR, year);
                        selectedDate.set(Calendar.MONTH, month);
                        selectedDate.set(Calendar.DAY_OF_MONTH, day);

                        // 获取的日期文本用一个字符串保存起来
                        updateSelectedDateText();
                    }
                }, year, month, day);

        // 显示日期选择器对话框
        datePickerDialog.show();
    }

    private void updateSelectedDateText() {
        // 格式化日期并显示在文本视图中
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = sdf.format(selectedDate.getTime());
        timeStr=formattedDate;
    }
}


