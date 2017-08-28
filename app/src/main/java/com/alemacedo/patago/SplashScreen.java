package com.alemacedo.patago;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, FirstTimeActivity.class);
                startActivity(intent);
                SplashScreen.this.finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout); ;
            }
        }, 2000);
    }

    /* private void showFirstTime() {
        Intent intent = new Intent(SplashScreen.this, FirstTimeActivity.class);
        startActivity(intent);
        finish();
    } */
}

