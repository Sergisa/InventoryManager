package com.sergisa.inventorymanager.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Inventory.class, Equipment.class}, version = 1)
public abstract class InventoryDatabase extends RoomDatabase {
    public abstract InventoryDao inventoryDao();
    public abstract EquipmentDao equipmentDao();
}
