package com.sergisa.inventorymanager.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.sergisa.inventorymanager.databinding.FragmentImportDataBinding;

public class ImportDataFragment extends Fragment {

    private FragmentImportDataBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ImportDataViewModel importDataViewModel =
                new ViewModelProvider(this).get(ImportDataViewModel.class);

        binding = FragmentImportDataBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textHome;
        //importDataViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}