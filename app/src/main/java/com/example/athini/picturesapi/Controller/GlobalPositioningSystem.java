package com.example.athini.picturesapi.Controller;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;

/**
 * Created by athini on 2018/04/23.
 */

public class GlobalPositioningSystem extends Service implements LocationListener {
    private final Context context;
    boolean isGPSEnabled = false;
    boolean canGetlocation = false;
    boolean isNetworkEnabled = false;
    Location location;
    double latitude;
    double longitude;
    protected LocationManager locationManager;

    public GlobalPositioningSystem(Context context) {
        this.context = context;
        getLocation();
    }

    @SuppressLint("MissingPermission")
    public Location getLocation() {
        try {
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (!isGPSEnabled && !isNetworkEnabled) {
            } else
                this.canGetlocation = true;
            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
            }
            if (isGPSEnabled) {
                if (location == null) {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }
    public  double getLatitude() {
        if (location!= null){
            latitude = location.getLatitude();
        }
        return latitude;
    }
    public double getLongitude(){
        if (location != null){
            longitude = location.getLongitude();
        }
        return longitude;
    }
    public void showSettingAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("GPS settings");
        alertDialog.setMessage("Please enabled your GPS");
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });
        alertDialog.show();
    }
    @Override
    public void onLocationChanged(Location arg0){
    }
    @Override
    public void onProviderDisabled(String arg0){
    }
    @Override
    public void onProviderEnabled(String arg0){
    }
    @Override
    public void onStatusChanged(String arg0, int arg1, Bundle arg2){
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public boolean canGetlocation() {
        return this.canGetlocation;
    }

}
