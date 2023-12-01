package com.xinke.edu.Appointment.Dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.xinke.edu.Appointment.LoginActivity;
import com.xinke.edu.Appointment.R;

public class SplashActivity extends AppCompatActivity {

    Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            hideLoadingDialog();
            toMainActivity();
        }
    };

    AlertDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        // 显示加载对话框
        // showLoadingDialog();

        // 模拟耗时操作，比如初始化数据等
        handler.postDelayed(runnable, 2000);
    }

    private void showLoadingDialog() {
        loadingDialog = new AlertDialog.Builder(this)
                .setTitle("Loading")
                .setMessage("Please wait...")
                .setCancelable(false)
                .create();

        loadingDialog.show();
    }

    private void hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    private void toMainActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
