package com.sergisa.inventorymanager.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.budiyev.android.codescanner.CodeScanner;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.sergisa.inventorymanager.Inventory;
import com.sergisa.inventorymanager.R;
import com.sergisa.inventorymanager.databinding.FragmentFirstBinding;
import com.sergisa.inventorymanager.db.InventoryTableManager;

public class FirstFragment extends Fragment {
    CodeScanner codeScanner;
    Inventory scannedInventory;
    MainActivity mainActivity;
    private FragmentFirstBinding binding;
    int activeCamera = CodeScanner.CAMERA_BACK;
    BottomSheetBehavior<FrameLayout> behavior;
    InventoryTableManager inventoryTableManager;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        mainActivity = (MainActivity) getActivity();
        inventoryTableManager = new InventoryTableManager(getContext());
        inventoryTableManager.open();
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        FrameLayout bottomSheetLayout = binding.bottomSheet;
        behavior = BottomSheetBehavior.from(bottomSheetLayout);
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View mainView, Bundle savedInstanceState) {
        super.onViewCreated(mainView, savedInstanceState);
        codeScanner = new CodeScanner(getContext(), binding.scannerView);
        codeScanner.setCamera(activeCamera);
        codeScanner.setDecodeCallback(result -> {
            getActivity().runOnUiThread(() -> {
                codeScanner.stopPreview();
                showBottomSheetDialog(result);
            });
        });
        binding.rotateCamera.setOnClickListener(view -> {
            codeScanner.stopPreview();
            codeScanner.releaseResources();
            if (activeCamera == CodeScanner.CAMERA_FRONT) {
                codeScanner.setCamera(CodeScanner.CAMERA_BACK);
                activeCamera = CodeScanner.CAMERA_BACK;
            } else if (activeCamera == CodeScanner.CAMERA_BACK) {
                codeScanner.setCamera(CodeScanner.CAMERA_FRONT);
                activeCamera = CodeScanner.CAMERA_FRONT;
            }
            codeScanner.startPreview();
        });
        binding.fab.setOnClickListener(view -> {
            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_SecondFragment);
        });
        binding.scannerView.setOnClickListener(view -> {
            codeScanner.startPreview();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //codeScanner.startPreview();
    }

    @Override
    public void onPause() {
        super.onPause();
        codeScanner.releaseResources();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showBottomSheetDialog(Result result) {
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        binding.inventoryNumber.setText(result.getText());
        if (result.getBarcodeFormat() == BarcodeFormat.DATA_MATRIX) {
            scannedInventory = new Inventory().withAdditionalCode(result.getText());
        } else if (result.getBarcodeFormat() == BarcodeFormat.QR_CODE) {
            scannedInventory = new Inventory(result.getText());
        } else {
            scannedInventory = new Inventory();
        }
        mainActivity.add(scannedInventory);
        Log.d("CODE SCAN FRAGMENT", scannedInventory.toString());
        Log.d("CODE SCAN FRAGMENT:request", inventoryTableManager.requestInventoryData(scannedInventory).toString());
    }
}