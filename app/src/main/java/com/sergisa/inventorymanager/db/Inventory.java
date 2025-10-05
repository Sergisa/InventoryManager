package com.sergisa.inventorymanager.db;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "Inventory")
public class Inventory {
    @ColumnInfo(name = "inventory_number")
    public String inventoryNumber = "";

    @ColumnInfo(name = "image")
    @TypeConverters(ImageBitmapString.class)
    public Bitmap image;
    @ColumnInfo(name = "serial_number")
    public String serialNumber = "";

    @ColumnInfo(name = "name")
    public String name = "";
    @ColumnInfo(name = "additional_code")
    public String additionalCode = "";
    @ColumnInfo(name = "room")
    public String room = "";
    @PrimaryKey
    @ColumnInfo(name = "ID")
    public int ID;

    public Inventory() {
    }

    public Inventory(int id) {
        ID = id;
    }

    public Inventory(String inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    public Inventory(String inventoryNumber, String name, String additionalCode, String room, int ID) {
        this.inventoryNumber = inventoryNumber;
        this.name = name;
        this.additionalCode = additionalCode;
        this.room = room;
        this.ID = ID;
    }

    public String getInventoryNumber() {
        return inventoryNumber;
    }

    public Inventory withInventoryNumber(String inventoryNumber) {
        if (inventoryNumber != null) {
            this.inventoryNumber = inventoryNumber;
        }
        return this;
    }

    public String getName() {
        return name;
    }

    public Inventory withName(String name) {
        if (name != null) {
            this.name = name;
        }
        return this;
    }

    public String getAdditionalCode() {
        return additionalCode;
    }

    public Inventory withAdditionalCode(String additionalCode) {
        if (additionalCode != null) {
            this.additionalCode = additionalCode;
        }
        return this;
    }

    public String getRoom() {
        return room;
    }

    public Inventory withRoom(String room) {
        if (room != null) {
            this.room = room;
        }
        return this;
    }

    public int getID() {
        return ID;
    }

    public Inventory withID(int ID) {
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

    public String getSerial() {
        return serialNumber;
    }

    public void setSerial(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
