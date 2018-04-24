package com.example.athini.picturesapi.Controller;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * Created by athini on 2018/04/23.
 */

public class PermissionCheckUtils {

    public static boolean hasPermission(Context context, String permission){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }
}
