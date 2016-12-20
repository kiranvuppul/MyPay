package com.user.easypay.Views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.user.easypay.Constants.EasyPayConstants;
import com.user.easypay.R;

/*
    Class to show splash screen for the application.
*/
public class SplashScreenActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_layout);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, MainMaterialActivity.class));
                finish();
            }
        }, EasyPayConstants.SPLASH_DISPLAY_INTERVAL);

    }
}
