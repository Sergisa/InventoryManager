package com.sergisa.inventorymanager.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Equipment")
public class Equipment {
    @ColumnInfo(name = "inventory_number")
    public String inventoryNumber = "";
    @ColumnInfo(name = "name")
    public String name = "";
    @ColumnInfo(name = "additional_code")
    public String additionalCode = "";
    @ColumnInfo(name = "room")
    public String room = "";
    @PrimaryKey
    @ColumnInfo(name = "ID")
    public int ID;

    public Equipment() {
    }

    public Equipment(int id) {
        ID = id;
    }

    public Equipment(String inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    public String getInventoryNumber() {
        return inventoryNumber;
    }

    public Equipment withInventoryNumber(String inventoryNumber) {
        if (inventoryNumber != null) {
            this.inventoryNumber = inventoryNumber;
        }
        return this;
    }

    public String getName() {
        return name;
    }

    public Equipment withName(String name) {
        if (name != null) {
            this.name = name;
        }
        return this;
    }

    public String getAdditionalCode() {
        return additionalCode;
    }

    public Equipment withAdditionalCode(String additionalCode) {
        if (additionalCode != null) {
            this.additionalCode = additionalCode;
        }
        return this;
    }

    public String getRoom() {
        return room;
    }

    public Equipment withRoom(String room) {
        if (room != null) {
            this.room = room;
        }
        return this;
    }

    public int getID() {
        return ID;
    }

    public Equipment withID(int ID) {
        this.ID = ID;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return "Inventory{" +
                "inventoryNumber='" + inventoryNumber + '\'' +
                ", name='" + name + '\'' +
                ", additionalCode='" + additionalCode + '\'' +
                ", room='" + room + '\'' +
                ", ID=" + ID +
                '}';
    }
}
