package com.apps.thecodess.medicationmanger.utils;

import android.os.Build;

import android.os.Bundle;
import android.view.Window;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.apps.thecodess.medicationmanger.R;


public class BaseActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT <= 22) {
            Window window = getWindow();
            window.setStatusBarColor(getResources().getColor(R.color.dark_middle_gray));
        }
    }
}
