package com.sergisa.inventorymanager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sergisa.inventorymanager.Inventory;

import java.util.ArrayList;
import java.util.List;

public class InventoryTableManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public InventoryTableManager(Context c) {
        context = c;
    }

    public InventoryTableManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(Inventory inv) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.INV_NUMBER, inv.getInventoryNumber());
        contentValue.put(DatabaseHelper.NAME, inv.getName());
        contentValue.put(DatabaseHelper.ADDITIONAL_CODE, inv.getAdditionalCode());
        contentValue.put(DatabaseHelper.ROOM, inv.getRoom());
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[]{DatabaseHelper._ID, DatabaseHelper.INV_NUMBER, DatabaseHelper.NAME, DatabaseHelper.ADDITIONAL_CODE, DatabaseHelper.ROOM};
        Cursor cursor = database.query(
                DatabaseHelper.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null
        );
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(Inventory inv) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME, inv.getName());
        contentValues.put(DatabaseHelper.ROOM, inv.getRoom());
        contentValues.put(DatabaseHelper.INV_NUMBER, inv.getInventoryNumber());
        contentValues.put(DatabaseHelper.ADDITIONAL_CODE, inv.getAdditionalCode());
        return database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + inv.getID(), null);
    }

    public List<Inventory> getInventory() {
        List<Inventory> list = new ArrayList<>();
        Cursor c = fetch();
        if (c.moveToFirst()) {
            do {
                int nameFieldColumnIndex = c.getColumnIndex(DatabaseHelper._ID);
                int descFieldColumnIndex = c.getColumnIndex(DatabaseHelper.NAME);
                int invFieldColumnIndex = c.getColumnIndex(DatabaseHelper.INV_NUMBER);
                int additionalCodeFieldColumnIndex = c.getColumnIndex(DatabaseHelper.ADDITIONAL_CODE);
                int roomFieldColumnIndex = c.getColumnIndex(DatabaseHelper.ROOM);
                String ID = c.getString(nameFieldColumnIndex);
                list.add(
                        new Inventory(Integer.parseInt(ID))
                                .withName(c.getString(descFieldColumnIndex))
                                .withInventoryNumber(c.getString(invFieldColumnIndex))
                                .withAdditionalCode(c.getString(additionalCodeFieldColumnIndex))
                                .withRoom(c.getString(roomFieldColumnIndex))
                );
            } while (c.moveToNext());
        }
        Log.d("DBManager", list.toString());
        return list;
    }

    public void delete(Inventory inv) {
        Log.d("TABLE MANAGERR", inv.toString());
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + inv.getID(), null);
    }
}

