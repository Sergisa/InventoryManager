package com.sergisa.inventorymanager;

import androidx.annotation.NonNull;

import com.sergisa.inventorymanager.annotations.DataBaseKey;

@DataBaseKey(key = "Inventory")
public class Inventory {
    @DataBaseKey(key = "inventory_number")
    String inventoryNumber = "";
    @DataBaseKey(key = "name")
    String name = "";
    @DataBaseKey(key = "additional_code")
    String additionalCode = "";
    @DataBaseKey(key = "room")
    String room = "";
    int ID;

    public Inventory() {
    }

    public Inventory(int id) {
        ID = id;
    }

    public Inventory(String inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
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
}
