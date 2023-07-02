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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sergisa.inventorymanager.Inventory;
import com.sergisa.inventorymanager.InventoryAdapter;
import com.sergisa.inventorymanager.databinding.FragmentGalleryBinding;
import com.sergisa.inventorymanager.db.InventoryTableManager;
import com.sergisa.inventorymanager.dialogs.AddItemDialogFragment;
import com.sergisa.inventorymanager.dialogs.EditItemDialogFragment;

import java.util.List;

public class GalleryFragment extends Fragment implements EditItemDialogFragment.NoticeDialogListener, AddItemDialogFragment.NoticeDialogListener {
    private InventoryTableManager inventoryTableManager;
    private FragmentGalleryBinding binding;
    RecyclerView itemsListView;
    private InventoryAdapter adapter;
    List<Inventory> inventoryList;
    SwipeRefreshLayout swipeRefreshLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        swipeRefreshLayout = binding.swipeRefresh;
        swipeRefreshLayout.setOnRefreshListener(this::onRefreshData);
        inventoryTableManager = new InventoryTableManager(getContext());
        inventoryTableManager.open();
        Log.d("GALLERY", "onCreateView: " + inventoryTableManager.getInventory().toString());

        itemsListView = binding.recordsList;
        inventoryList = inventoryTableManager.getInventory();
        adapter = new InventoryAdapter(getContext(), inventoryList);
        itemsListView.setAdapter(adapter);
        itemsListView.addItemDecoration(
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL)
        );
        adapter.withInventoryClickListener(this::inventoryClicked);
        binding.addFab.setOnClickListener(view -> {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            new AddItemDialogFragment()
                    .withDialogListener(GalleryFragment.this)
                    .show(fragmentManager, "");
        });
        return root;
    }

    private void inventoryClicked(Inventory inventory) {
        new EditItemDialogFragment(inventory)
                .withDialogListener(GalleryFragment.this)
                .show(getActivity().getSupportFragmentManager(), "");
    }

    private void onRefreshData() {
        swipeRefreshLayout.setRefreshing(false);
        inventoryList = inventoryTableManager.getInventory();
        adapter = new InventoryAdapter(getContext(), inventoryList)
                .withInventoryClickListener(this::inventoryClicked);
        itemsListView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDialogAddClick(AddItemDialogFragment dialog) {
        Inventory inventoryToAdd = dialog.getPartialInventory();
        inventoryTableManager.insert(inventoryToAdd);
        inventoryList.add(0, inventoryToAdd);
        adapter.notifyItemRangeChanged(0, 1);
        dialog.dismiss();
    }

    @Override
    public void onDialogRemoveClick(EditItemDialogFragment dialog) {
        Inventory inventoryToRemove = dialog.getInventory();
        inventoryTableManager.delete(inventoryToRemove);
        final int indexToRemove = inventoryList.indexOf(inventoryToRemove);
        inventoryList.removeIf(inventory -> inventory.getID() == inventoryToRemove.getID());
        adapter.notifyItemRemoved(indexToRemove);
        dialog.dismiss();
    }

    @Override
    public void onDialogEditClick(EditItemDialogFragment dialog) {
        Inventory inventoryToChange = dialog.getInventory();
        int changingElementIndex = inventoryList.indexOf(inventoryToChange);
        inventoryTableManager.update(dialog.getInventory());
        adapter.notifyItemChanged(changingElementIndex);
        dialog.dismiss();
    }
}