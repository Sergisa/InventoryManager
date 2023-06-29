package com.sergisa.inventorymanager;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainApp extends Application {
    @Override
    public void onCreate() {
        Log.d("APPLICATION", "CREATE");
        Singleton.initInstance();
        super.onCreate();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        Log.d("APPLICATION", "CONFIG CHANGED");
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onTerminate() {
        Log.d("APPLICATION", "TERMINATE");
        super.onTerminate();
    }

}
