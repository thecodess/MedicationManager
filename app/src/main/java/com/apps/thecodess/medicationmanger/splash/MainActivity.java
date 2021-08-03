package com.apps.thecodess.medicationmanger.splash;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.apps.thecodess.medicationmanger.R;
import com.apps.thecodess.medicationmanger.onboarding.OnboardingActivity;


/**
 * Displays the main screen.
 */
public class MainActivity extends AppCompatActivity implements MainContract.View{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MainPresenter mainPresenter = new MainPresenter(this);

        mainPresenter.splashDisplay();


    }

    @Override
    public void splashComplete() {
        Intent homeIntent = new Intent(MainActivity.this, OnboardingActivity.class);
        startActivity(homeIntent);
        finish();
    }
}
