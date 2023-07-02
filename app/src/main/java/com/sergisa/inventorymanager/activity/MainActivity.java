package com.sergisa.inventorymanager.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.sergisa.inventorymanager.Inventory;
import com.sergisa.inventorymanager.R;
import com.sergisa.inventorymanager.databinding.ActivityMainBinding;
import com.sergisa.inventorymanager.db.InventoryTableManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private InventoryTableManager inventoryTableManager;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private final ArrayList<Inventory> scannedLines = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getBaseContext().moveDatabaseFrom()
        //SQLiteDatabase db = getBaseContext().openOrCreateDatabase("inventory.db", MODE_PRIVATE, null);
        if (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 1);
        }
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        inventoryTableManager = new InventoryTableManager(this);
        inventoryTableManager.open();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public boolean add(Inventory s) {
        return scannedLines.add(s);
    }

    public List<Inventory> getScannedInventory() {
        return scannedLines;
    }
}