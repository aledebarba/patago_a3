package com.alemacedo.patago;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FirstTimeActivity extends AppCompatActivity {

    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time);
    }


    public void botaoPermissoes (View view) {

           checkMultiplePermissions();

    }


// ------------------- Envia múltiplas permissões ------------------------------

    private void checkMultiplePermissions() {

        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissionsNeeded = new ArrayList<String>();
            List<String> permissionsList = new ArrayList<String>();

            if (!addPermission(permissionsList, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                permissionsNeeded.add("GPS");
            }

            if (!addPermission(permissionsList, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    permissionsNeeded.add("READ STORAGE");
            }

            if (!addPermission(permissionsList, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                permissionsNeeded.add("COARSE LOCATION");
            }

            if (!addPermission(permissionsList, Manifest.permission.INTERNET)) {
                permissionsNeeded.add("INTERNET");
            }

            if (!addPermission(permissionsList, Manifest.permission.SEND_SMS)) {
                permissionsNeeded.add("SEND SMS");
            }

            if (!addPermission(permissionsList, Manifest.permission.CAMERA)) {
                permissionsNeeded.add("CAMERA");
            }

            if (!addPermission(permissionsList, Manifest.permission.CALL_PHONE)) {
                permissionsNeeded.add("CALL PHONE");
            }

            if (permissionsList.size() > 0) {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                return;
            }

        }

    }

    // pede cada uma das permissões e adiciona em permissionsList
    private boolean addPermission(List<String> permissionsList, String permission) {
        if (Build.VERSION.SDK_INT >= 23)

            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);

                // Check for Rationale Option????
                if (!shouldShowRequestPermissionRationale(permission))
                    return false;
            }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, DeniedPermission.class);
            startActivity(intent);
            this.finish();

        } else {

            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, DeniedPermission.class);
            startActivity(intent);
            this.finish();
        }
        return;
        }



// --------------------------------------fim da classe

}

