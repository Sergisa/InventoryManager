package com.sergisa.inventorymanager;

import androidx.annotation.NonNull;

public class Inventory {
    String inventoryNumber = "";
    String name = "";
    String additionalCode = "";

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

    @Override
    public String toString() {
        return "Inventory{" +
                "inventoryNumber='" + inventoryNumber + '\'' +
                ", name='" + name + '\'' +
                ", additionalCode='" + additionalCode + '\'' +
                "}\r\n";
    }
}
