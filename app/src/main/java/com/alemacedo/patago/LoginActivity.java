package com.alemacedo.patago;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btLogin = (Button) findViewById(R.id.btLogin);
        btLogin.requestFocus();


    }


    public void btLoginClick (View view) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}