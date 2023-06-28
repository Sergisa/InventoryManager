package com.sergisa.inventorymanager.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "Inventory";
    public static final String INV_NUMBER = "inventory_number";
    public static final String NAME = "name";
    public static final String ADDITIONAL_CODE = "additional_code";

    Map<String, String> fields = new HashMap<>();
    public static final String _ID = "ID";
    public static final String SUBJECT = "subject";

    // Database Information
    static final String DB_NAME = "JOURNALDEV_COUNTRIES.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS Inventory ( ID INTEGER PRIMARY KEY AUTOINCREMENT, additional_code TEXT, name TEXT, inventory_number TEXT);";
    private static final String EQUIPMENT_CREATION = "CREATE TABLE IF NOT EXISTS Equipment (ID INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, inventory_id TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        /*fields.put("ID", "INTEGER PRIMARY KEY AUTOINCREMENT");
        fields.put("subject", "TEXT NOT NULL");
        fields.put("description", "TEXT NOT NULL");
        fields.put("additional_code", "TEXT NOT NULL");
        StringBuilder SQL_QUERY_Builder = new StringBuilder("CREATE TABLE " + TABLE_NAME + " (");

        fields.forEach(new BiConsumer<String, String>() {
            @Override
            public void accept(String key, String value) {
                SQL_QUERY_Builder.append(key).append(" ").append(value).append(", ");
            }
        });
        SQL_QUERY_Builder.(", ");
        SQL_QUERY_Builder.append(");");
        CREATE_TABLE = SQL_QUERY_Builder.toString();
        Log.d("DBHelper", SQL_QUERY_Builder.toString());
        //CREATE TABLE Equipment (ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT NOT NULL);*/

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        db.execSQL(EQUIPMENT_CREATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "Inventory");
        db.execSQL("DROP TABLE IF EXISTS " + "Equipment");
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        Log.d("DBManager","Open");
        //db.execSQL("DROP TABLE IF EXISTS " + "Inventory");
        //db.execSQL("DROP TABLE IF EXISTS " + "Equipment");
        onCreate(db);
    }
}
