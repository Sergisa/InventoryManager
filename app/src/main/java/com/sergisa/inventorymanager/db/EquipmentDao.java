package com.sergisa.inventorymanager.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EquipmentDao {
    @Query("SELECT * FROM Inventory")
    List<Inventory> getInventory();

    @Query("SELECT * FROM Inventory WHERE ID = :id")
    Inventory getById(long id);

    @Query("SELECT * FROM Inventory WHERE additional_code = :code OR inventory_number = :code")
    Inventory getByCode(String code);

    @Insert
    void insert(Inventory employee);

    @Update
    void update(Inventory inv);

    @Delete
    void delete(Inventory inv);
}
