package com.sergisa.inventorymanager.dialogs;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.sergisa.inventorymanager.R;
import com.sergisa.inventorymanager.db.Inventory;

public class AddItemDialogFragment extends InventoryItemDialog {
    String id = "";

    public AddItemDialogFragment(String code) {
        this(new Inventory(code));
    }

    public AddItemDialogFragment(Inventory inv) {
        partialInventory = inv;
    }

    public AddItemDialogFragment() {
        this(new Inventory(null));
    }

    public interface NoticeDialogListener {
        void onDialogAddClick(AddItemDialogFragment dialog);
    }

    NoticeDialogListener dialogListener;

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initDialog(savedInstanceState);
        (rootView.findViewById(R.id.btn_save)).setOnClickListener(view -> {
            partialInventory
                    .withName(inventoryNameEditText.getText().toString())
                    .withAdditionalCode(inventoryAdditionalCodeEditText.getText().toString())
                    .withInventoryNumber(inventoryNumberEditText.getText().toString());
            dialogListener.onDialogAddClick(AddItemDialogFragment.this);
        });
        return builder
                .setTitle("Добавление")
                .setIcon(R.drawable.add_to_queue_svgrepo_com)
                .create();
    }

    public AddItemDialogFragment withId(String id) {
        this.id = id;
        return this;
    }

    public AddItemDialogFragment withDialogListener(NoticeDialogListener dialogListener) {
        this.dialogListener = dialogListener;
        return this;
    }

    public Inventory getPartialInventory() {
        return partialInventory;
    }
}
