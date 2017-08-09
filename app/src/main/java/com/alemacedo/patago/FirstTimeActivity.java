package com.alemacedo.patago;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FirstTimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time);
    }

    public void VerifyPermissions (View view) {

        Intent intent = new Intent(FirstTimeActivity.this, LoginActivity.class);

        startActivity(intent);
        FirstTimeActivity.this.finish();
    }
}
