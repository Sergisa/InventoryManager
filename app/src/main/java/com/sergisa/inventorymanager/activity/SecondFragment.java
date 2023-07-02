package com.sergisa.inventorymanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sergisa.inventorymanager.Inventory;
import com.sergisa.inventorymanager.InventoryAdapter;
import com.sergisa.inventorymanager.R;
import com.sergisa.inventorymanager.databinding.FragmentSecondBinding;
import com.sergisa.inventorymanager.db.InventoryTableManager;
import com.sergisa.inventorymanager.dialogs.AddItemDialogFragment;
import com.sergisa.inventorymanager.ui.DatabaseViewer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SecondFragment extends Fragment implements AddItemDialogFragment.NoticeDialogListener {

    private FragmentSecondBinding binding;
    List<Inventory> scannedCodes;
    InventoryTableManager inventoryTableManager;
    MainActivity mainActivity;
    InventoryAdapter adapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        mainActivity = (MainActivity) getActivity();
        scannedCodes = mainActivity.getScannedInventory();
        inventoryTableManager = new InventoryTableManager(getContext());
        inventoryTableManager.open();
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        Log.d("SECOND ACT", "Incoming list" + scannedCodes.toString());

        adapter = new InventoryAdapter(
                getContext(),
                scannedCodes
        ).withInventoryClickListener(item -> {
            new AddItemDialogFragment(item)
                    .withDialogListener(SecondFragment.this)
                    .show(getActivity().getSupportFragmentManager(), "");
        });
        adapter.withOptionsClickListener((v, singleInventory, i) -> {
            Log.d("SECOND FRAGMENT -> LIST", singleInventory.toString());
        });
        binding.listView.setAdapter(adapter);
        binding.listView.addItemDecoration(
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL)
        );
        binding.viewDatabaseButton.setOnClickListener(view -> {
            Intent home_intent = new Intent(getContext(), DatabaseViewer.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(home_intent);
        });
        binding.swipeRefresh.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        binding.swipeRefresh.setOnRefreshListener(this::onRefresh);
        return binding.getRoot();

    }

    private void onRefresh() {
        binding.swipeRefresh.setRefreshing(false);
        //mainActivity.getScannedInventory().
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonSecond.setOnClickListener(view1 -> {
            NavHostFragment.findNavController(SecondFragment.this)
                    .navigate(R.id.action_SecondFragment_to_FirstFragment);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDialogAddClick(AddItemDialogFragment dialog) {
        inventoryTableManager.insert(dialog.getPartialInventory());
        dialog.dismiss();
    }
}