package com.sergisa.inventorymanager.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Inventory.class}, version = 2)
@TypeConverters({ImageBitmapString.class})
public abstract class InventoryDatabase extends RoomDatabase {
    public abstract InventoryDao inventoryDao();
    public abstract EquipmentDao equipmentDao();
}
