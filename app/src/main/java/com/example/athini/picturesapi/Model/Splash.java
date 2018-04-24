package com.example.athini.picturesapi.Model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.athini.picturesapi.Controller.PermissionCheckUtils;
import com.example.athini.picturesapi.R;

import java.util.ArrayList;

/**
 * Created by athini on 2018/04/24.
 */

public class Splash extends Activity {

    ImageView imageView;
    TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        imageView = findViewById(R.id.logo);
        textView = findViewById(R.id.message);
        final Animation start = AnimationUtils.loadAnimation(getBaseContext(),R.anim.timer);
        imageView.startAnimation(start);
        textView.startAnimation(start);
        start.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(Splash.this, MainActivity.class));
                finish();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!hasAllRequiredPermissions()){
            requestAllRequiredPermissions();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    protected void onStop() {
        super.onStop();
    }
    private static final int REQUEST_ALL_MISSING_PERMISSIONS = 1;
    protected String[] getRequiredPermissions(){
        return new String[] {
                android.Manifest.permission.ACCESS_FINE_LOCATION
        };
    }
    private boolean hasAllRequiredPermissions () {
        for (String permission : getRequiredPermissions()) {
            if ((!PermissionCheckUtils.hasPermission(getApplicationContext(), permission))) {
                return false;
            }
        }
        return true;
    }

    @SuppressLint("NewApi")
    private void requestAllRequiredPermissions () {

        ArrayList<String> notGrantedPermissions = new ArrayList<>();
        for (String permission : getRequiredPermissions()) {
            if (!PermissionCheckUtils.hasPermission(getApplicationContext(),permission)) {
                notGrantedPermissions.add(permission);
            }
        }
        if (notGrantedPermissions.size()>0){
            requestPermissions(notGrantedPermissions.toArray(new String[notGrantedPermissions.size()]),REQUEST_ALL_MISSING_PERMISSIONS);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ALL_MISSING_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(getApplicationContext(),"Please allow permissions", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
