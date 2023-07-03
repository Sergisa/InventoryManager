package com.sergisa.inventorymanager;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.sergisa.inventorymanager.db.InventoryDatabase;

public class InventoryApp extends Application {
    public static InventoryApp instance;

    private InventoryDatabase database;

    @Override
    public void onCreate() {
        Log.d("APPLICATION", "CREATE");
        super.onCreate();
        instance = this;
        database = Room.
                databaseBuilder(this, InventoryDatabase.class, "INVENTORY_DATABASE.db")
                .createFromAsset("INVENTORY_DATABASE.db")
                .allowMainThreadQueries()
                .build();
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

    public static InventoryApp getInstance() {
        return instance;
    }

    public InventoryDatabase getDatabase() {
        return database;
    }
}
