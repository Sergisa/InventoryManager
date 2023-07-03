package com.sergisa.inventorymanager;

import android.view.View;

import com.sergisa.inventorymanager.db.Inventory;

public interface OnInventoryOptionsClickListener {
    void onButtonClick(View v, Inventory post, int i);
}