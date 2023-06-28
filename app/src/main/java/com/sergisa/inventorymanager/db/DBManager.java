package com.sergisa.inventorymanager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.FileUtils;
import android.util.Log;

import com.sergisa.inventorymanager.Inventory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String name, String desc) {
        ContentValues contentValue = new ContentValues();
        contentValue.put("additional_code", "123");
        contentValue.put(DatabaseHelper.SUBJECT, name);
        contentValue.put(DatabaseHelper.NAME, desc);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[]{DatabaseHelper._ID, DatabaseHelper.INV_NUMBER, DatabaseHelper.NAME, DatabaseHelper.ADDITIONAL_CODE};
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

    public int update(long _id, String name, String desc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.SUBJECT, name);
        contentValues.put(DatabaseHelper.NAME, desc);
        return database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
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
                String ID = c.getString(nameFieldColumnIndex);
                list.add(
                        new Inventory(ID)
                                .withName(c.getString(descFieldColumnIndex))
                                .withInventoryNumber(c.getString(invFieldColumnIndex))
                                .withAdditionalCode(c.getString(additionalCodeFieldColumnIndex))
                );
            } while (c.moveToNext());
        }
        Log.d("DBManager", list.toString());
        return list;
    }

    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }
}

