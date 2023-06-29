package com.sergisa.inventorymanager.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.sergisa.inventorymanager.Inventory;
import com.sergisa.inventorymanager.InventoryAdapter;
import com.sergisa.inventorymanager.databinding.FragmentGalleryBinding;
import com.sergisa.inventorymanager.db.InventoryTableManager;
import com.sergisa.inventorymanager.dialogs.AddItemDialogFragment;
import com.sergisa.inventorymanager.dialogs.EditItemDialogFragment;

public class GalleryFragment extends Fragment implements EditItemDialogFragment.NoticeDialogListener, AddItemDialogFragment.NoticeDialogListener {
    private InventoryTableManager inventoryTableManager;
    private FragmentGalleryBinding binding;
    RecyclerView itemsListView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        inventoryTableManager = new InventoryTableManager(getContext());
        inventoryTableManager.open();
        Log.d("GALLERY", "onCreateView: " + inventoryTableManager.getInventory().toString());

        itemsListView = binding.recordsList;
        final InventoryAdapter adapter = new InventoryAdapter(getContext(), inventoryTableManager.getInventory().toArray(new Inventory[0]));
        itemsListView.setAdapter(adapter);
        itemsListView.addItemDecoration(
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL)
        );
        adapter.withInventoryClickListener(item -> {
            new EditItemDialogFragment(item)
                    .withDialogListener(GalleryFragment.this)
                    .show(getActivity().getSupportFragmentManager(), "");
        });
        binding.addFab.setOnClickListener(view -> {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            new AddItemDialogFragment()
                    .withDialogListener(GalleryFragment.this)
                    .show(fragmentManager, "");
        });
        //galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDialogAddClick(AddItemDialogFragment dialog) {
        inventoryTableManager.insert(dialog.getPartialInventory());
        itemsListView.swapAdapter(new InventoryAdapter(
                getContext(),
                inventoryTableManager.getInventory().toArray(new Inventory[0])
        ), true);
        dialog.dismiss();
    }

    @Override
    public void onDialogRemoveClick(EditItemDialogFragment dialog) {
        inventoryTableManager.delete(Long.parseLong(dialog.getInventoryId()));
        //listViewAdapter.
        itemsListView.swapAdapter(new InventoryAdapter(
                getContext(),
                inventoryTableManager.getInventory().toArray(new Inventory[0])
        ), true);
        //adapter.changeCursor(dbManager.fetch());
        dialog.dismiss();
    }

    @Override
    public void onDialogEditClick(EditItemDialogFragment dialog) {
        inventoryTableManager.update(dialog.getInventory());
        itemsListView.swapAdapter(new InventoryAdapter(
                getContext(),
                inventoryTableManager.getInventory().toArray(new Inventory[0])
        ), true);
        dialog.dismiss();
    }
}