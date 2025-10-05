package com.sergisa.inventorymanager.activity;

import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HALF_EXPANDED;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;
import com.sergisa.inventorymanager.CameraSwitchHandler;
import com.sergisa.inventorymanager.InventoryApp;
import com.sergisa.inventorymanager.OnSwipeTouchListener;
import com.sergisa.inventorymanager.R;
import com.sergisa.inventorymanager.databinding.FragmentFirstBinding;
import com.sergisa.inventorymanager.db.Inventory;
import com.sergisa.inventorymanager.db.InventoryDatabase;
import com.sergisa.inventorymanager.ui.BottomSheetStateController;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FirstFragment extends Fragment {
    private final int[] states = new int[]{
            STATE_HIDDEN,
            STATE_COLLAPSED,
            STATE_HALF_EXPANDED,
            STATE_EXPANDED
    };
    Inventory scannedInventory;
    MainActivity mainActivity;
    private FragmentFirstBinding binding;
    BottomSheetBehavior<FrameLayout> behavior;
    InventoryDatabase database;
    private static final String TAG = "MLKit Barcode";
    private CameraSelector cameraSelector;
    private ProcessCameraProvider cameraProvider;
    private Preview previewUseCase;
    private ImageAnalysis analysisUseCase;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        database = InventoryApp.getInstance().getDatabase();
        mainActivity = (MainActivity) getActivity();
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        FrameLayout bottomSheetLayout = binding.BSL.bottomSheet;
        behavior = BottomSheetBehavior.from(bottomSheetLayout);
        BottomSheetStateController<FrameLayout> BSController = new BottomSheetStateController<>(behavior);
        BSController.setStateSequence(states);
        behavior.setFitToContents(false);
        behavior.setState(STATE_HIDDEN);
        CameraSwitchHandler switchHandler = new CameraSwitchHandler(binding.cameraToggleButton, CameraSelector.LENS_FACING_BACK, CameraSelector.LENS_FACING_FRONT);
        switchHandler.setSelectionListener(cameraSelection -> {
            unbindCameraUseCases();
            setupCamera(cameraSelection);
        });
        binding.previewCamera.setOnClickListener(v -> {
            unbindCameraUseCases();
            setupCamera(switchHandler.getCameraSelection());
        });
        binding.previewCamera.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            @Override
            public void onSwipeTop() {
                BSController.increaseState();
            }

            @Override
            public void onSwipeBottom() {
                BSController.decreaseState();
            }
        });
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View mainView, Bundle savedInstanceState) {
        super.onViewCreated(mainView, savedInstanceState);

        binding.fab.setOnClickListener(view -> {
            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_SecondFragment);
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        setupCamera(CameraSelector.LENS_FACING_BACK);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setupCamera(int lensFacing) {
        final ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                ProcessCameraProvider.getInstance(requireContext());

        cameraSelector = new CameraSelector.Builder().requireLensFacing(lensFacing).build();
        cameraProviderFuture.addListener(() -> {
                    try {
                        cameraProvider = cameraProviderFuture.get();
                        bindAllCameraUseCases();
                    } catch (ExecutionException | InterruptedException e) {
                        Log.e(TAG, "cameraProviderFuture.addListener Error", e);
                    }
                },
                ContextCompat.getMainExecutor(requireContext())
        );
    }

    private void unbindCameraUseCases() {
        analysisUseCase.clearAnalyzer();
        cameraProvider.unbindAll();
    }

    private void bindAllCameraUseCases() {
        if (cameraProvider != null) {
            cameraProvider.unbindAll();
            bindPreviewUseCase();
            bindAnalysisUseCase();
        }
    }

    private void bindPreviewUseCase() {
        if (cameraProvider == null) {
            return;
        }

        if (previewUseCase != null) {
            cameraProvider.unbind(previewUseCase);
        }

        Preview.Builder builder = new Preview.Builder();
        builder.setTargetRotation(getRotation());

        previewUseCase = builder.build();
        previewUseCase.setSurfaceProvider(binding.previewCamera.getSurfaceProvider());

        try {
            cameraProvider.bindToLifecycle(this, cameraSelector, previewUseCase);
        } catch (Exception e) {
            Log.e(TAG, "Error when bind preview", e);
        }
    }

    private void bindAnalysisUseCase() {
        if (cameraProvider == null) {
            return;
        }

        if (analysisUseCase != null) {
            cameraProvider.unbind(analysisUseCase);
        }

        Executor cameraExecutor = Executors.newSingleThreadExecutor();

        ImageAnalysis.Builder builder = new ImageAnalysis.Builder();
        builder.setTargetRotation(getRotation());

        analysisUseCase = builder.build();
        analysisUseCase.setAnalyzer(cameraExecutor, this::analyze);

        try {
            cameraProvider.bindToLifecycle(this, cameraSelector, analysisUseCase);
        } catch (Exception e) {
            //Log.e(TAG, "Error when bind analysis", e);
        }
    }

    protected int getRotation() throws NullPointerException {
        return binding.previewCamera.getDisplay().getRotation();
    }

    @SuppressLint("UnsafeOptInUsageError")
    private void analyze(@NonNull ImageProxy image) {
        if (image.getImage() == null) return;

        InputImage inputImage = InputImage.fromMediaImage(
                image.getImage(),
                image.getImageInfo().getRotationDegrees()
        );

        BarcodeScanner barcodeScanner = BarcodeScanning.getClient();
        barcodeScanner.process(inputImage)
                .addOnSuccessListener(barcodes -> recognitionSuccess(barcodes, image))
                .addOnFailureListener(e -> Log.e(TAG, "Barcode process failure", e))
                .addOnCompleteListener(task -> image.close());
    }

    public Bitmap rotateBitmap(Bitmap source, float angle, Rect croppingRect) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, croppingRect.left, croppingRect.top, croppingRect.width(), croppingRect.height(), matrix, true);
    }

    private void recognitionSuccess(List<Barcode> barcodes, ImageProxy imageProxy) {
        if (!barcodes.isEmpty()) {
            Barcode bar = barcodes.get(0);
            analysisUseCase.clearAnalyzer();
            cameraProvider.unbindAll();
            //binding.scannedImage.setImageBitmap(rotateBitmap(imageProxy.toBitmap(), imageProxy.getImageInfo().getRotationDegrees(), bar.getBoundingBox()));
            binding.BSL.inventoryNumber.setText(barcodes.get(0).getDisplayValue());
            //scannedItemDao.insert(new ScannedItem(barcodes.get(0).getDisplayValue()));
            /*NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_SecondFragment);*/
            if (!barcodes.isEmpty()) {
                behavior.setShouldRemoveExpandedCorners(true);
                behavior.setState(STATE_HALF_EXPANDED);
                //binding.barcodeValue.setText(bar.getDisplayValue());
            }
        }
    }
}