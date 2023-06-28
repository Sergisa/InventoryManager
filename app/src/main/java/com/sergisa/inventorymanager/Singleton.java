package com.sergisa.inventorymanager;

import android.util.Log;

public class Singleton {
    private static Singleton mInstance;

    public static void initInstance() {
        Log.d("MY", "MySingleton::InitInstance()");
        if (mInstance == null) {
            mInstance = new Singleton();
        }
    }
    public static Singleton getInstance() {
        Log.d("MY", "MySingleton::getInstance()");
        return mInstance;
    }
}
