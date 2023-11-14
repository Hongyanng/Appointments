package com.xinke.edu.Appointment.Dialog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.xinke.edu.Appointment.LoginActivity;
import com.xinke.edu.Appointment.R;

import butterknife.BindView;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity {

    /*启动页*/

    Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tomainActivity();
        }
    };

    private void tomainActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }


//    TimeCount timeCount;


    Button button;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        //点击跳过
//        initViews();

        //
        handler.postDelayed(runnable,3000);

//        timeCount=new TimeCount(4000,1000);
//        timeCount.start();
    }


//    public void initViews() {
//        button = findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tomainActivity();
//            }
//        });
//    }


//   class TimeCount extends CountDownTimer{
//
//       /**
//        * @param millisInFuture    The number of millis in the future from the call
//        *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
//        *                          is called.
//        * @param countDownInterval The interval along the way to receive
//        *                          {@link #onTick(long)} callbacks.
//        */
//       public TimeCount(long millisInFuture, long countDownInterval) {
//           super(millisInFuture, countDownInterval);
//       }
//
//       @SuppressLint("SetTextI18n")
//       @Override
//       public void onTick(long l) {
//            button.setText(l/1000+ "秒");
//       }
//
//       @Override
//       public void onFinish() {
//
//       }
//   }

}
