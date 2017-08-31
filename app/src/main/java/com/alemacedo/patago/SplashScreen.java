package com.alemacedo.patago;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    boolean permission = true; // se o build for menor que 23, já vem com as permissões
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

       // verifica a build e depois verifica as permissões

        if (Build.VERSION.SDK_INT >= 23) {
            permission = (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) &&
                    (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) &&
                    (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) &&
                    (checkSelfPermission(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) &&
                    (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) &&
                    (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                    (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED);
        }

            if (!permission) {
                intent = new Intent(SplashScreen.this, FirstTimeActivity.class); // executando pela primeira vez ou não tem alguma permissão
            } else {
                intent = new Intent(SplashScreen.this, LoginActivity.class);
            }

        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                SplashScreen.this.finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout); ;
            }
        }, 2000);
    }
}

