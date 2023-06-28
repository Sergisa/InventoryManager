package com.sergisa.inventorymanager.activity;

import android.os.Bundle;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.FrameLayout;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.sergisa.inventorymanager.R;
import com.sergisa.inventorymanager.databinding.ActivityMainBinding;
import com.sergisa.inventorymanager.db.DBManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DBManager dbManager;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private final ArrayList<String> scannedLines = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getBaseContext().moveDatabaseFrom()
        //SQLiteDatabase db = getBaseContext().openOrCreateDatabase("inventory.db", MODE_PRIVATE, null);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        dbManager = new DBManager(this);
        dbManager.open();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public boolean add(String s) {
        return scannedLines.add(s);
    }

    public List<String> getScannedLines() {
        return scannedLines;
    }
}