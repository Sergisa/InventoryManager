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

import com.sergisa.inventorymanager.Inventory;
import com.sergisa.inventorymanager.InventoryAdapter;
import com.sergisa.inventorymanager.R;
import com.sergisa.inventorymanager.databinding.FragmentSecondBinding;
import com.sergisa.inventorymanager.dialogs.EditItemDialogFragment;
import com.sergisa.inventorymanager.ui.DatabaseViewer;
import com.sergisa.inventorymanager.ui.gallery.GalleryFragment;

import java.util.ArrayList;
import java.util.List;

public class SecondFragment extends Fragment implements EditItemDialogFragment.NoticeDialogListener {

    private FragmentSecondBinding binding;
    List<Inventory> scannedCodes;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        scannedCodes = new ArrayList<>();
        for (String code : getArguments().getStringArrayList("data")) {
            scannedCodes.add(new Inventory(code));
        }
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        Log.d("SECOND ACT", "Incoming list" + scannedCodes.toString());

        InventoryAdapter adapter = new InventoryAdapter(
                getContext(),
                scannedCodes.toArray(new Inventory[]{})
        ).withInventoryClickListener(item -> {
            new EditItemDialogFragment(item)
                    .withDialogListener(SecondFragment.this)
                    .show(getActivity().getSupportFragmentManager(), "");
        });
        adapter.withOptionsClickListener((v, singleInventory, i) -> {
            Log.d("SECOND FRAGMENT -> LIST", singleInventory.toString());
        });
        binding.listView.setAdapter(adapter);
        binding.viewDatabaseButton.setOnClickListener(view -> {
            Intent home_intent = new Intent(getContext(), DatabaseViewer.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(home_intent);
        });
        return binding.getRoot();

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
    public void onDialogRemoveClick(EditItemDialogFragment dialog) {

    }

    @Override
    public void onDialogEditClick(EditItemDialogFragment dialog) {

    }
}